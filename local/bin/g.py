import re,sys
s=open(sys.argv[1],encoding='utf-8').read()
n=int(sys.argv[3]) if len(sys.argv)>3 else 2
for m in list(re.finditer(sys.argv[2],s))[:n]:
    print('…'+s[max(0,m.start()-220):m.end()+260]+'…\n')
print('---')
