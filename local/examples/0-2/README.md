# 0.2 页面上实际运行过的代码

终端输出是用 JDK 8（Corretto 1.8.0_512）和 JDK 25（OpenJDK 25.0.2）跑出来的，命令就是页面终端里那些。

| 目录 | 页面位置 | 内容 |
| --- | --- | --- |
| `e1/` | 第 1、2 节 | `Hello.java`，字节浏览器里的 `Hello.class` 由 JDK 8 编译它得到 |
| `e3/` | 第 3 节 | `App` 用到时才加载 `Report` |
| `e4/` | 第 4 节 | `Main` + `Shop`；`Shop.v0`–`v3` 是三次改动后的版本，依次 `cp ShopN Shop.java` 再只编译 Shop |
| `e6/` | 第 5 节 | `Check.java` 用 `String.isBlank()`，比较三种编译方式 |
| `e7/` | 自测第 2 题 | `Api.java`，对比加不加 `-parameters` 的 `javap -v` |
