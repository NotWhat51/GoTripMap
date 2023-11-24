import json
import uuid

#присваиваем каждому запросу свой ID
id_ = str(uuid.uuid4())
entries = []

#тело запроса
data = {
    "id": id_,
    "entries": []
}

tsys = ''
#транспортные средства (передается от клиента нажатием на соответствующую иконку, но текст запроса в приоритете)
tsyssets = {'велосипед': 'bike', 'пешком': 'ped', 'автобус': 'bus',
            'троллейбус': 'trolley', 'трамвай': 'tram', 'машина': 'car', 'такси': 'taxi'}

#открываем файл с ключевыми словами
with open("keywords.txt", "r", encoding='utf-8') as keywords:
    lines = keywords.readlines()
    i = 1
    for line in lines:
        entry = {}
        words = line.split(" ")
        words = [word.rstrip('\n') for word in words]
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

json_string = json.dumps(data, ensure_ascii=False, indent=4)

#непосредственно создание json
with open("data.json", "w", encoding='utf-8-sig') as file:
    file.write(json_string)