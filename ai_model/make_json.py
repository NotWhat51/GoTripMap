import json
import uuid

id_ = str(uuid.uuid4())
entries = []
data = {
    "id": id_,
    "entries": []
}

tsys = ''
#транспортные средства
tsyssets = {'велосипед': 'bike', 'пешком': 'ped','автобус': 'bus',
            'троллейбус': 'trolley', 'трамвай': 'tram', 'машина': 'car'}

with open("keywords.txt", "r") as keywords:
    lines = keywords.readlines()
    i = 1
    for line in lines:
        entry = {}
        words = line.split(" ")
        entry['entryno'] = i
        entry['tsys'] = 'ped'

        for word in words:
            if word in tsyssets.keys():
                entry['tsys'] = tsyssets[word]
                tsys = word

        if tsys in words:
            words.remove(tsys)

        entry['time'] = 'NOT_SPECIFIED'
        entry['maxdistance'] = 'NOT_SPECIFIED'
        entry['mindistance'] = 'NOT_SPECIFIED'

        if i == 1:
            entry['startpoint'] = 'FROM_CLIENT'
        else:
            entry['startpoint'] = 'PREVIOUS_POINT'

        destpoint = {'name': '', 'category': words[0]}
        entry['destpoint'] = destpoint
        entries.append(entry)
        i += 1

    data['entries'] = entries

json_string = json.dumps(data)

with open("data.json", "w") as file:
    file.write(json_string)