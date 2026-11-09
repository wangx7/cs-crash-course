"""下载 collect_subs.js 验证过的字幕，每集写成一个 NNN.txt。

ver.json：[[page, lan, path, last_to_s], ...]
  path 是 aisubtitle.hdslb.com/bfs/ 之后的部分（含 ?auth_key=...）；
  也兼容简写前缀 "A/" = "ai_subtitle/prod/"、"S/" = "subtitle/"。
pages.json：[{page, part, cid, duration}, ...]

用法：python3 dl_subs.py ver.json pages.json OUT_DIR
输出：OUT_DIR/NNN.txt（格式见 README），原始 JSON 存到 OUT_DIR/raw/NNN.<lan>.json。
已存在的 NNN.txt 会被覆盖；没有字幕的集数不生成文件，交给 transcribe.py。
写完后运行 build_index.py 重建 INDEX.tsv。
"""
import json, os, subprocess, sys, time

ver_path, pages_path, out = sys.argv[1], sys.argv[2], sys.argv[3]
pages = {p["page"]: p for p in json.load(open(pages_path))}
os.makedirs(os.path.join(out, "raw"), exist_ok=True)


def expand(path):
    if path.startswith("A/"):
        return "ai_subtitle/prod/" + path[2:]
    if path.startswith("S/"):
        return "subtitle/" + path[2:]
    return path


def fetch(path):
    # 用 curl：python.org 版 Python 在 macOS 上常因证书报 CERTIFICATE_VERIFY_FAILED
    url = "https://aisubtitle.hdslb.com/bfs/" + expand(path)
    for _ in range(3):
        r = subprocess.run(["curl", "-s", "-f", "-m", "20", "-A", "Mozilla/5.0",
                            "-H", "Referer: https://www.bilibili.com/", url], capture_output=True)
        if r.returncode == 0:
            return json.loads(r.stdout.decode("utf-8"))
        time.sleep(1)
    raise RuntimeError(f"curl rc={r.returncode}")


ok = 0
for page, lan, path, _ in json.load(open(ver_path)):
    try:
        data = fetch(path)
    except Exception as e:
        print(page, lan, "FAIL", e, file=sys.stderr)
        continue
    json.dump(data, open(os.path.join(out, "raw", f"{page:03d}.{lan}.json"), "w"), ensure_ascii=False)
    p = pages.get(page, {})
    with open(os.path.join(out, f"{page:03d}.txt"), "w") as f:
        f.write(f"# P{page} {p.get('part', '')}\n# track: {lan}  cid: {p.get('cid', 0)}\n\n")
        for seg in data.get("body", []):
            m, s = divmod(int(seg["from"]), 60)
            f.write(f"[{m:02d}:{s:02d}] {seg['content']}\n")
    ok += 1
    time.sleep(0.1)

have = {int(x[:3]) for x in os.listdir(out) if x[:3].isdigit() and x.endswith(".txt")}
print("downloaded", ok, "| still missing:", sorted(set(pages) - have))
