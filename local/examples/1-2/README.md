# 1.2 页面上实际运行过的代码

终端输出都是用 JDK 8（Amazon Corretto 1.8.0_512）跑出来的，页面上的输出原样摘自这些运行。同名 `.out` 文件里是命令和输出。

| 文件 | 页面位置 | 内容 |
| --- | --- | --- |
| `CopyVsShare.java` | 第 2 节（步进器的配套实测） | `b = a` 拷的是值（a 仍是 5）；`ys = xs` 拷的是引用（`ys[0] = 99` 后 `xs` 也是 `[99, 2]`） |
| `Swap.java` | 第 3 节 | `swap(int,int)` 换不动；`arr[0] = 0` 改得动；`arr = new int[]{9,9}` 换引用无效——参数是实参值的副本 |
| `StackDepth.java` | 第 5 节 | 无限递归直到 `StackOverflowError`，连跑三次深度停在 30545 / 30622 / 30630——深度不是定值 |
| `NullDemo.java` | 第 6 节 | 对 null 调方法、把 `Integer` 的 null 拆箱，都抛 NPE；Java 8 的 NPE 消息是空的（对照 JEP 358） |
| `BoxCache.java` | 第 2 节末尾 | `Integer` 127 的 `==` 是 true、128 是 false；`equals` 和 `intValue` 拆箱比较都是 true |

复现：JDK 8 的 `javac`/`java`，命令见各 `.out` 文件第一行。本机 PATH 上的 JDK 是 Temurin 8，页面统一用 `$(/usr/libexec/java_home -v 1.8)` 取 Corretto 8。

注：StackDepth 的深度是本机当时栈设置下的实测值，JVM 规范不规定栈大小（JVMS §2.5.2 允许固定或动态扩展），换台机器数字会变。
