# Java 学习教程目录

整理自黑马程序员《2026 黑马程序员 Java 学习路线图》（<https://www.bilibili.com/opus/494817843530680807>，2026-03-19 版）。文中链接的 71 门课，集数、时长、发布时间均取自 B 站接口（2026-11-12 抓取），章节划分按分集标题归并。全部课程由“黑马程序员”官方账号发布，免费。

学习目标：**学完能上手公司项目，并对技术有一定了解。** 下面的“建议”一栏都是按这个目标给的，属于整理者的判断，不是路线图原文。

目标技术栈：Java 8、Spring Boot 2、Spring Cloud 微服务（Nacos、OpenFeign）、MyBatis-Plus、若依、Redis，前端 Vue 2。课程取舍按这套技术栈定；技术栈不同的话，取舍也要跟着变。

## 一、总览

| 阶段 | 内容 | 推荐课程 | 时长 | 对本目标 |
|---|---|---|---|---|
| 1 | Java 基础 | Java 入门到起飞 上部 + 下部 | 75h + 38h | 必学，下部可挑着看 |
| 2 | Java Web 框架 | JavaWeb 开发教程（2023 旧版） | 43h | 必学，最关键 |
| 3 | 单体项目 | AI + 若依框架 | 28h | 必学基础篇和实战篇；苍穹外卖可跳过 |
| 4 | 微服务 | SpringCloud 黑马商城 | 41h | 必学前半部分（约 17h） |
| 5 | 企业级项目 | 天机学堂 / 学成在线 / 黑马头条 | 24–52h | 找工作攒经验用，可不学 |
| 6 | 面试专题 | 新版 Java 面试专题 | 24h | 找工作用，可不学 |

最短路径（阶段 1–4，按下文的取舍）约 **180 小时**视频，再加动手敲代码的时间：

| 顺序 | 课程 | 看哪些 | 时长 |
|---|---|---|---|
| 1 | Java 上部 | 全部 | 75h |
| 2 | Java 下部 | 按第二节的建议挑着看 | 约 19h |
| 3 | Git | 前 2 小时 | 约 2h |
| 4 | JavaWeb（旧版） | 全部 | 43h |
| 5 | AI + 若依框架 | 基础篇 + 实战篇 | 约 21h |
| 6 | SpringCloud 黑马商城 | MyBatisPlus、Docker、微服务 01、微服务 02 | 约 17h |
| 7 | Redis | 基础篇 | 约 4h |

MQ、Elasticsearch、工作流、定时任务等，分到相关任务时再查第八节的单项课。

## 二、第一阶段：Java 基础

路线图列了 6 套，任选其一：

| # | 课程 | 集数 | 时长 | 发布 | 链接 |
|---|---|---|---|---|---|
| 1 | Java + AI 零基础入门到大牛 | 199 | 59.3h | 2024-07 | [BV1gb42177hm](https://www.bilibili.com/video/BV1gb42177hm) |
| 2 | **Java 入门到起飞 上部**（在学） | 200 | 75.2h | 2022-04 | [BV17F411T7Ao](https://www.bilibili.com/video/BV17F411T7Ao) |
| 2 | **Java 入门到起飞 下部** | 200 | 38.4h | 2022-09 | [BV1yW4y1Y7Ms](https://www.bilibili.com/video/BV1yW4y1Y7Ms) |
| 3 | 2026 版 AI + Java 入门到大神 | 149 | 37.8h | 2025-10 | [BV1TJxCzSEEZ](https://www.bilibili.com/video/BV1TJxCzSEEZ) |
| 4 | 一套超哇塞的 Java 教程 | 200 | 80.6h | 2023-01 | [BV1Fv4y1q7ZH](https://www.bilibili.com/video/BV1Fv4y1q7ZH) |
| 5 | Java 基础入门（经典版） | 572 | 69.3h | 2019-12 | [BV18J411W7cE](https://www.bilibili.com/video/BV18J411W7cE) |
| 6 | Java 入门基础（含项目和真题） | 200 | 79.2h | 2021-09 | [BV1Cv411372m](https://www.bilibili.com/video/BV1Cv411372m) |

第 3 套最短（37.8h），官方简介说它去掉了“开发用不到、面试问不到”的知识点；第 2 套最长（上下部合计 113.6h），讲得最细。

### 上部章节（BV17F411T7Ao）

| 分集 | 章节 | 时长 |
|---|---|---|
| P1–16 | Java 入门 | 2.2h |
| P17–29 | Java 基础概念 | 3.7h |
| P30–38 | 运算符 | 3.2h |
| P39–48 | 判断和循环 | 2.9h |
| P49–53 | 循环高级综合练习 | 1.1h |
| P54–62 | 数组 | 2.0h |
| P63–73 | 方法 | 2.4h |
| P74–80 | 综合练习 | 3.2h |
| P81–90 | 面向对象 | 2.6h |
| P91–95 | 面向对象综合训练 | 2.2h |
| P96–110 | 字符串 | 5.0h |
| P111–115 | ArrayList | 1.2h |
| P116–120 | 学生管理系统（含升级版） | 3.0h |
| P121–143 | 面向对象进阶（static、继承、多态、抽象类、接口、内部类） | 11.2h |
| P144–154 | 阶段项目（拼图小游戏，Swing 界面） | 4.9h |
| P155–176 | 常用 API | 11.0h |
| P177–184 | 常见算法、Lambda 表达式、算法题 | 4.5h |
| P185–199 | 集合进阶（上） | 8.8h |

### 下部章节（BV1yW4y1Y7Ms）

| 分集 | 章节 | 时长 | 建议 |
|---|---|---|---|
| P2–26 | 集合进阶（Map、HashMap、TreeMap、可变参数、Collections） | 5.1h | 必看；P14–19 源码解析（约 2h）可跳 |
| P27–33 | 阶段项目（斗地主） | 2.4h | 可跳 |
| P34 | 不可变集合 | 0.6h | 必看 |
| P35–42 | Stream 流 | 1.9h | 必看 |
| P43–51 | 方法引用 | 1.5h | 必看 |
| P52–62 | 异常 | 1.8h | 必看 |
| P63–73 | File | 2.4h | 看基础用法 |
| P74–134 | IO 流 | 13.1h | 只看字节流、字符流、缓冲流，约三分之一 |
| P135–167 | 多线程 & JUC | 4.9h | 看懂概念 |
| P168–190 | 网络编程 | 2.9h | 可跳 |
| P191–197 | 反射 | 1.3h | 看懂概念（理解 Spring 的前提） |
| P198–199 | 动态代理 | 0.5h | 看懂概念 |

## 三、第二阶段：Java Web 框架

| 课程 | 集数 | 时长 | 发布 | 链接 |
|---|---|---|---|---|
| JavaWeb + AI，Tlias 教学管理系统（新版，Spring Boot 3 + Vue 3） | 200 | 76.0h | 2024-10 | [BV1yGydYEE3H](https://www.bilibili.com/video/BV1yGydYEE3H) |
| **JavaWeb 开发教程**（旧版，Spring Boot 2 + Vue 2） | 200 | 42.9h | 2023-03 | [BV1m84y1w7Tb](https://www.bilibili.com/video/BV1m84y1w7Tb) |

新版比旧版多出 33 小时，主要多在前端实战（Tlias 前端案例 9.7h）、Linux（3.8h）、Docker（3.8h）、JDBC、单元测试和日志。

**本路线选旧版**：目标技术栈是 Spring Boot 2 + Vue 2 + Java 8，旧版更贴近，还省 33 小时；Docker 在黑马商城课里补。如果目标项目用 Spring Boot 3 和 Vue 3，则选新版，做后端可跳过其中的前端案例。

### 新版章节（BV1yGydYEE3H）

| 分集 | 章节 | 时长 | 建议 |
|---|---|---|---|
| P1–2 | 导学、Web 前端初识 | 0.6h | |
| P3–16 | HTML + CSS | 5.1h | 看懂即可 |
| P17–24 | JavaScript | 2.3h | 看懂即可 |
| P25–30 | Vue、Ajax | 2.4h | 看懂即可 |
| P31–39 | Maven、单元测试 | 3.1h | 必看 |
| P40–49 | Web 基础（Spring Boot 入门、HTTP、请求响应、分层解耦） | 3.8h | 必看 |
| P50–61 | MySQL | 4.6h | 必看 |
| P62–72 | JDBC、MyBatis | 3.4h | 必看 |
| P73–83 | 项目配置、准备工作、部门管理 | 4.0h | 必看 |
| P84–85 | 日志技术 | 0.9h | 必看 |
| P86–92 | 多表关系、多表查询 | 2.6h | 必看 |
| P93–119 | 员工管理、事务、文件上传、全局异常处理、信息统计 | 10.3h | 必看 |
| P120–132 | 登录认证（会话、JWT、过滤器、拦截器） | 4.0h | 必看 |
| P133–140 | Spring AOP | 2.9h | 必看 |
| P141–147 | 原理篇（Spring Boot 配置、Bean、自动配置） | 3.0h | 看懂概念 |
| P148–151 | Maven 高级 | 1.8h | 必看 |
| P153–159 | Vue 工程化、ElementPlus | 3.6h | 做后端可略看 |
| P160–181 | Tlias 前端案例 | 9.7h | 做后端可跳 |
| P182–190 | Linux | 3.8h | 必看 |
| P191–199 | Docker | 3.8h | 必看 |

### 旧版章节（BV1m84y1w7Tb）

| 天 | 内容 | 时长 |
|---|---|---|
| Day 1 | HTML、CSS | 3.0h |
| Day 2 | JavaScript、Vue | 3.8h |
| Day 3 | Ajax、Vue 项目、组件 | 3.3h |
| Day 4 | Maven、Spring Boot 入门、HTTP、Tomcat | 3.0h |
| Day 5 | 请求、响应、三层架构、IOC 和 DI | 2.5h |
| Day 6 | MySQL：DDL、DML | 2.6h |
| Day 7 | MySQL：DQL、多表设计 | 2.6h |
| Day 8 | 多表查询、事务、索引、MyBatis 入门 | 3.2h |
| Day 9 | MyBatis 基础操作、XML 映射、动态 SQL | 2.7h |
| Day 10 | 案例：部门管理、员工管理 | 2.5h |
| Day 11 | 案例：员工管理、文件上传、配置文件 | 2.9h |
| Day 12 | 登录校验、JWT、Filter、Interceptor | 3.4h |
| Day 13 | 事务进阶、AOP | 2.8h |
| Day 14 | Spring Boot 原理 | 2.5h |
| Day 15 | Maven 高级（分模块、继承聚合、私服） | 1.6h |

## 四、第三阶段：单体项目

| 课程 | 集数 | 时长 | 发布 | 链接 |
|---|---|---|---|---|
| **苍穹外卖** | 199 | 37.8h | 2023-07 | [BV1TP411v7v6](https://www.bilibili.com/video/BV1TP411v7v6) |
| AI + 若依框架（RuoYi-Vue3） | 122 | 27.7h | 2024-06 | [BV1pf421B71v](https://www.bilibili.com/video/BV1pf421B71v) |

### 苍穹外卖章节

| 天 | 内容 | 时长 |
|---|---|---|
| Day 1 | 项目介绍、环境搭建、Git 版本控制、前后端联调、断点调试 | 2.6h |
| Day 2 | 新增员工、分页查询、启用禁用、编辑员工 | 2.9h |
| Day 3 | 公共字段自动填充、新增菜品、菜品分页查询 | 3.5h |
| Day 5 | Redis 入门、数据类型、常用命令、在 Java 中操作 Redis | 2.9h |
| Day 6 | HttpClient、微信小程序开发、微信登录 | 2.8h |
| Day 7 | 缓存菜品、Spring Cache、缓存套餐 | 2.8h |
| Day 8 | 地址簿、用户下单 | 2.9h |
| Day 10 | Spring Task 定时任务、WebSocket 来单提醒 | 2.1h |
| Day 11 | Apache ECharts、营业额统计、用户统计 | 3.0h |
| Day 12 | 工作台、Apache POI、导出 Excel 报表 | 1.8h |
| 前端 Day 1–3 | Vue 脚手架、路由、Vuex、TypeScript、员工和套餐页面 | 10.5h |

Day 4、Day 9 在分集里没有视频，推测是课后自己完成的作业日。后端部分合计约 27h；前端三天做后端可跳过。

### AI + 若依框架章节

| 分集 | 章节 | 时长 |
|---|---|---|
| P1–21 | 基础篇（若依搭建、功能介绍、源码阅读） | 5.1h |
| P22–89 | 实战篇（帝可得售货机运营项目，代码生成与改造） | 16.3h |
| P90–121 | 原理篇 | 6.3h |

若依是国内很多公司后台管理系统直接拿来用的开源框架。**本路线必学基础篇和实战篇（约 21h）**，原理篇按需；学了它，苍穹外卖可以跳过。注意课程用的是 RuoYi-Vue3 版本，老项目多是 Vue 2 版，后端结构基本一致。

## 五、第四阶段：微服务

| 课程 | 集数 | 时长 | 发布 | 链接 |
|---|---|---|---|---|
| SpringCloud 微服务开发与实战（黑马商城） | 171 | 40.9h | 2024-04 | [BV1S142197x7](https://www.bilibili.com/video/BV1S142197x7) |
| SpringAI + DeepSeek 大模型应用开发 | 26 | 8.5h | 2025-04 | [BV1MtZnYtEB3](https://www.bilibili.com/video/BV1MtZnYtEB3) |

### 黑马商城章节

| 分集 | 章节 | 时长 |
|---|---|---|
| P2–21 | MyBatisPlus | 5.1h |
| P22–35 | Docker | 3.2h |
| P36–57 | 微服务 01（认识微服务、服务拆分、注册发现、OpenFeign） | 5.1h |
| P58–70 | 微服务 02（网关、配置管理） | 3.9h |
| P71–84 | 微服务保护和分布式事务 | 3.2h |
| P85–113 | MQ 入门、MQ 高级 | 6.9h |
| P114–141 | Elasticsearch 01、02 | 6.4h |
| P142–170 | Redis 面试篇、微服务面试篇 | 6.9h |

**本路线必学 P2–70（约 17h）**：MyBatisPlus、Docker、微服务 01、微服务 02，对应微服务项目的骨架（数据访问、部署、服务注册发现、远程调用、网关）。后面的服务保护、MQ、Elasticsearch 用到再看，两个面试篇可跳过。

## 六、第五阶段：企业级项目实战

| 课程 | 行业 | 集数 | 时长 | 发布 | 链接 |
|---|---|---|---|---|---|
| 天机学堂 | 在线教育 | 161 | 34.7h | 2025-10 | [BV19RWdzxEF7](https://www.bilibili.com/video/BV19RWdzxEF7) |
| 学成在线 | 在线教育 | 185 | 52.1h | 2023-01 | [BV1j8411N7Bm](https://www.bilibili.com/video/BV1j8411N7Bm) |
| 黑马头条 | 资讯 | 181 | 23.8h | 2023-06 | [BV1Qs4y1v7x4](https://www.bilibili.com/video/BV1Qs4y1v7x4) |

三个都是微服务项目，前提是学完第四阶段。按“根据自己情况选择学习”，做一个即可；天机学堂最新。

## 七、第六阶段：面试专题

| 课程 | 集数 | 时长 | 发布 | 链接 |
|---|---|---|---|---|
| 新版 Java 面试专题 | 150 | 23.7h | 2023-04 | [BV1yT411H7YK](https://www.bilibili.com/video/BV1yT411H7YK) |
| Java 八股文面试题（2021 版） | 199 | 22.9h | 2021-09 | [BV15b4y117RJ](https://www.bilibili.com/video/BV15b4y117RJ) |
| LangChain4j 从入门到实战 | 23 | 5.5h | 2025-06 | [BV1sDMqzpEQ3](https://www.bilibili.com/video/BV1sDMqzpEQ3) |
| DeepSeek + Cursor 零代码项目开发部署 | 18 | 5.5h | 2025-03 | [BV1ig9jYUERk](https://www.bilibili.com/video/BV1ig9jYUERk) |

新版面试专题的章节：准备篇 0.7h、Redis 篇 2.7h、数据库篇 2.5h、框架篇 2.1h、微服务篇 2.1h、消息中间件篇 1.4h、常见集合篇 2.8h、并发编程篇 4.4h、JVM 篇 2.7h、企业场景篇 1.9h。

## 八、单项技术课（工作后按需查）

路线图原话是“如想工作后再提升”。不用按顺序学，遇到再查。已列入最短路径的两门：**Git**（协作必用，前 2 小时够用）、**Redis**（P2–23 基础篇 3.8h；实战篇 19.7h 用到缓存、分布式锁时再看）。其次可能用到：RabbitMQ、Elasticsearch、工作流、Nginx。

### 框架与工具

| 课程 | 集数 | 时长 | 发布 | 链接 |
|---|---|---|---|---|
| Git | 31 | 3.6h | 2021-03 | [BV1MU4y1Y7h5](https://www.bilibili.com/video/BV1MU4y1Y7h5) |
| Maven | 29 | 5.7h | 2021-04 | [BV1Ah411S7ZE](https://www.bilibili.com/video/BV1Ah411S7ZE) |
| SSM（Spring + SpringMVC + MyBatisPlus） | 118 | 20.9h | 2022-04 | [BV1Fi4y1S7ix](https://www.bilibili.com/video/BV1Fi4y1S7ix) |
| Spring 高级（Spring 5 底层原理） | 174 | 25.2h | 2022-03 | [BV1P44y1N7QG](https://www.bilibili.com/video/BV1P44y1N7QG) |
| Spring Boot 2 | 174 | 34.5h | 2021-10 | [BV15b4y1a7yG](https://www.bilibili.com/video/BV15b4y1a7yG) |
| Spring Data 高级 | 74 | 13.0h | 2019-10 | [BV1RE41167Pk](https://www.bilibili.com/video/BV1RE41167Pk) |
| Java 日志框架 | 38 | 5.3h | 2020-01 | [BV1iJ411H74S](https://www.bilibili.com/video/BV1iJ411H74S) |
| Spring Security | 71 | 10.1h | 2019-11 | [BV1fE411i7jt](https://www.bilibili.com/video/BV1fE411i7jt) |
| Spring Security OAuth2.0 | 49 | 8.3h | 2019-10 | [BV1VE411h7aL](https://www.bilibili.com/video/BV1VE411h7aL) |
| Apache Shiro | 102 | 16.1h | 2020-12 | [BV1j54y1t7jM](https://www.bilibili.com/video/BV1j54y1t7jM) |

### 数据库与中间件

| 课程 | 集数 | 时长 | 发布 | 链接 |
|---|---|---|---|---|
| MySQL 入门到精通 | 195 | 30.0h | 2022-01 | [BV1Kr4y1i7ru](https://www.bilibili.com/video/BV1Kr4y1i7ru) |
| Oracle | 83 | 13.4h | 2019-12 | [BV1KJ411h7NH](https://www.bilibili.com/video/BV1KJ411h7NH) |
| Redis（含黑马点评项目） | 175 | 42.8h | 2022-02 | [BV1cr4y1671t](https://www.bilibili.com/video/BV1cr4y1671t) |
| MongoDB | 49 | 5.4h | 2019-12 | [BV1bJ411x7mq](https://www.bilibili.com/video/BV1bJ411x7mq) |
| RabbitMQ | 31 | 6.6h | 2023-09 | [BV1mN4y1Z7t9](https://www.bilibili.com/video/BV1mN4y1Z7t9) |
| RocketMQ | 148 | 19.5h | 2019-09 | [BV1L4411y7mn](https://www.bilibili.com/video/BV1L4411y7mn) |
| Elastic Stack 入门到实践 | 65 | 6.1h | 2019-09 | [BV1R4411C7Tf](https://www.bilibili.com/video/BV1R4411C7Tf) |
| ELK 高级搜索 | 102 | 17.8h | 2020-04 | [BV1Nt4y1m7qL](https://www.bilibili.com/video/BV1Nt4y1m7qL) |
| Lucene | 37 | 6.3h | 2019-12 | [BV1eJ411q7nw](https://www.bilibili.com/video/BV1eJ411q7nw) |
| Solr | 191 | 19.5h | 2021-03 | [BV1dh411Q7Qu](https://www.bilibili.com/video/BV1dh411Q7Qu) |

### 分布式与部署

| 课程 | 集数 | 时长 | 发布 | 链接 |
|---|---|---|---|---|
| Docker | 14 | 3.2h | 2023-09 | [BV1HP4118797](https://www.bilibili.com/video/BV1HP4118797) |
| Nginx | 159 | 19.0h | 2021-04 | [BV1ov41187bq](https://www.bilibili.com/video/BV1ov41187bq) |
| Jenkins 持续集成 | 92 | 12.4h | 2020-01 | [BV1kJ411p7mV](https://www.bilibili.com/video/BV1kJ411p7mV) |
| Dubbo | 20 | 3.4h | 2019-11 | [BV1VE411q7dX](https://www.bilibili.com/video/BV1VE411q7dX) |
| Zookeeper | 24 | 3.6h | 2020-02 | [BV1M741137qY](https://www.bilibili.com/video/BV1M741137qY) |
| 分布式事务（入门到应用） | 42 | 7.8h | 2019-09 | [BV1Q4411y7ip](https://www.bilibili.com/video/BV1Q4411y7ip) |
| 分布式事务（3 天深入） | 67 | 7.2h | 2019-11 | [BV1GJ411m73n](https://www.bilibili.com/video/BV1GJ411m73n) |
| Elastic-Job 分布式任务调度 | 18 | 3.4h | 2019-10 | [BV1cE411h7e2](https://www.bilibili.com/video/BV1cE411h7e2) |

### 编程强化

| 课程 | 集数 | 时长 | 发布 | 链接 |
|---|---|---|---|---|
| 数据结构与算法（上篇） | 200 | 40.2h | 2023-02 | [BV1Lv4y1e7HL](https://www.bilibili.com/video/BV1Lv4y1e7HL) |
| 数据结构与算法（下篇） | 200 | 38.4h | 2023-04 | [BV1rv4y1H7o6](https://www.bilibili.com/video/BV1rv4y1H7o6) |
| JVM 入门到实战 | 125 | 35.3h | 2023-10 | [BV1r94y1b7eS](https://www.bilibili.com/video/BV1r94y1b7eS) |
| 设计模式 | 154 | 20.6h | 2020-12 | [BV1Np4y1z7BU](https://www.bilibili.com/video/BV1Np4y1z7BU) |
| JUC 并发编程 | 302 | 32.6h | 2019-12 | [BV16J411h7Rd](https://www.bilibili.com/video/BV16J411h7Rd) |
| Netty 网络编程 | 157 | 23.8h | 2021-03 | [BV1py4y1E7oA](https://www.bilibili.com/video/BV1py4y1E7oA) |

### 业务技术

| 课程 | 集数 | 时长 | 发布 | 链接 |
|---|---|---|---|---|
| 在线支付（微信、支付宝） | 67 | 11.1h | 2021-04 | [BV1LU4y1h736](https://www.bilibili.com/video/BV1LU4y1h736) |
| 报表与数据可视化（POI、ECharts 等） | 100 | 15.5h | 2021-04 | [BV1eA41157va](https://www.bilibili.com/video/BV1eA41157va) |
| 工作流 Activiti7 | 106 | 12.4h | 2020-12 | [BV1H54y167gf](https://www.bilibili.com/video/BV1H54y167gf) |
| 模板引擎 VelocityTools | 50 | 7.8h | 2021-05 | [BV1hK4y1G7Np](https://www.bilibili.com/video/BV1hK4y1G7Np) |
| 百度地图原理与案例 | 25 | 2.3h | 2020-05 | [BV1Wa4y1e7AK](https://www.bilibili.com/video/BV1Wa4y1e7AK) |
| Java 百度地图全套 | 134 | 17.8h | 2022-07 | [BV1ue4y1R712](https://www.bilibili.com/video/BV1ue4y1R712) |

### JDK 新特性

| 课程 | 集数 | 时长 | 发布 | 链接 |
|---|---|---|---|---|
| JDK 8–10 | 97 | 10.8h | 2019-11 | [BV1zJ411R7uQ](https://www.bilibili.com/video/BV1zJ411R7uQ) |
| JDK 13 | 17 | 0.9h | 2020-05 | [BV12V411C7k8](https://www.bilibili.com/video/BV12V411C7k8) |
| JDK 14 | 28 | 2.9h | 2020-04 | [BV1qp4y1C7iu](https://www.bilibili.com/video/BV1qp4y1C7iu) |
| JDK 15 | 9 | 0.9h | 2021-03 | [BV1Ei4y1P7yX](https://www.bilibili.com/video/BV1Ei4y1P7yX) |

### 更多项目

| 课程 | 集数 | 时长 | 发布 | 链接 |
|---|---|---|---|---|
| 瑞吉外卖（服务行业，单体） | 191 | 32.3h | 2022-04 | [BV13a411q753](https://www.bilibili.com/video/BV13a411q753) |
| 传智健康（医疗） | 295 | 38.0h | 2021-05 | [BV1Bo4y117zV](https://www.bilibili.com/video/BV1Bo4y117zV) |
| iHRM 人力资源管理（SaaS） | 370 | 49.7h | 2021-04 | [BV18A411L7UX](https://www.bilibili.com/video/BV18A411L7UX) |
| 好客租房（生活服务） | 462 | 60.8h | 2021-04 | [BV1sZ4y1F7PD](https://www.bilibili.com/video/BV1sZ4y1F7PD) |
| 品达通用权限（权限管理方案） | 165 | 21.7h | 2021-06 | [BV1tw411f79E](https://www.bilibili.com/video/BV1tw411f79E) |
| 集信达短信平台（短信网关） | 77 | 9.4h | 2021-06 | [BV1Jb4y1d7GY](https://www.bilibili.com/video/BV1Jb4y1d7GY) |

## 九、抓取时发现的问题

- 路线图快捷链接里的“Java 日志框架”指向 BV1Cy4y1t7gT，实际是一门 Flume（大数据）课；正文卡片里的 BV1iJ411H74S 才是日志框架课，上表用的是后者。
- 单项技术课多数发布于 2019–2021 年，版本偏旧（如 Spring Boot 2、JDK 15 之前的新特性）；主线课程（阶段 1–5）则是 2022–2025 年的。
- “学完拥有 2–3 年项目经验”之类是培训机构的宣传语，课程顺序本身可参考。
