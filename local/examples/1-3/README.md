# 1.3 页面上实际运行过的代码

终端输出都是用 JDK 8（Amazon Corretto 1.8.0_512）跑出来的，页面上的输出原样摘自这些运行。同名 `.out` 文件里是命令和输出。

| 文件 | 页面位置 | 内容 |
| --- | --- | --- |
| `StringIdentity.java` | 第 1、3 节 | 字面量、`new String`、常量拼接、运行时拼接、`intern()` 的 `==` / `equals` 结果 |
| `ImmutableString.java` | 第 2 节 | `trim()` / `toUpperCase()` 不改原串；无操作的 `trim()` 在本机实现里返回原对象，说明“不可变”不等于“每次必造新对象” |
| `EqualsNull.java` | 第 4 节 | `equals` 按内容比较；已知非空常量在前可安全接收 null；`Objects.equals` 的两种 null 结果；null 调方法抛 NPE |
| `Concat.java` | 第 5 节 | 常量拼接、运行时拼接、循环 `+=`、显式 `StringBuilder` 的输出 |
| `Concat.javap.out` | 第 5 节 | JDK 8 `javap -c -p` 完整输出：`+` 被编译成 `StringBuilder`；循环 `+=` 每轮创建 builder，显式 builder 只创建一次 |
| `Utf16.java` | 第 6 节 | `A😀B` 的 `length()` 为 4、代码点数为 3；代理项值、代码点值、按代码点移动索引并截取 |

复现：在本目录用 JDK 8 的 `javac` / `java` / `javap`，命令见各 `.out` 文件第一行。本机 PATH 上的 JDK 是 Temurin 8，页面统一用 `$(/usr/libexec/java_home -v 1.8)` 取 Corretto 8。

注意：`trimmed == trimmed.trim()` 为 true 是这台 Corretto 8 的实现行为，用来反驳“不可变意味着每次调用都创建新对象”；String API 没有把“返回原对象”写成所有实现都必须遵守的身份承诺。页面只把内容不变当作语言/API 规则，不依赖该方法的身份结果。
