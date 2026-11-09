"""用 yt-dlp 下载分 P 音频，再用 mlx-whisper 在本机（Apple Silicon GPU）转写。

用法：venv/bin/python transcribe.py BVID OUT_DIR AUDIO_DIR PAGES_JSON P1 P2 ...
  例：venv/bin/python transcribe.py BV17F411T7Ao ../../transcripts/heima-java-1 /tmp/audio pages.json 5 6 9
输出格式与 dl_subs.py 相同（track 标为 whisper-large-v3-turbo (local)）。
已存在的 NNN.txt 会跳过，所以中途停掉后重跑即可从断点继续；音频转完即删。
建议外面套 `caffeinate -i` 防止系统睡眠。M1 上约 10 倍速（1 小时音频约 6 分钟）。
"""
import json, os, subprocess, sys, time

bvid, out_dir, audio_dir, pages_json = sys.argv[1:5]
todo = [int(x) for x in sys.argv[5:]]
here = os.path.dirname(os.path.abspath(sys.executable))
ytdlp = os.path.join(here, "yt-dlp")  # 与 python 同一个 venv/bin
titles = {p["page"]: p["part"] for p in json.load(open(pages_json))}
MODEL = "mlx-community/whisper-large-v3-turbo"
# 简体中文的提示词：引导输出简体、把常见术语写对。换课程时改成该课的术语。
PROMPT = "以下是普通话的Java编程课程，讲解JDK、IDEA、变量、数据类型、byte、int、long、double、运算符、if、switch、for循环、while循环、数组、方法、面向对象、类、对象、String、StringBuilder、ArrayList、static、继承、多态。"

import mlx_whisper  # noqa: E402

os.makedirs(audio_dir, exist_ok=True)
for page in todo:
    dst = os.path.join(out_dir, f"{page:03d}.txt")
    if os.path.exists(dst):
        print(page, "exists, skip", flush=True)
        continue
    audio = os.path.join(audio_dir, f"{page:03d}.m4a")
    t0 = time.time()
    if not os.path.exists(audio):
        r = subprocess.run([ytdlp, "-q", "--no-warnings", "-f", "bestaudio", "-o", audio,
                            f"https://www.bilibili.com/video/{bvid}?p={page}"], capture_output=True, text=True)
        if r.returncode != 0 or not os.path.exists(audio):
            print(page, "DOWNLOAD FAIL", r.stderr[-300:], flush=True)
            continue
    t1 = time.time()
    # condition_on_previous_text=False：避免 whisper 常见的整段重复循环
    res = mlx_whisper.transcribe(audio, path_or_hf_repo=MODEL, language="zh", initial_prompt=PROMPT,
                                 condition_on_previous_text=False, verbose=None)
    t2 = time.time()
    segs = res.get("segments", [])
    with open(dst + ".part", "w") as f:
        f.write(f"# P{page} {titles.get(page, '')}\n# track: whisper-large-v3-turbo (local)  cid: 0\n\n")
        for s in segs:
            text = s["text"].strip()
            if not text:
                continue
            m, sec = divmod(int(s["start"]), 60)
            f.write(f"[{m:02d}:{sec:02d}] {text}\n")
    os.replace(dst + ".part", dst)
    os.remove(audio)
    last = int(segs[-1]["end"]) if segs else 0
    print(f"{page} ok segs={len(segs)} last={last}s dl={t1-t0:.0f}s asr={t2-t1:.0f}s", flush=True)
print("ALL DONE", flush=True)
