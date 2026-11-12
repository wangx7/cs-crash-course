import re,sys,html
raw=open(sys.argv[1],encoding='utf-8',errors='ignore').read()
txt=re.sub(r'\s+',' ',html.unescape(re.sub(r'<[^>]+>',' ',raw)))
for pat in sys.argv[2:]:
    # find position in raw by searching a regex over raw with tags removed progressively: approximate via first word sequence
    words=pat.split()
    rx=r'(?:<[^>]+>|\s)*'.join(re.escape(w) for w in words)
    m=re.search(rx,raw)
    if not m: print('NOTFOUND',pat); continue
    ids=re.findall(r'(?:id|name)="(jvms-[\d.]+[^"]*|jls-[\d.]+[^"]*)"',raw[:m.start()])
    print(pat[:50],'=>',ids[-1] if ids else None)
