# 图文版课程

把视频课程整理成一课一页的图文网页：正文据原课字幕整理，配手绘示意图，关键处附可以动手点的小演示，每篇末尾有小结和自测题。

在线阅读：<https://wangx7.github.io/cs-crash-course/>

## 目前的课程

| 目录 | 课程 | 说明 |
| --- | --- | --- |
| `cs/` | 计算机科学速成课（Crash Course Computer Science） | 原课 40 集逐集整理 + 7 集原创续集（2017 年之后的新变化）；`cs/dijkstra/` 是 Dijkstra 算法专题 |
| `java/` | Java 后端 · 图文课 | 原创课，没有对应视频。从一个请求讲起，走到语言、并发、数据库，再到 Spring 和微服务；计划约 86 篇，陆续更新中 |

## 目录结构

```
index.html            首页：全部课程
404.html              旧地址（根目录下的 NN.html、dijkstra/）自动跳到新位置
assets/cc.css, cc.js  所有课程共用的样式（含暗色模式）和交互辅助函数
cs/                   计算机科学速成课
java/                 Java 后端 · 图文课（原创）
transcripts/<课程>/   逐集字幕文本（目前是黑马 Java 上部和 JavaWeb，备用）
docs/java-roadmap.md  Java 学习路线和课程清单
docs/java-course-outline.md  Java 后端课的主线和取舍依据
tools/bili-transcripts/  B 站合集 → 字幕文本的工具（字幕下载 + 本地 whisper 转写）
```

纯静态页面，没有构建步骤，直接打开 `index.html`，或在仓库根目录运行 `python3 -m http.server`。新增一门课的步骤见 `CLAUDE.md` 的 "Adding a new course"。

## 来源与协议

- **计算机科学速成课**：原作 Crash Course Computer Science（PBS Digital Studios），中英字幕来自 [1c7/Crash-Course-Computer-Science-Chinese](https://github.com/1c7/Crash-Course-Computer-Science-Chinese)。图文版以 [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/deed.zh-hans) 协议发布，与原作一致，见 `LICENSE`。第 41–47 集续集是本项目的原创内容，不是 Crash Course 官方作品，同样以 CC BY-NC-SA 4.0 发布。
- **Java 后端 · 图文课**：原创内容，随本仓库以 CC BY-NC-SA 4.0 发布。文中引用的 [SpringBlade](https://github.com/chillzhuang/SpringBlade) 代码版权归原作者所有，按 Apache License 2.0 引用。0.1 由 Claude Fable 5.2 写作，0.2 由 Claude Opus 5.5 写作，0.3 由 GPT-6（OpenAI Codex）写作。
- **`transcripts/` 里的字幕文本**：来自黑马程序员在 B 站发布的视频（BV17F411T7Ao 等），视频和字幕版权归原作者所有，仅供个人学习。
- 示意图、交互演示和“补充”内容的作者见各篇署名。
