# demo-springboot-mysql 项目介绍

一个用于学习的 SpringBoot 后台 API 入门项目，演示了 **SpringBoot + MyBatis-Plus + MySQL** 的最基本用法：一个 User 表的完整增删改查（CRUD）接口。

## 一、技术栈

| 技术 | 版本 | 说明 |
| --- | --- | --- |
| JDK | 1.8 | 本机现有版本 |
| SpringBoot | 2.7.18 | 支持 JDK8 的最后一个大版本 |
| MyBatis-Plus | 3.5.3.1 | 兼容 SpringBoot 2.7.x 的稳定版本 |
| MySQL | 本地版本（驱动 8.0.33） | 兼容 MySQL 5.7 / 8.x |
| Lombok | 由 SpringBoot 父 POM 管理 | 简化实体类代码 |

## 二、目录结构

```
demo-springboot-mysql
├── pom.xml                                  # Maven 依赖配置
├── sql/
│   └── init.sql                             # 建库建表 + 测试数据脚本
├── 如何连接本地MySQL.txt                     # 数据库配置修改指南
└── src/main/
    ├── java/com/example/demo/
    │   ├── DemoSpringbootMysqlApplication.java   # 启动类
    │   ├── common/Result.java                    # 统一返回结果包装
    │   ├── config/MybatisPlusConfig.java         # 分页插件配置
    │   ├── entity/User.java                      # 实体类，对应表 t_user
    │   ├── mapper/UserMapper.java                # Mapper 接口，继承 BaseMapper
    │   ├── service/UserService.java              # Service 接口
    │   ├── service/impl/UserServiceImpl.java     # Service 实现
    │   └── controller/UserController.java        # REST 接口层
    └── resources/
        └── application.yml                       # 配置文件（数据库为虚拟配置）
```

## 三、分层架构说明（学习重点）

```
请求 -> Controller -> Service -> Mapper -> MySQL
```

- **Controller**：接收 HTTP 请求，调用 Service，返回统一 `Result` 包装。
- **Service**：业务层。继承 MyBatis-Plus 的 `ServiceImpl` 后自动拥有 save / removeById / updateById / getById / list / page 等方法。
- **Mapper**：数据访问层。只需继承 `BaseMapper<User>`，不用写一行 SQL 和 XML 即可完成单表 CRUD。
- **Entity**：`@TableName("t_user")` 绑定表名，`@TableId(type = IdType.AUTO)` 指定主键自增。

## 四、快速启动

1. 执行 `sql/init.sql` 创建数据库 `demo_db` 和表 `t_user`。
2. 按《如何连接本地MySQL.txt》修改 `application.yml` 中的数据库账号密码。
3. 运行 `DemoSpringbootMysqlApplication` 的 main 方法（或 `mvn spring-boot:run`）。
4. 服务启动在 `http://localhost:8080`。

## 五、接口列表

统一返回格式：`{ "code": 0, "message": "success", "data": ... }`

| 功能 | 方法 | 路径 | 示例 |
| --- | --- | --- | --- |
| 新增用户 | POST | /api/users | body: `{"name":"赵六","age":22,"email":"z6@example.com"}` |
| 删除用户 | DELETE | /api/users/{id} | /api/users/1 |
| 更新用户 | PUT | /api/users | body: `{"id":1,"age":21}` |
| 按 id 查询 | GET | /api/users/{id} | /api/users/1 |
| 查询全部 | GET | /api/users | - |
| 条件查询 | GET | /api/users/search | ?name=张&age=20 |
| 分页查询 | GET | /api/users/page | ?pageNum=1&pageSize=2 |

curl 示例：

```bash
# 查询全部
curl http://localhost:8080/api/users

# 新增
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"赵六","age":22,"email":"z6@example.com"}'

# 分页
curl "http://localhost:8080/api/users/page?pageNum=1&pageSize=2"
```

## 六、学习延伸建议

- 给 `t_user` 增加逻辑删除字段 `deleted`，实践 `@TableLogic`
- 用 `Validation`（@NotBlank 等）做参数校验
- 增加全局异常处理器 `@RestControllerAdvice`
- 把 SQL 打印日志切换为 logback 输出，学习日志配置
