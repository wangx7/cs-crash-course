# 0.3 · Java 8 语法实验

本目录的代码独立于其他篇。环境：macOS Apple 芯片，Amazon Corretto 1.8.0_512。

在本目录运行全部实验：

```sh
python3 run.py
```

默认通过 macOS 的 `java_home -v 1.8` 找到 JDK 8；其他平台或自定义安装位置可用 `python3 run.py --jdk /path/to/jdk8`。结果记录在 `results.json`，编译产物放在临时目录并自动清理。

| 文件 | 用途 | 预期 |
| --- | --- | --- |
| `InventoryReport.java` | 正文的完整库存检查程序 | 编译运行成功 |
| `SyntaxLab.java` | 类型、默认值、短路、数组遍历、循环和 switch | 编译运行成功 |
| `ForEachCopy.java` | 给增强 for 的循环变量赋值 | 数组仍为 `[8, 0, 3]` |
| `Division.java` | 先做整数除法再赋给 double | 输出 `2.0` |
| `errors/Condition.java` | 把 int 当作 if 条件 | 编译失败 |
| `errors/Unassigned.java` | 读取未赋值的局部变量 | 编译失败 |
| `errors/Quote.java` | 把 char 赋给 String | 编译失败 |
| `errors/MissingReturn.java` | 有返回类型的方法可能走到末尾 | 编译失败 |

`errors/` 中的错误是教学实验，按文件分别编译；不要把它们混进正常项目。编译器的诊断语言显式设为英文，页面中的英文诊断原样取自 `results.json`，随后另给中文解释。

只运行正文程序（先确认所用的是 JDK 8）：

```sh
javac -encoding UTF-8 InventoryReport.java
java InventoryReport
```

页面的交互展示固定代码对应的实测输出和解说，浏览器不编译或执行 Java。
