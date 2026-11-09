# B 站合集 → 逐集字幕文本

把一个 B 站多 P 合集变成 `transcripts/<课程>/001.txt … NNN.txt`，供写图文版时"从字幕里汲取知识"。逐集的 `NNN.txt`、`INDEX.tsv`、`pages.json` 会提交到仓库，换电脑 clone 下来就能接着写；`ver.json`（含 B 站签名链接）、`raw/`（原始 JSON）、`bad-ai/`（挂错的别家视频字幕）在 `.gitignore` 里，只留本地。字幕内容版权归原作者。

已完成：`transcripts/heima-java-1/`（BV17F411T7Ao，200 集全齐）。还没做：下部 BV1yW4y1Y7Ms。

## 输出格式

```
# P38 运算符-12-多学一招原码反码补码
# track: zh-CN  cid: 123456

[00:01] 第一行字幕
[00:04] 第二行字幕
```

`track` 是字幕来源：`zh-CN`（UP 主上传）、`zh-Hans`、`ai-zh`（B 站 AI 字幕）、`whisper-large-v3-turbo (local)`（本地转写）。
目录里还有 `INDEX.tsv`（每集一行：page, track, lines, last_ts_s, title）、`pages.json`（分 P 列表）、`raw/`（原始字幕 JSON）。

## 流程

1. **收集字幕地址**（需要登录 B 站）：在 Claude 内置浏览器里打开 bilibili.com 并登录，改好 `collect_subs.js` 里的 `BVID`，用 javascript_tool 运行它；跑完后按文件末尾注释导出 `pages.json` 和 `ver.json`。
   - B 站会给很多分 P 挂错字幕或空白占位字幕，脚本按"字幕时长覆盖视频时长"来筛，详见脚本开头注释。
   - 浏览器页面不能把数据 POST 到 localhost，只能从 javascript_tool 的返回值里拿 JSON，再写成文件。
2. **下载字幕**：`python3 dl_subs.py ver.json pages.json OUT_DIR`（auth_key 几小时就过期，导出后尽快跑）。
3. **本地转写缺的集**（Apple Silicon）：
   ```bash
   python3.12 -m venv venv && venv/bin/pip install yt-dlp mlx-whisper
   caffeinate -i venv/bin/python transcribe.py BVID OUT_DIR /tmp/audio OUT_DIR/pages.json 5 6 9 …
   ```
   yt-dlp 下 B 站音频不需要登录。第一次会下载约 1.6 GB 的模型。M1 上约 10 倍速，heima-java-1 的 89 集（约 30 小时）跑了约 3 小时。换课程时改一下 `PROMPT` 里的术语。
4. **检查并重建索引**：`python3 build_index.py OUT_DIR OUT_DIR/pages.json`，会列出缺失、覆盖不足和疑似重复循环的集数。
5. **抽查内容是否对得上**：时长筛选挡不住"时长差不多、内容完全是别的视频"的 AI 字幕。heima-java-1 里 P32、P33、P70、P82、P86 的 `ai-zh` 字幕就是别的视频（明星、电影、游戏、手机测评……），已移到 `bad-ai/` 并用本地 whisper 重转。简单的检查办法：统计每集里课程术语出现的次数，特别少的打开看看。
   ```bash
   cd OUT_DIR && for f in $(awk -F'\t' '$2=="ai-zh"{printf "%03d.txt\n",$1}' INDEX.tsv); do echo "$f $(grep -o -E 'Java|java|代码|变量|方法|数组|对象|idea|字符串|类' $f | wc -l)"; done | sort -k2 -n | head
   ```
