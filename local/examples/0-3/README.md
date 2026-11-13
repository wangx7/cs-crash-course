# 0.3 · 语法速览的可复现实验

对应 `java/0-3.html`。2026-11-13 使用 Amazon Corretto 1.8.0_512 编译运行。

在仓库根目录执行：

```sh
python3 local/examples/0-3/verify.py
```

默认通过 macOS 的 `/usr/libexec/java_home -v 1.8` 找 JDK。其他系统可设置 `JDK8` 为 JDK 8 安装目录。脚本在临时目录生成 class，结束后自动清理，不改源码。

- `OrderStats.java`：本篇完整例子，实际输出在 `OrderStats.out`。
- `SyntaxChecks.java`：声明、默认值、条件、switch、数组循环、整数除法；实际输出在同名 `.out`。
- `fail/Unassigned.java`：局部变量未初始化，预期编译失败。
- `fail/Condition.java`：int 不能作为 if 条件，预期编译失败。
- `fail/MissingReturn.java`：有返回类型却可能走到方法末尾，预期编译失败。
- `fail/ReturnOnly.java`：仅返回类型不同，预期编译失败。
- `fail/Bounds.java`：编译成功，运行时数组越界。

`fail/*.out` 保存实测诊断，编译器使用 `-J-Duser.language=en`；仅把本机绝对路径缩短为实验目录内的相对路径。不同 JDK 8 发行版的错误排版可能不同，脚本检查错误类别。

验证还包括练习修改版（把负数分支的 break 换成 continue，结果为 11）、空数组求和，以及访问空数组下标 0 抛出异常。修改版和边界实验均在临时目录生成。

页面的逐轮追踪是 JavaScript 制作的固定流程示意，不是 JVM 解释器。订单状态、满 5 件的标签、负数停止标记都是教学约定。
