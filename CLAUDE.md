# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A graphic/interactive-illustrated rewrite of PBS's *Crash Course Computer Science*, in Chinese, one static HTML page per episode (`01.html` … `47.html`): body text adapted from the Chinese fansub (1c7 translation group), plus hand-drawn SVG diagrams and clickable mini-demos, a chapter summary, and a self-test per episode. Episodes 1–40 follow the original course; 41–47 are original sequel content (not official Crash Course material) covering post-2017 developments.

No build step. Open `index.html` directly, or serve the directory with any static file server (e.g. `python3 -m http.server`) for correct relative paths.

## Content provenance — keep these visually distinct

- **正文 (body text)**: adapted from the course's Chinese subtitles — treat as close to source, only fixing clear mistranslations (mark fixes with the `.fix` span, dotted-underline tooltip).
- **补充 (supplementary) boxes**: anything added beyond the subtitle content goes in `.extra` (dashed accent border, auto-prefixed "补充 ·") or `.callout`/`.callout.key`, never silently blended into 正文.
- Each hero header has a `.legend` explaining the 正文/补充 color coding for that page.
- Sequel episodes (41+) end with their own 参考资料 (references) list since they have no source subtitles to cite.

## Page architecture (every `NN.html` follows this shape)

1. `<head>`: page `<title>`, `<link rel="stylesheet" href="cc.css">`, then a page-specific `<style>` block for that episode's one-off SVG/diagram classes only (shared classes live in `cc.css`).
2. `<body data-ep="N">` — the `data-ep` attribute is read by `cc.js` to key the "mark read" localStorage state.
3. `.topbar` — link back to `index.html` + link to the matching Bilibili video (`?p=N+1`, since episode 1 is playlist part 2).
4. `.hero` header — episode number, Chinese + English title, lead paragraph, a `.road` tag strip (topic progression), `.legend`.
5. `.layout` grid: sticky `nav.toc` (chapter links, auto-highlighted on scroll by `cc.js`) + `<main>` containing a sequence of `<section class="ch" id="chN">`, each starting with a `.ch-num` + `<h2>`.
6. End of main: a `.callout.key` one-line takeaway, a `.card` of self-test `.q` items (click to reveal `.ans`), the `#mark-read` button, a `.pager` (prev/next links to adjacent `NN.html`), and a `.credit` attribution footer.
7. `<script src="cc.js"></script>` then one page-specific inline `<script>`: a `sleep` helper plus one IIFE per interactive demo, each grabbing its `<svg id="...">` placeholder from the chapter markup and building it with `cc.js`'s SVG helpers, in the same order the chapters appear.

When adding a new episode, wire its `.pager` to the real adjacent files, and register it in `index.html`'s `EPS` array (original 40) or `EXT` + `EXT_READY` arrays (sequels — `EXT_READY` controls whether the entry links to a page yet or shows "撰写中").

## Shared files

- **`cc.css`**: all shared design tokens (CSS custom properties) and classes. Light theme on `:root`; dark theme via `prefers-color-scheme` (`:root:not([data-theme="light"])`) and an optional manual `:root[data-theme="dark"]` override — the override attribute isn't currently wired to any UI toggle, pages just follow the system theme. Logic-gate/transistor SVG classes (`.gate-body`, `.tr-ch`, `.wire`, …) are shared here because circuit diagrams recur from episode 3 onward.
- **`cc.js`**: `$`/`$$` query shorthands, `el`/`txt` SVG-element builders, `ccStore` (try/catch localStorage wrapper). On `DOMContentLoaded`: TOC scroll-spy highlighting, `.q` answer-reveal toggling, and the `#mark-read` button (toggles the episode number in the `cc-read` localStorage set, read back by `index.html`'s progress bar). Also exports the reusable circuit-diagram primitives (`wire`, `dot`, `gate`, `gatePath`, `inNode`, `lampNode`, `transistor`, `ground`, `setSignals`) used by per-episode demo IIFEs for anything gate/transistor-shaped.
- **`index.html`**: table of contents. `EPS` (original 40, `[中文标题, 英文标题]`) and `EXT` (7 sequels) arrays drive rendering; `EXT_READY` lists which sequel episode numbers are actually linked. Read/progress state comes from the same `cc-read` localStorage key `cc.js` writes.

## Topic deep-dives

- **`dijkstra/`**: a 4-part supplementary deep-dive on Dijkstra's algorithm (`index.html`, `01.html`–`04.html`), linked from an `.extra` box in `13.html`. It is self-contained: its own `dj.css`/`dj.js` (styled after the user's "从加法器到CPU" study page — 📦 `.boxcard`, 🔍 `details.deep`, scored `quiz()`), not `cc.css`/`cc.js`, and it is not registered in `index.html`'s `EPS`/`EXT` arrays.

## Java course (`java/`)

A second illustrated course in the same style: 黑马程序员《Java 零基础视频教程（上部）：Java 入门到起飞》, Bilibili `BV17F411T7Ao`, 200 parts (~75 h). The user's rule: **pages must be drawn from the video transcripts**, not written from general knowledge — read the transcript for every part a page covers before writing it.

- **Transcripts**: `transcripts/heima-java-1/NNN.txt` (one per part, `[MM:SS] text` lines; gitignored, local only), plus `INDEX.tsv` (page, track, lines, last_ts_s, title) and `pages.json`. 111 parts are Bilibili subtitles, 89 are local whisper transcriptions (expect homophone errors like 稳定牙→文件夹, Bing→bin; infer from context). Strip timestamps and join lines to read a part as prose: `sed -n '4,$p' NNN.txt | sed 's/^\[[0-9:]*\] //' | tr '\n' ' '`. The pipeline that produced them (and can do the 下部 `BV1yW4y1Y7Ms`) is in `tools/bili-transcripts/`.
- **Pages group adjacent parts by topic** (~30–60 min of video each, 71 pages). The grouping lives in `java/index.html`'s `UNITS` array (`[firstPart, lastPart, title]` per section). File name = first part number, zero-padded to 2 digits (`01.html` = P1–5, `38.html` = P38, `121.html` = P121–123). After finishing a page, add its first part number to `READY` in `java/index.html`. Grouping can be adjusted (e.g. merge exercise/project-heavy units where the transcript is mostly live typing) — update `UNITS` and any affected pagers together.
- **Templates**: `java/01.html` and `java/38.html`. Same architecture as the episode pages above, with these differences:
  - links `../cc.css` / `../cc.js`; `<body data-ep="<firstPart>" data-store="hm-java-read">` (separate mark-read key from the crash course);
  - `.topbar`: `<a href="index.html">← Java 入门到起飞 · 图文版</a>` + video link `https://www.bilibili.com/video/BV17F411T7Ao?p=<firstPart>` (no +1 offset here);
  - `.hero` eyebrow `<section> · P<a>–P<b>`; `.legend` uses 正文 = "据视频字幕提炼重写，讲解顺序和例子来自原课" and 蓝色虚线框 = "视频之外的补充 / 更正";
  - each `.ch-num` names the part(s) it comes from (`第 3 节 · P3`);
  - `.pager` says 上一篇/下一篇 with the adjacent unit's range + title; use `class="off"` (no href) for pages not yet written, and when writing a page, also turn on the link in its already-written neighbours;
  - `.credit`: content source (course name, part range, Bilibili link, "视频版权归原作者所有"), then "正文是看视频字幕后提炼、重写的学习笔记，讲解思路和例子来自原课；示意图、交互演示与"补充"框由 Claude Opus 5.5 制作。仅供个人学习，非官方内容。" — do **not** claim CC BY-NC-SA for this course (the source isn't CC-licensed).
- **Content rules**:
  - 正文 = reorganized notes in our own words following the video's teaching order and examples; skip ads, greetings, sign-offs and filler; never paste long transcript stretches verbatim.
  - Anything the video doesn't say goes in `.extra` boxes; when the video is wrong, keep the video's claim visible, mark it with a `.fix` span, and give the correction in an `.extra` titled "更正：…" (e.g. 01.html: `dir` doesn't show hidden files — the extra two entries are `.`/`..`).
  - The user is on a Mac; where the video is Windows-specific, a short Mac equivalent in an `.extra` is welcome.
  - Exercise/project parts: present problem → analysis (the course stresses "先分析需求再写代码") → reference code → pitfalls, so the reader can try it first; don't just dump final code.
  - Interactive demos should let the reader *do* the thing being taught (01.html: simulated CMD window + PATH lookup; 38.html: step-by-step binary adder, bit explorer). Verify every page in the browser (`.claude/launch.json` "static" server on port 8731) and check example outputs are actually correct.

## License

CC BY-NC-SA 4.0, matching the source course — any content added must stay compatible (attribution, non-commercial, share-alike).
