import numpy as np
import pandas as pd
from shapely import LineString
from math import acos, degrees
import requests

def intersect(lst1, lst2):
    if lst1 == lst2:
        return lst1
    else:
        temp = set(lst2)
        lst3 = [value for value in lst1 if value in temp]
        return lst3

turns = edges[['un', 'vn', 'osmid', 'TSYSSET', 'geometry']]
turns = turns.sort_values(by = ['un'])
turns1 = turns.copy()
turns1 = turns1.sort_values(by = ['vn'])
turns = pd.merge(turns, turns1, how = 'inner', left_on = 'un', right_on = 'vn', suffixes = ('_out', '_in'))
turns['TSYSSET_in'] = turns['TSYSSET_in'].apply(lambda x: x.split(','))
turns['TSYSSET_out'] = turns['TSYSSET_out'].apply(lambda x: x.split(','))
turns['TSYSSET'] = [intersect(x, y) for x, y in zip(turns['TSYSSET_in'], turns['TSYSSET_out'])]
turns['TSYSSET'] = turns['TSYSSET'].apply(lambda x: str(x).replace("'", "").replace("[", "").replace("]", "").replace(" ",""))
nodes1 = nodes[['no', 'osmid']]
nodes1  = nodes1.rename(columns = {'no' : 'vn_in'})
turns = pd.merge(left = turns, right = nodes1, on = 'vn_in')
turns.rename(columns = {'osmid' : 'osmid_via'}, inplace = True)

coords = '59.9936,30.1927,60.0107,30.2261'
query = f'''
[out:json];
(relation["type"="restriction"]({coords}););
out center;
'''
r = requests.get('https://overpass-api.de/api/interpreter', params={'data': query})

elems = r.json()

members = [elem['members'] for elem in elems['elements']]

restrictions = [{member[i]['role'] : member[i]['ref'] for i in range(0, len(member))} for member in members]
for restriction in restrictions:
    if not 'from' in restriction.keys():
        restriction['from'] = ''
    if not 'via' in restriction.keys():
        restriction['via'] = ''
    if not 'to' in restriction.keys():
        restriction['to'] = ''

tags = [elem['tags']['restriction'] for elem in elems['elements']]
restr_df = pd.DataFrame({'osmid_in' : [res['from'] for res in restrictions], 'osmid_via' : [res['via'] for res in restrictions], 'osmid_out' : [res['to'] for res in restrictions]})
restr_df['restriction'] = tags
restr_df.replace('', np.nan, inplace=True)
restr_df = restr_df.dropna()

restr_df['osmid_in'] = restr_df['osmid_in'].apply(lambda x: int(x))
restr_df['osmid_via'] = restr_df['osmid_via'].apply(lambda x: int(x))
restr_df['osmid_out'] = restr_df['osmid_out'].apply(lambda x: int(x))

turns['osmid_in'] = turns['osmid_in'].astype('int64')
turns['osmid_out'] = turns['osmid_out'].astype('int64')
turns['osmid_via'] = turns['osmid_via'].astype('int64')
turns = pd.merge(left = turns, right = restr_df, how = 'left', on = ['osmid_in', 'osmid_via', 'osmid_out'])
turns = turns.fillna('')
turns.loc[turns['restriction'].str.contains('no'), 'TSYSSET'] = ''


def count_angle(line1: LineString, line2: LineString):
    x_in_start, y_in_start = line1.coords[-2:][0][0], line1.coords[-2:][0][1]
    x_in_finish, y_in_finish = line1.coords[-2:][1][0], line1.coords[-2:][1][1]
    x_out_finish, y_out_finish = line2.coords[:2][1][0], line2.coords[:2][1][1]

    # вычисляем координаты векторов
    a = (x_in_finish - x_in_start, y_in_finish - y_in_start)
    b = (x_out_finish - x_in_finish, y_out_finish - y_in_finish)

    # вычисляем длины векторов
    length_a = (a[0] ** 2 + a[1] ** 2) ** 0.5
    length_b = (b[0] ** 2 + b[1] ** 2) ** 0.5

    # вычисляем скалярное произведение
    dot_product = a[0] * b[0] + a[1] * b[1]
    result = dot_product / (length_a * length_b)
    # вычисляем угол между векторами в градусах
    if result < -1:
        result = -1
    if result > 1:
        result = 1
    angle = degrees(acos(result))

    if a[0] * b[1] - a[1] * b[0] < 0:
        return 180 - angle
    else:
        return 180 + angle

turns['angle'] = [count_angle(line1, line2) for line1, line2 in zip(turns['geometry_in'], turns['geometry_out'])]

def get_type(ang):
    if ang == 360:
        return 4
    if ang < 135:
        return 1
    if ang >= 135 and ang <= 225:
        return 2
    if ang > 225:
        return 3

turns['type'] = [get_type(a) for a in turns.angle]

turns['capprt'] = pd.Series('', dtype=str)
turns['t0prt'] = pd.Series('', dtype=str)
turns['osm_rel_id'] = pd.Series('', dtype=str)
save = ['un_in', 'vn_in', 'vn_out', 'TSYSSET', 'capprt', 't0prt', 'osm_rel_id', 'type']
turns = turns.drop(columns = [column for column in turns.columns if not column in save])
turns = turns.reindex(columns = save)
turns['TSYSSET'] = turns['TSYSSET'].str.replace(",PUTW","")
turns.to_csv('turns.csv', index = False, sep = ';', header = False)