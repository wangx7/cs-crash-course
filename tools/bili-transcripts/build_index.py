"""检查一个字幕目录并重建 INDEX.tsv。

用法：python3 build_index.py OUT_DIR [PAGES_JSON]
  - 重写 OUT_DIR/INDEX.tsv：page, track, lines, last_ts_s, title
  - 给了 PAGES_JSON 时，再报告：缺哪些集；覆盖不足（最后时间戳 < 0.85 × 时长）的集；
    疑似重复循环（同一行连续出现 ≥ 5 次）的集。口头禅（"好""OK"）连说几次属于正常。
"""
import collections, json, os, re, sys

D = sys.argv[1]
pages = {p["page"]: p for p in json.load(open(sys.argv[2]))} if len(sys.argv) > 2 else {}
rows, flags = [], []
for f in sorted(x for x in os.listdir(D) if re.fullmatch(r"\d{3}\.txt", x)):
    L = open(os.path.join(D, f)).read().splitlines()
    n = int(f[:3])
    head = L[0].split(" ", 2)
    title = head[2] if len(head) > 2 else ""
    track = L[1].split()[2]
    track = "whisper" if track.startswith("whisper") else track
    body = [l for l in L[3:] if l.startswith("[")]
    last = 0
    if body:
        m, s = map(int, re.match(r"\[(\d+):(\d+)\]", body[-1]).groups())
        last = m * 60 + s
    rows.append((n, track, len(body), last, title))
    texts = [l.split("] ", 1)[1] if "] " in l else "" for l in body]
    run = mx = 1
    for a, b in zip(texts, texts[1:]):
        run = run + 1 if a == b else 1
        mx = max(mx, run)
    if n in pages and (last < 0.85 * pages[n]["duration"] or mx >= 5):
        flags.append((n, track, f"cov={last / pages[n]['duration']:.2f}", f"maxrun={mx}"))

with open(os.path.join(D, "INDEX.tsv"), "w") as o:
    o.write("page\ttrack\tlines\tlast_ts_s\ttitle\n")
    for r in rows:
        o.write("\t".join(map(str, r)) + "\n")
print(len(rows), "files", dict(collections.Counter(r[1] for r in rows)), f"{sum(r[3] for r in rows) / 3600:.1f}h")
if pages:
    print("missing:", sorted(set(pages) - {r[0] for r in rows}))
    for x in flags:
        print("flag:", x)
