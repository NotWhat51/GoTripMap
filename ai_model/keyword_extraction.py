from rutermextract import TermExtractor
term_extractor = TermExtractor()

with open("request.txt", "r") as request, open("keywords.txt", "w") as keywords:
    for line in request.readlines():
        for term in term_extractor(line):
            keywords.writelines(str(term.normalized) + ' ' + str(term.count) + '\n')

#JSON?
