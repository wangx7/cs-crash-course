# 1.3 String：不可变、常量池、== 和 equals · 独立实验

只需要 JDK 8 和 Python 3，无须 Spring、Maven、数据库或其他章节的文件。
实测 JDK：Amazon Corretto 1.8.0_512（macOS arm64）。

在仓库根目录运行：

```sh
python3 local/examples/1-3/run.py
```

macOS 默认通过 `java_home -v 1.8` 定位 JDK。其他系统可以显式指定 JDK 8：

```sh
python3 local/examples/1-3/run.py --jdk /path/to/jdk8
```

脚本逐个编译示例，检查退出码及输出，记录到 `results.json`；
并对 `FoldLab`、`ConcatCost` 执行 `javap -c`，全文保存在 `javap/` 目录。
编译产物保存在自动清理的临时目录。`run_stdout` 是实际输出，未做替换。

| 文件 | 内容 |
| --- | --- |
| `ComparePuzzle.java` | 两个字面量、`new String`、字面量拼接与 `==`/`equals` 的结果 |
| `PoolLab.java` | 页面“字符串工作台”的同一组变量；`intern` 命中与未命中常量池的两种返回 |
| `FoldLab.java` | 常量折叠：字面量拼接、普通变量拼接、`final` 常量变量拼接的差异 |
| `ImmutableLab.java` | 方法返回新字符串；无需改动时 `trim`/`substring(0)`/`replace`/`toUpperCase` 返回原对象 |
| `HashLab.java` | 用反射读取私有 `hash` 字段，观察 `hashCode()` 首次计算后缓存 |
| `ConcatCost.java` | 循环 `+=` 与 `StringBuilder` 拼接 20000 次的耗时对比 |

`ConcatCost` 的毫秒数随机器、JIT 预热和运行次数变化，`run.py` 只校验
`sameContent=true` 和 `length=20000` 两行；页面引用的耗时是 `results.json`
里记录的那一次运行，不是可复现的常数。

`PoolLab` 的 `fresh` 用 `StringBuilder` 在运行时拼出，内容 `"blade"` 不以
字面量形式出现在程序里，因此常量池起初没有它，`intern()` 把这个对象
本身登记进池并返回（JDK 7 起的行为，`String.intern` 的 javadoc 写明了这一契约）。
若改写成 `new String("blade")`，字面量 `"blade"` 已随类加载入池，结果会变为 false。

`HashLab` 用 `new String("notice")` 而不是字面量，确保拿到一个尚未算过
哈希的新对象；反射只用于观察私有字段，不是常规业务写法。

页面引用的 JDK 源码出自 JDK 自带的 `src.zip`（Corretto 1.8.0_512）：

```sh
unzip -p "$(/usr/libexec/java_home -v 1.8)/src.zip" java/lang/String.java
```

本地已解出一份在 `local/refs/jdk8src/java/lang/String.java`（gitignored）。
关键行号：`value` 字段 114、`hash` 字段 117、`hashCode()` 1465–1476、
`substring(int)` 1925–1934、`concat` 2026–2034、`replace(char,char)` 2066–2091、
`toUpperCase(Locale)` 无改动返回 this 2748、`trim` 2867–2879、`intern` 3167。

网页的交互演示展示这些预先记录的结果或按语言规则绘制的示意，不在浏览器编译 Java。
“常量池/堆”分区图是语义示意，不表示对象的物理内存位置。