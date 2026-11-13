# 1.1 页面上实际运行过的代码

终端输出都是用 JDK 8（Amazon Corretto 1.8.0_512）跑出来的，页面上的输出原样摘自这些运行。同名 `.out` 文件里是命令和输出。

| 文件 | 页面位置 | 内容 |
| --- | --- | --- |
| `IntMath.java` | 整数除法一节 | `7/2=3`、`-7/2=-3`（向零取整）、`-7%2=-1`、`1/0` 抛 `ArithmeticException`、浮点 `1.0/0.0=Infinity` |
| `Odometer.java` | 溢出一节 | `MAX_VALUE+1` 回绕成最小值、`24*60*60*1000*1000` 全是 int 字面量先溢出得 500654080、加 `L` 后正确、`Math.addExact` 抛异常 |
| `Floats.java` | 浮点一节 | `0.1+0.2=0.30000000000000004`；`0.1f+0.2f==0.3f` 反而是 `true`；`(float)0.3==0.3` 是 `false`；浮点除零得 `Infinity`/`NaN`；`NaN==NaN` 是 `false` |
| `Money.java` | BigDecimal 一节 | `new BigDecimal(0.1)` 展开成 0.10000…05625；`equals` 看标度（2.0≠2.00）、`compareTo` 不看；`1/3` 不给舍入模式抛 `ArithmeticException` |

复现：JDK 8 的 `javac`/`java`，命令见各 `.out` 文件第一行。本机 PATH 上的 JDK 是 Temurin 8，页面统一用 `$(/usr/libexec/java_home -v 1.8)` 取 Corretto 8。

页面上的 SpringBlade 统计数字（223 个 Java 文件的全仓库 grep 结果，v3.7.1 标签）：

```sh
grep -rn "ToStringSerializer" local/springblade-3.7.1/ --include="*.java" | grep -c "JsonSerialize"   # 38
grep -rn "private Long id;" local/springblade-3.7.1/ --include="*.java" | wc -l                      # 24
grep -rn "private Double\|private double\|private Float\|private float" --include="*.java" local/springblade-3.7.1/   # 0
grep -rn "addExact\|multiplyExact\|toUnsigned" --include="*.java" local/springblade-3.7.1/            # 0
grep -rln "BigDecimal" --include="*.java" local/springblade-3.7.1/                                    # 2 个文件
```
