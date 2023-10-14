from rutermextract import TermExtractor
term_extractor = TermExtractor()
text = []
text.append(u'я хочу съездить в кафе за пятьсот метров от меня на велосипеде и потом заехать в ближайший парк и обратно')
text.append(u'хотел бы покататься на самокате и заехать в ближайший вкусно и точка')
text.append(u'хочу прогуляться по лесу прогулка должна составить полчаса')
text.append(u'велопоездка по красивой набережной')
text.append(u'съездить в ближайший магазин магнит и потом заехать в пункт выдачи озон')
text.append(u'съездить в додо пиццу в ломоносове на велосипеде из деревни дубки')
text.append(u'я хочу покататься на велосипеде по ровной дороге в воскресенье и я хочу где-нибудь поесть')

for t in text:
    for term in term_extractor(t):
        print(str(term.normalized), str(term.count))
    print('-----------')