# 1.2 引用和内存 · 独立实验

只需要 JDK 8 和 Python 3，无须 Spring、Maven、数据库或其他章节的文件。
实测 JDK：Amazon Corretto 1.8.0_512（macOS arm64）。

在仓库根目录运行：

```sh
python3 local/examples/1-2/run.py
```

macOS 默认通过 `java_home -v 1.8` 定位 JDK。其他系统可以显式指定 JDK 8：

```sh
python3 local/examples/1-2/run.py --jdk /path/to/jdk8
```

脚本逐个编译示例，检查退出码及全部输出，记录到 `results.json`。
编译产物保存在自动清理的临时目录；`errors/` 内的文件预期编译失败。
`run_stdout`、`run_stderr`、`compile_stderr` 是实际输出，未做替换。

| 文件 | 内容 |
| --- | --- |
| `ReferencePuzzle.java` | 修改对象字段、重新赋值形参、修改基本类型形参的不同结果 |
| `AssignmentLab.java` | 赋值复制引用，两个变量可以指向同一对象 |
| `ReturnLab.java` | 接收返回值才能替换调用方变量；将一个别名设为 null 不影响另一个 |
| `ArrayCopyLab.java` | 引用数组的 clone 创建新数组，但数组元素仍引用原对象 |
| `NullLab.java` | 字段与数组元素的默认值、null 字段访问、null 自动拆箱 |
| `ReassignOrder.java` | 复制工单后分配处理人，保留输入对象并拒绝 null 输入 |
| `errors/UnassignedReference.java` | 局部引用变量必须明确赋值后才能读取 |

工单类及其状态、处理人编号均为原创教学设定，并非真实系统接口。
状态 `0`、`1`、`9` 只用于区分修改前后的值；没有持久化、事务或并发行为。
嵌套类只是为了让每个示例保存在一个文件中，各程序都可以单独编译运行。

`ReassignOrder` 复制的 `status` 是基本类型值，`assigneeId` 是 `Integer` 引用。
`Integer` 对象不可变，给新工单的 `assigneeId` 赋值是替换字段中的引用，不会修改旧工单引用的整数对象。
因此这里的复制足以隔离这两个字段的后续赋值；它不是通用深拷贝。
如果工单增加可变的地址对象、数组或集合，需要按照业务要求决定是否继续复制这些对象。

异常示例捕获异常并仅打印异常类名或指定消息，便于对比发生失败的操作。
完整异常类型仍由 Java 8 实际执行获得；这些教学 catch 不代表生产代码应以捕获空指针异常代替输入校验。

网页的“运行”按钮展示这些预先记录的结果，不在浏览器编译 Java。
引用关系图是对语言语义的示意，不是对象地址、真实内存布局或垃圾回收时点的测量。
