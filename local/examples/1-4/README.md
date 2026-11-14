# 1.4 页面上实际运行过的代码

所有程序都用 JDK 8（Amazon Corretto 1.8.0_512）编译；页面上的输出和报错摘自本目录的 `.out` 文件。`classes/`、`access-classes/` 只放编译产物，不入库。

| 文件 | 页面位置 | 验证内容 |
| --- | --- | --- |
| `TwoObjects.java` | 第 1、2 节 | 两次 `new` 得到独立的实例字段；引用赋值后，两个变量改的是同一个对象 |
| `MethodsAndThis.java` | 第 3 节 | 点号左边决定方法作用于哪个对象；参数遮蔽字段时用 `this.name` 区分 |
| `Construction.java` | 第 4 节 | 字段先取得声明处的初始值；`this(...)` 委托另一个构造器；字段初始化器不随委托重复执行 |
| `DefaultConstructor.java` | 第 4 节 | 完全没有声明构造器时，可以调用编译器隐式提供的默认构造器 |
| `MissingNoArg.java` | 第 4 节 | 已声明 `NamedAccount(String)` 后，`new NamedAccount()` 编译失败；这是预期失败的示例 |
| `Encapsulation.java` | 第 5 节 | 用方法守住余额约束；同一个类的方法可以读取另一个实例的 `private` 字段 |
| `access-src/` | 第 6 节 | 同包、跨包、public、包私有、protected 的可访问性，以及 protected 跨包引用类型限制 |

## 复现

在本目录执行。JDK 路径可用 `JH="$(/usr/libexec/java_home -v 1.8)"` 取得。

```bash
mkdir -p classes access-classes
"$JH/bin/javac" -encoding UTF-8 -d classes TwoObjects.java
"$JH/bin/java" -cp classes TwoObjects
```

其他成功示例把文件名和主类名换成 `MethodsAndThis`、`Construction`、`DefaultConstructor` 或 `Encapsulation` 即可。包访问示例：

```bash
"$JH/bin/javac" -encoding UTF-8 -d access-classes \
  access-src/demo/model/Vault.java \
  access-src/demo/model/SamePackage.java \
  access-src/demo/client/OtherPackage.java \
  access-src/demo/client/ProtectedChild.java
"$JH/bin/java" -cp access-classes demo.model.SamePackage
"$JH/bin/java" -cp access-classes demo.client.OtherPackage
"$JH/bin/java" -cp access-classes demo.client.ProtectedChild
```

`MissingNoArg.java`、`SamePackagePrivateError.java`、`OtherPackageError.java`、`ProtectedChildError.java` 都是故意不能编译的示例，完整 javac 报错见同名 `.out`。不要把它们并入“全部源码一次编译”的命令。

注意：`UserAccount()` 是**显式声明的无参构造器**，不是 JLS 所说的“默认构造器”；默认构造器只在类没有任何构造器声明时隐式出现。`Construction.java` 里的字段初始值不是 JVM 的零值/空值阶段，而是字段声明处的初始化器已经执行后的结果。
