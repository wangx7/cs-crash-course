# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A collection of **illustrated Chinese rewrites of video courses**: each course becomes a folder of static HTML pages (body text adapted from the course's subtitles, plus hand-drawn SVG diagrams, clickable mini-demos, a summary and a self-test per page). Published with GitHub Pages from the root of `main` at <https://wangx7.github.io/cs-crash-course/>.

No build step. Open `index.html` directly, or serve the repo root with any static file server (e.g. `python3 -m http.server`; `.claude/launch.json` has a "static" server on port 8731).

## Layout

```
index.html            hub: one card per course, rendered from the COURSES array (with read-progress)
404.html              GitHub Pages 404; JS-redirects the pre-2026-11 URLs (root NN.html, dijkstra/, cc.css/js) into cs/ and assets/
assets/cc.css, cc.js  design system + helpers shared by every course
cs/                   Crash Course Computer Science: cs/index.html (TOC) + cs/01.html … cs/47.html
cs/dijkstra/          4-part Dijkstra deep-dive linked from cs/13.html (own dj.css/dj.js)
java/                 黑马 Java 入门到起飞（上部）: java/index.html (TOC) + java/<firstPart>.html
transcripts/<id>/     per-course subtitle text, e.g. transcripts/heima-java-1/ (see "Java course")
tools/bili-transcripts/  Bilibili → transcripts pipeline (subtitle download + local whisper)
```

Every course page sits one level deep, so shared files are always `../assets/cc.css` and `../assets/cc.js`, and the hub is `../index.html`.

## Adding a new course

1. Pick a short folder name (e.g. `java-2/` for 黑马 Java 下部) and a transcript id (e.g. `transcripts/heima-java-2/`). Generate transcripts with `tools/bili-transcripts/` and spot-check them (README step 5) before writing anything.
2. Copy a TOC page (`java/index.html` is the most general: `UNITS` groups parts into pages, `READY` lists written pages) and one or two content pages as templates.
3. Give the course its own mark-read key: `<body data-store="<course>-read">` on every page (cc.js defaults to `cc-read`, which belongs to the crash course), and use the same key in the TOC's progress code.
4. `.topbar` on every page: TOC link + original-video link; the TOC page's topbar links `../index.html` ("← 全部课程").
5. Register the course in the root `index.html` `COURSES` array (href, icon, key, total pages, title, sub, video link).
6. Credit/licence: state the source and its licence in each page's `.credit`. Only courses whose source is CC-licensed (like Crash Course) may claim CC BY-NC-SA; otherwise say "视频版权归原作者所有…仅供个人学习".
7. Add a section for the course to this file (source, transcript notes, page grouping, template specifics), like "Java course" below.

## Content provenance — keep these visually distinct (all courses)

- **正文 (body text)**: adapted from the course's subtitles — follow the source's order and examples, only fixing clear mistakes (mark fixes with the `.fix` span, dotted-underline tooltip).
- **补充 (supplementary) boxes**: anything added beyond the source goes in `.extra` (dashed accent border, auto-prefixed "补充 ·") or `.callout`/`.callout.key`, never silently blended into 正文.
- Each hero header has a `.legend` explaining the 正文/补充 color coding for that page.

## Page architecture (shared by all course pages)

1. `<head>`: page `<title>`, `<link rel="stylesheet" href="../assets/cc.css">`, then a page-specific `<style>` block for that page's one-off classes only (shared classes live in `assets/cc.css`).
2. `<body data-ep="N">` (+ `data-store` for courses other than the crash course) — read by `cc.js` to key the "mark read" localStorage state.
3. `.topbar` — link back to the course TOC + link to the matching video.
4. `.hero` header — eyebrow, Chinese + English title, lead paragraph, a `.road` tag strip (topic progression), `.legend`.
5. `.layout` grid: sticky `nav.toc` (chapter links, auto-highlighted on scroll by `cc.js`) + `<main>` containing a sequence of `<section class="ch" id="chN">`, each starting with a `.ch-num` + `<h2>`.
6. End of main: a `.callout.key` one-line takeaway, a `.card` of self-test `.q` items (click to reveal `.ans`), the `#mark-read` button, a `.pager` (prev/next links to adjacent pages), and a `.credit` attribution footer.
7. `<script src="../assets/cc.js"></script>` then one page-specific inline `<script>`: a `sleep` helper plus one IIFE per interactive demo, in the same order the chapters appear.

## Shared files (`assets/`)

- **`cc.css`**: all shared design tokens (CSS custom properties) and classes. Light theme on `:root`; dark theme via `prefers-color-scheme` (`:root:not([data-theme="light"])`) and an optional manual `:root[data-theme="dark"]` override (not wired to any UI toggle). Logic-gate/transistor SVG classes (`.gate-body`, `.tr-ch`, `.wire`, …) live here because circuit diagrams recur in the crash course.
- **`cc.js`**: `$`/`$$` query shorthands, `el`/`txt` SVG-element builders, `ccStore` (try/catch localStorage wrapper). On `DOMContentLoaded`: TOC scroll-spy, `.q` answer-reveal toggling, and the `#mark-read` button (toggles `data-ep` in the localStorage set named by `data-store`, default `cc-read`). Also exports circuit-diagram primitives (`wire`, `dot`, `gate`, `gatePath`, `inNode`, `lampNode`, `transistor`, `ground`, `setSignals`).

## Crash course (`cs/`)

PBS's *Crash Course Computer Science*, one page per episode (`cs/01.html` … `cs/47.html`), body text from the Chinese fansub (1c7 translation group). Episodes 1–40 follow the original course; 41–47 are original sequel content (not official Crash Course material) covering post-2017 developments, each ending with its own 参考资料 list.

- `.topbar` links `index.html` (the course TOC) + the Bilibili video `BV1ZQ4y1W7rF?p=N+1` (episode 1 is playlist part 2). Uses the default `cc-read` key.
- `cs/index.html`: `EPS` (original 40, `[中文标题, 英文标题]`) and `EXT` (7 sequels) arrays drive rendering; `EXT_READY` lists which sequel episodes are linked. When adding an episode, wire its `.pager` and register it there.
- **`cs/dijkstra/`**: a 4-part supplementary deep-dive on Dijkstra's algorithm (`index.html`, `01.html`–`04.html`), linked from an `.extra` box in `cs/13.html`. Self-contained: its own `dj.css`/`dj.js` (📦 `.boxcard`, 🔍 `details.deep`, scored `quiz()`), not the shared assets; not registered in `EPS`/`EXT`.
- Licence: CC BY-NC-SA 4.0, matching the source — anything added must stay compatible.

## Java course (`java/`)

A second illustrated course in the same style: 黑马程序员《Java 零基础视频教程（上部）：Java 入门到起飞》, Bilibili `BV17F411T7Ao`, 200 parts (~75 h). The user's rule: **pages must be drawn from the video transcripts**, not written from general knowledge — read the transcript for every part a page covers before writing it.

- **Transcripts**: `transcripts/heima-java-1/NNN.txt` (one per part, `[MM:SS] text` lines; committed to the repo, as are `INDEX.tsv` and `pages.json`; `ver.json`, `raw/` and `bad-ai/` stay local), plus `INDEX.tsv` (page, track, lines, last_ts_s, title) and `pages.json`. 106 parts are Bilibili subtitles, 94 are local whisper transcriptions (P32, P33, P70, P82, P86 were re-transcribed because their Bilibili AI subtitles belonged to unrelated videos — if a transcript reads like a different video, check it before writing) (expect homophone errors like 稳定牙→文件夹, Bing→bin; infer from context). Strip timestamps and join lines to read a part as prose: `sed -n '4,$p' NNN.txt | sed 's/^\[[0-9:]*\] //' | tr '\n' ' '`. The pipeline that produced them (and can do the 下部 `BV1yW4y1Y7Ms`) is in `tools/bili-transcripts/`.
- **Pages group adjacent parts by topic** (64 pages; concept pages cover ~30–60 min of video each). The grouping lives in `java/index.html`'s `UNITS` array (`[firstPart, lastPart, title]` per section). File name = first part number, zero-padded to 2 digits (`01.html` = P1–5, `38.html` = P38, `121.html` = P121–123). After finishing a page, add its first part number to `READY` in `java/index.html`. Grouping can be adjusted (e.g. merge exercise/project-heavy units where the transcript is mostly live typing) — update `UNITS` and any affected pagers together.
- **Three tiers** (decided 2026-11-12: the user accepts restructuring the whole Java course; the goal is "能上手公司项目", not a transcript of every minute). A unit's tier is the optional 4th element in `UNITS` and shows as a tag in the TOC:
  - **精讲** (default): concept parts, the full format described below.
  - **题单** (`'drill'`): exercise/project parts (about a quarter of the course's video time, and mostly live typing). One page per chapter instead of one per 2–3 parts. Each problem = `.task` box with the 需求 → the course's analysis in 正文 (keep this visible: "先分析再写" is the point) → `details.fold` 提示 (one idea per fold) → `details.fold` 参考代码 → 易错点 → `.extra`. Tag each problem 必做/选做 (say that the tag is the editor's judgment). A checklist table at the top stores ticks under `hm-java-drill-<firstPart>`. **Exercise parts still hide new concepts** (P91 has printf/%s, P92 has Scanner's two input families): those get their own 精讲 sections inside the drill page, with a demo. Template: `java/91.html`.
  - **略读** (`'skim'`): content with little bearing on backend work (Swing puzzle game, crawler, sorting/search algorithms, collection source code, red-black trees). One short page: what the parts cover, which ideas are worth keeping, when to come back, and a pointer to the video (and to `cs/13.html`/`cs/14.html` where the crash course already covers it). Still read the transcripts first; promote anything that turns out to matter.
  - The tiers for P96–P200 were assigned from part titles only. Confirm or change a unit's tier after reading its transcripts, and update `UNITS`, the pagers and the hub's `total` together.
  - The already-written exercise pages (49, 58, 74, 77) are still in the long form; convert them only if the user asks.
  - **Reference code must be run, not just written**: keep the Java sources in the scratchpad, compile and run them with the local JDK 8 (`/usr/libexec/java_home -V`), and paste the tested source into the page (the 91 page was generated from the tested files). Data that the video takes from course materials rather than reading aloud (e.g. the kung-fu strings in P91) is not in the transcripts: say so on the page and mark what was filled in.
- **Templates**: `java/01.html` and `java/38.html`. Same page architecture as above, with these differences:
  - links `../assets/cc.css` / `../assets/cc.js`; `<body data-ep="<firstPart>" data-store="hm-java-read">` (separate mark-read key from the crash course);
  - `.topbar`: `<a href="index.html">← Java 入门到起飞 · 图文版</a>` + video link `https://www.bilibili.com/video/BV17F411T7Ao?p=<firstPart>` (no +1 offset here);
  - `.hero` eyebrow `<section> · P<a>–P<b>`; `.legend` uses 正文 = "据视频字幕提炼重写，讲解顺序和例子来自原课" and 蓝色虚线框 = "视频之外的补充 / 更正";
  - each `.ch-num` names the part(s) it comes from (`第 3 节 · P3`);
  - `.pager` says 上一篇/下一篇 with the adjacent unit's range + title; use `class="off"` (no href) for pages not yet written, and when writing a page, also turn on the link in its already-written neighbours;
  - `.credit`: content source (course name, part range, Bilibili link, "视频版权归原作者所有"), then "正文是看视频字幕后提炼、重写的学习笔记，讲解思路和例子来自原课；示意图、交互演示与"补充"框由 Claude Opus 5.5 制作。仅供个人学习，非官方内容。" (name the model that actually wrote the page) — do **not** claim CC BY-NC-SA for this course (the source isn't CC-licensed).
- **Content rules**:
  - 正文 = reorganized notes in our own words following the video's teaching order and examples; skip ads, greetings, sign-offs and filler; never paste long transcript stretches verbatim.
  - Anything the video doesn't say goes in `.extra` boxes; when the video is wrong, keep the video's claim visible, mark it with a `.fix` span, and give the correction in an `.extra` titled "更正：…" (e.g. 01.html: `dir` doesn't show hidden files — the extra two entries are `.`/`..`).
  - The user is on a Mac; where the video is Windows-specific, a short Mac equivalent in an `.extra` is welcome.
  - **IDEA menus and buttons are written in Chinese, matching IntelliJ IDEA 2026.1 with the Chinese language pack** (the user's IDE) — never the English labels the video shows (`文件 → 项目结构… → 模块`, `新建 → 软件包`, `重构 → 重命名…`, `设置… → 插件`, `生成… → 构造函数`, `确定`/`应用`/`创建`). Don't guess a translation: look it up in the bundled pack, `unzip` `/Applications/IntelliJ IDEA.app/Contents/plugins/localization-zh/lib/localization-zh.jar` into the scratchpad and grep the `.properties` files (e.g. `action.RenameElement.text=重命名(_R)…`; drop the `(_R)` mnemonic). Names that stay English in the Chinese UI stay English (Marketplace, Getter, Setter, plugin names such as PTG), as do shortcuts, live templates (`psvm`, `sout`) and code. This applies to every course that shows IDEA operations.
  - Exercise/project parts: present problem → analysis (the course stresses "先分析需求再写代码") → reference code → pitfalls, so the reader can try it first; don't just dump final code.
  - Interactive demos should let the reader *do* the thing being taught (01.html: simulated CMD window + PATH lookup; 38.html: step-by-step binary adder, bit explorer). Verify every page in the browser (`.claude/launch.json` "static" server on port 8731) and check example outputs are actually correct.

