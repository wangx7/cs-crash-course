# 0.3 页面上实际运行过的代码

终端输出都是用 JDK 8（Amazon Corretto 1.8.0_512，Apple 芯片）跑出来的，页面上的输出原样摘自这些运行。同名 `.out` 文件里是命令和输出。

| 文件 | 页面位置 | 内容 |
| --- | --- | --- |
| `OrderStats.java` | 第 1 节起贯穿 | 整篇的主程序：正数累加、零跳过、负数停止 |
| `Checks.java` | 第 2–5 节的零散结论 | 数组默认值、增强 for 的副本、`&&` 短路、switch、整数除法、`Boolean` 拆箱抛 NPE（最后一行故意抛的） |
| `FallThrough.java` | 第 3 节 | `switch` 不写 `break` 会一路走进 `default` |
| `variants/OrderStatsContinue.java` | 第 7 节动手 | 把负数分支的 `break` 换成 `continue`，输出从 5 变 11 |
| `fail/Unassigned.java` | 第 2 节 | 局部变量没赋值就读：编译错误 |
| `fail/Truthiness.java` | 第 3 节 | `int` 当 `if` 条件：编译错误 |
| `fail/MissingReturn.java` | 第 5 节 | 有返回类型却有路径不返回：编译错误 |
| `fail/DuplicateMethod.java` | 第 5 节 | 只靠返回类型区分同名方法：编译错误 |
| `fail/VarKeyword.java` | 第 2 节 | Java 8 没有 `var`：编译器把它当成一个类名去找 |
| `fail/Bounds.java` | 第 4 节 | 越界编译不管，运行时抛 `ArrayIndexOutOfBoundsException` |
| `fail/Inspect.java` | 第 6 节找错练习 | 19 行、四类问题混在一起 |
| `fail/Inspect2/3/4.java` | 第 6 节 | 同一个文件第 2–4 轮的样子（类名不同只是为了能放在同一目录；页面上统一叫 `Inspect.java`，行号和页面一致） |

复现：JDK 8 的 `javac`/`java`，命令见各 `.out` 文件第一行。本机 PATH 上的 JDK 是 Temurin 8，页面统一用 `$(/usr/libexec/java_home -v 1.8)` 取 Corretto 8。
