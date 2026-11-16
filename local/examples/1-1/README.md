# 1.1 数值类型和它的坑 · 独立实验

只需要 JDK 8 和 Python 3，无须 Spring、Maven、数据库或其他章节的文件。
实测 JDK：Amazon Corretto 1.8.0_512（macOS arm64）。

在仓库根目录运行：

```sh
python3 local/examples/1-1/run.py
```

其他系统可以显式传入 JDK 8 的目录：

```sh
python3 local/examples/1-1/run.py --jdk /path/to/jdk8
```

脚本逐个编译文件，检查退出码和关键输出，记录到 `results.json`。
编译产物保存在自动清理的临时目录；`errors/` 内的文件预期编译失败。

| 文件 | 内容 |
| --- | --- |
| `PriceOverflow.java` | 开篇题：int 乘积赋给 long 仍先发生溢出 |
| `NumberLab.java` | 提升、转换、除法、溢出检查、浮点特殊值、null 拆箱 |
| `DecimalLab.java` | 二进制误差、BigDecimal 构造、scale/precision、比较、不可变、除法 |
| `RoundingLab.java` | 正负数舍入、舍入边界、分摊后的合计差异 |
| `PriceQuote.java` | 完整的命令行报价程序，读取十进制字符串，验证并计算 |
| `errors/ByteSum.java` | 两个 byte 相加提升为 int，不能直接赋给 byte |
| `errors/LongLiteral.java` | long 接收变量不能改变无 L 后缀的字面量类型 |

在自己的电脑上单独运行报价程序：先安装 JDK 8，将其 `bin` 目录加入 PATH，并用 `javac -version` 和 `java -version` 确认二者均为 1.8。下载 `PriceQuote.java`，在终端进入文件所在目录后执行（macOS / Windows / Linux 通用）：

```sh
javac -encoding UTF-8 PriceQuote.java
java PriceQuote 19.90 3 0.85
```

报价程序的规则是本篇人为约定：单价非负且能精确到分、数量为正 int、折扣系数在 0 到 1 之间，先汇总再乘折扣系数，最后保留两位并用 HALF_UP 舍入。
`19.900` 可以精确缩成 `19.90`，接受；`19.901` 需要舍入，拒绝。
这是数值语义实验；没有处理币种、税费、数据库、认证，也没有实现 HTTP 接口。参数长度、业务金额上限和计数上限需要在实际接口中另行约定。

`results.json` 中 `run_stdout`、`run_stderr`、`compile_stderr` 都是实际输出，未做文本替换。
网页中的“运行”按钮直接展开这些已验证的结果，无须安装 JDK；切换写法或输入后会清除旧结果，再次点击运行即可查看新结果。页面不发起编译请求，也不使用 JavaScript 浮点计算替代 Java 的数值语义。
