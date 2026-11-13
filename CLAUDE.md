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
java/                 Java 后端 · 图文课 (original course, no video): java/index.html (TOC) + java/<part>-<n>.html
transcripts/<id>/     per-course subtitle text: heima-java-1/, heima-javaweb/ (see "Java")
docs/java-roadmap.md  黑马 Java learning roadmap + the user's target stack
tools/bili-transcripts/  Bilibili → transcripts pipeline (subtitle download + local whisper)
local/                course-writing materials: setup.sh rebuilds the gitignored third-party copies (see "Java")
```

Every course page sits one level deep, so shared files are always `../assets/cc.css` and `../assets/cc.js`, and the hub is `../index.html`.

## Adding a new course

1. Pick a short folder name (e.g. `java-2/` for 黑马 Java 下部) and a transcript id (e.g. `transcripts/heima-java-2/`). Generate transcripts with `tools/bili-transcripts/` and spot-check them (README step 5) before writing anything.
2. Copy a TOC page and one or two content pages as templates. `cs/index.html` is one page per episode; for a course whose pages each group several video parts, the removed Java course's TOC is the most general (`UNITS` groups parts into pages, `READY` lists written pages): `git show 229972e:java/index.html`.
3. Give the course its own mark-read key: `<body data-store="<course>-read">` on every page (cc.js defaults to `cc-read`, which belongs to the crash course), and use the same key in the TOC's progress code.
4. `.topbar` on every page: TOC link + original-video link; the TOC page's topbar links `../index.html` ("← 全部课程").
5. Register the course in the root `index.html` `COURSES` array (href, icon, key, total pages, title, sub, video link).
6. Credit/licence: state the source and its licence in each page's `.credit`. Only courses whose source is CC-licensed (like Crash Course) may claim CC BY-NC-SA; otherwise say "视频版权归原作者所有…仅供个人学习".
7. Add a section for the course to this file (source, transcript notes, page grouping, template specifics), like "Crash course" below.

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

## Java backend course (`java/`)

An **original** course (no source video, no transcripts), written for a frontend developer who wants to reach the core of backend work and get onto a BladeX microservice project. Outline, evidence and open questions: `docs/java-course-outline.md` (11 parts, about 86 pages). Written **one page at a time**: show each page to the user and wait for their reaction before writing the next; record their feedback here.

- **Files**: `java/<part>-<n>.html` (`0-1.html` = 0.1). `<body data-ep="<part*100+n>" data-store="java-be-read" data-unit="篇">` (0.1 → 1, 3.11 → 311). `java/index.html` holds `PARTS` (titles) and `READY` (written pages, as `'0-1'`); when a page is written, add it to `READY` and turn on the pagers of its neighbours. The hub's `COURSES` entry has `total: 86`; keep it in step with `PARTS`.
- **No fixed inner template.** Keep the navigation skeleton (`.topbar`, `.hero`, `nav.toc` + `section.ch`, `#mark-read`, `.pager`, `.credit`), but let the form of each page follow its content, and make each page differ in form from the one before. Don't use `.extra`/`.legend` (they mean "added beyond the source video", which doesn't apply here).
- **Every page must have**: something the reader predicts or does before the explanation; at least one point where a programmer's intuition gives the wrong answer; nothing every programmer already knows; real code with file and line cited, or code that was actually run (JDK 8). Data that is inferred rather than observed is labelled "示意", and anything not checked against source is listed on the page.
- **新旧两条线（user, 2026-11-13）**: 正文一律按 Java 8（公司栈）实测；每节末尾用一个「新版补充」callout 单列新版本在这条规则上加了什么（如 var = JEP 286 / Java 10，箭头 switch = JEP 361 / Java 14）。新语法在本机没有对应 JDK 时只依据 JEP 文档写、页面上注明未实测；"某规则到最新版也未变"的说法必须核对最新版规范原文（如 JLS SE 25 §10.2/§10.4）再写。
- **Every page ends with a 参考资料 section** (user rule, 2026-11-12: "防止咱们写着写着沉迷在自己的世界无法自拔"). Group it as source code / official docs / specs; after each link say which sentence or step it supports ("支撑：…"). Open every link and read the passage before listing it (`curl` + grep for the sentence), and use the references to check the text, not just decorate it: on 0.1 this caught a self-test answer that overstated when a Feign fallback runs. Say on the page which claims have no source and are inference. Pin versions that match the stack (Spring Framework 5.3.x, Spring Boot 2.7.18, Spring Cloud Gateway/OpenFeign 3.1.x, JLS SE 8).
- **No glib sentences.** The user rejected the first draft's lead ("你熟悉这两头，中间那一段就是后端") as "信口雌黄": "两头" was undefined, "the middle is the backend" was false, and "两百毫秒" was invented. Every sentence must say something specific and true: name the things (which programs, which file), no invented numbers, no sweeping claims such as "后端的日常是…" or "大部分错误发生在…". Before showing a page, reread the hero lead, section openers and summary callout for this.
- **Real-code source**: the open-source SpringBlade **v3.7.1** (`git clone --depth 1 --branch v3.7.1 https://github.com/chillzhuang/SpringBlade`, Apache-2.0; the last Java 8 release: Spring Boot 2.7.18, Spring Cloud 2021.0.8, Nacos). A clone is at `local/springblade-3.7.1/`; cite paths and line numbers, and credit it in `.credit`. Never use or describe BladeX's proprietary source.
- **Credit**: "本篇是原创内容，由 <the model that wrote it> 写作…"; quoted SpringBlade code stays under Apache-2.0, the rest follows the repo's CC BY-NC-SA 4.0.
- **Local materials (`local/`; user, 2026-11-12: keep tools and sources on disk so later pages don't redo the work, and be able to continue on another computer)**. Committed: `setup.sh`, `bin/`, `refs/SOURCES.tsv`, `examples/<page>/` (the code each page ran, with a README). Gitignored, because they are other people's copyrighted material and the repo root is served publicly by Pages: `springblade-3.7.1/` (the clone), `idea-zh/` (IDEA 2026.1 Chinese `.properties`), `refs/*.html` and `refs/*.txt` (fetched docs plus tag-stripped text). **On a new computer, run `bash local/setup.sh`**: it installs Corretto 8 if JDK 8 is missing, clones SpringBlade, unpacks the IDEA pack and downloads every URL in `SOURCES.tsv`. Search with `python3 -I local/bin/g.py local/refs/x.txt 'regex'` (the shell's `grep` is ugrep and chokes on long lines); `bin/sec.py` finds the spec anchor id before a sentence. When a page cites a new document, add it to `SOURCES.tsv` and fetch it with `setup.sh`; still re-open a reference before citing it.
- Template for tone and structure: `java/0-1.html` (guess-first questions, a 12-step request tracer, a live `JSON.parse` demo, a clickable code reader).

### Background: the removed 黑马 course and the user's requirements


The illustrated version of 黑马《Java 入门到起飞（上部）》 lived in `java/` (29 pages covering P1–P95, plus a TOC) and was **deleted at the user's request**. Reason: the course is for absolute beginners, the user already has a frontend background, and found the pages tedious. Don't restore or extend it (the new course reuses the `java/` folder name but shares nothing with it). The last commit that has it is `229972e` (e.g. `git show 229972e:java/91.html`).

Still in the repo:

- `transcripts/heima-java-1/` (上部, 200 parts) and `transcripts/heima-javaweb/`: `NNN.txt` with `[MM:SS] text` lines, plus `INDEX.tsv` and `pages.json`. A mix of Bilibili subtitles and local whisper output, so expect homophone errors (稳定牙→文件夹, Bing→bin). Read a part as prose with `sed -n '4,$p' NNN.txt | sed 's/^\[[0-9:]*\] //' | tr '\n' ' '`.
- `docs/java-roadmap.md`: the 黑马 roadmap, the goal ("学完能上手公司项目") and the target stack (Java 8, Spring Boot 2, Spring Cloud, MyBatis-Plus, 若依, Redis, Vue 2). Correction from the user (2026-11-12): at their company only one old operations project uses 若依 (若依 backend + layui frontend); the roadmap still lists the 若依 course as required, which overstates it. Don't anchor teaching material on 若依: it goes last, as an optional page. The user says most of their backend "好像是" **BladeX** (the commercial sibling of the open-source SpringBlade, `chillzhuang/SpringBlade` + `blade-tool`; Nacos, Feign, Gateway, MyBatis-Plus, Redis). BladeX's source is not public, and it is the Cloud (microservice) edition with several dozen services (user, 2026-11-12); the version is not confirmed yet: check against the user's `pom.xml` before describing its code conventions as fact.

**Audience for whatever replaces it (user, 2026-11-12; this is the wording after the user corrected it twice): a frontend developer, not a beginner.**

- Don't teach what every programmer knows (variables, operators, branches, loops, functions, arrays), and skip dated topics with no bearing on backend work (Swing and the like).
- Frontend knowledge may be referred to **lightly**: a clause in passing, where it is the shortest explanation. It is never the main line or the skeleton of a page. The user says an earlier attempt that wrote everything "按前端对照" was a problem too.
- The main line is the Java backend itself, ordered by what the target project (see `docs/java-roadmap.md`) actually needs.

What replaces it: a full Java backend course, outline drafted in `docs/java-course-outline.md` (about 85 pages in 11 parts; **direction and length approved by the user on 2026-11-12**; written one page at a time, each shown to the user before the next). The user's requirements for it (2026-11-12): "从开始到结束、有理有据、走到后端的核心、不要求速成、如沐春风"; an earlier 22-page outline was rejected as too thin. The outline's evidence includes counts from the open-source SpringBlade v3.7.1 (the last Java 8 release); `blade-tool` was not analysed (the user interrupted that download, so ask before downloading it). The user's stated worry about any replacement written entirely by Claude: it gets narrower and more mechanical page after page. Agree with the user on how quality is checked before writing pages in bulk, and don't batch-write.

Rules that hold for any future Java material:

- The user is on a Mac with IntelliJ IDEA 2026.1 and the Chinese language pack. **Write IDEA menus and buttons in Chinese as that IDE shows them**, never English labels (`文件 → 项目结构… → 模块`, `新建 → 软件包`, `重构 → 重命名…`). Don't guess a translation: grep the unpacked pack in `local/idea-zh/` (from `/Applications/IntelliJ IDEA.app/Contents/plugins/localization-zh/lib/localization-zh.jar`; re-unzip it there after an IDEA upgrade) and drop the `(_R)` mnemonic. Names that stay English in the Chinese UI stay English (Marketplace, Getter, Setter), as do shortcuts, live templates and code.
- **Code on a page must be run, not just written**: compile and run it with the local JDK 8 (`/usr/libexec/java_home -V`) and paste the tested source. JDK 8 is Amazon Corretto 1.8.0_512 at `~/Library/Java/JavaVirtualMachines/corretto-1.8.0_512` (`$(/usr/libexec/java_home -v 1.8)/bin/javac`). Since 2026-11-13 the PATH default is Temurin 8 (1.8.0_502, x86_64) and JDK 25 is no longer installed — 0.2's JDK 25 outputs were measured before the removal; don't make new claims that need a newer JDK without installing one. Paste terminal output exactly as run.
- Don't claim CC BY-NC-SA for anything derived from the 黑马 videos (not CC-licensed): "视频版权归原作者所有…仅供个人学习".
- Verify every page in the browser (`.claude/launch.json` "static" server on port 8731) and check that example outputs are correct.
