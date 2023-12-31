from rutermextract import TermExtractor

import pymorphy2

morph = pymorphy2.MorphAnalyzer()
term_extractor = TermExtractor()

features = []
feature_indices = []

#открываем файл с текстом запроса и создаем файл с ключевыми словами
with open("request.txt", "r", encoding='utf-8') as request, open("keywords.txt", "w", encoding='utf-8') as keywords:
    for line in request.readlines():
        words = line.split(" ")

        #ищем слова-соединители (для составных запросов)
        for i in range(0, len(words)):
            if words[i] in ['затем', 'далее', 'потом', 'после', 'ещё', 'и']:
                words[i] = '*'

        #непосредственно выделяем ключевые слова с помощью нейросети
        for term in term_extractor(line):
            features.append(str(term.normalized))

        #если вдруг к слову присоединилось соседнее (возможно, не ключевое), то удаляем
        for feature in features:
            if len(feature.split(" ")) > 1:
                features.remove(feature)
                features.extend(feature.split(" "))
            else:
                continue

        #приводим слова в запросе к начальной форме (так как ключевые в нач.форме)
        words = [morph.parse(word)[0].normal_form for word in words]

        #ищем слова, означающие единицы времени и количество
        if any(['минута' in words, 'час' in words]):
            time_index = next((i for i, word in enumerate(words) if word == 'minute' or word == 'hour'), None)
            time_units = words[time_index]
            time_value = words[time_index - 1]

        #если образовалось слово, которого нет в изначальном запросе, то удаляем
        for feature in features:
            if not feature in words:
                features.remove(feature)

        #заменяем ненужные слова на '-' и удаляем
        words = [word if any([word in features, word == '*']) else '-' for word in words]
        words = [w for w in words if w != '-']

        #собираем список из фич для каждого подзапроса
        output_list = []
        temp_list = []
        for item in words:
            if item == '*':
                output_list.append(temp_list)
                temp_list = []
            else:
                temp_list.append(item)
        if temp_list:
            output_list.append(temp_list)

        for inner_list in output_list:
            line = ' '.join(inner_list)
            keywords.write(line + '\n')