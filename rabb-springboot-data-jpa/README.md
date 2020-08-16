## 1、关于Spring Data JPA

JPA(Java Persistence API)是Sun官方提出的Java持久化规范，为Java开发人员提供了一种对象/关联映射工具来管理Java应用中的关系数据。

它的出现是为了简化现有的持久化开发工作和整合ORM技术，结束各个ORM框架各自为营的局面。

Spring Data JPA是Spring基于ORM框架、JPA规范的基础上封装的一套JPA应用框架，是基于Hibernate之上构建的JPA使用解决方案，用极简的代码实现了对数据库的访问和操作，包括了增、删、改、查等在内的常用功能。

## 二、简单使用

### 1、添加依赖

```
    <dependency>
    	<groupId>org.springframework.boot</groupId>
    	<artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
    	<groupId>org.springframework.boot</groupId>
    	<artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <dependency>
    	<groupId>mysql</groupId>
    	<artifactId>mysql-connector-java</artifactId>
    	<scope>runtime</scope>
    </dependency>
```

### 2、添加数据源

application.yml

```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai
    username: root
    password: 123456
  jpa:
    hibernate:
      ddl-auto: update

```

### 3、创建实体类

```
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_dict")
public class Dict {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="dict_key")
    private String key;
    @Column(name="dict_value")
    private String value;
    private String tableName;
    private String columnName;
}
```

### 4、编写controller进行测试

```
@SpringBootApplication
@RestController
public class DataJpaApplication {

    @Autowired
    DictRepository dictRepository;

    public static void main(String[] args) {
        SpringApplication.run(DataJpaApplication.class, args);
    }

    @RequestMapping("/add")
    public Dict add(){
        Dict dict = new Dict(null, "1", "普通软件","software","type");
        return  dictRepository.save(dict);
    }

    @RequestMapping("/")
    public Iterable list(){
        return dictRepository.findAll();
    }
}
```

## 三、使用注解定义实体

### 1、@Entity

在类上使用，表明这个类是实体类，当类名与表面不一致时，使用@Table(name="")注解，在name属性中定义表名。

### 2、@Id

在属性上使用，表明这是主键。

### 3、@GeneratedValue(strategy=GenerationType.AUTO)

主键生成策略,可以是：

- TABLE 使用一个特定的数据库表来保存主键
- SEQUENCE 使用序列
- IDENTITY 数据库自增
- AUTO 根据数据库选择最合适的

### 3、@Column

在属性上使用，可以使用name定义对应字段名称

### 4、@Transient

表示该属性并非一个到数据库表的字段的映射

### 5、@OneToOne/@OneToMany/@ManyToOne/@ManyToMany


N对N注解，可以定义：

- cascade：级联策略，默认无
- fetch：是否延迟加载，默认直接EAGER
- optional：定义关联实体是否可以存在null值，默认true
- mappedBy：双向关联实体时，标注在不保存关系的实体
- targetEntity: 表示关联的实体类型，默认为当前标注的实体类

CascadeType包含的类别

- 级联：给当前设置的实体操作另一个实体的权限
- CascadeType.ALL 级联所有操作
- CascadeType.PERSIST 级联持久化（保存）操作
- CascadeType.MERGE   级联更新（合并）操作
- CascadeType.REMOVE  级联删除操作
- CascadeType.REFRESH 级联刷新操作
- CascadeType.DETACH 级联分离操作,如果你要删除一个实体，但是它有外键无法删除，这个级联权限会撤销所有相关的外键关联。

### 6、@JoinColumn

- name：本表的外键
- referencedColumnName：外键所关联表的字段名

### 7、@JoinTable

- name：属性为连接两个表的表名称。若不指定，则使用默认的表名称，格式如下：    "表名1" + "_" + "表名2"
- joinColumn：属性表示，在保存关系的表中，所保存关联关系的外键的字段，并配合@JoinColumn标记使用
- inverseJoinColumn：属性与joinColumn类似，它保存的是保存关系的另外一个外键字段；
- catalog和schema：属性表示实体指定点目录名称或数据库名称；
- uniqueConstraints：属性表示该实体所关联的唯一约束条件，一个实体可以有多个唯一约束条件，默认没有约束；


## N对N关系映射的使用

### 1、注意

- 一般在在维护关系的一方给cascade进行权限配置，同时在另一方使用mappedBy
- 直接使用外键关联的使用@JoinColumn注解，如果名称符合规范则可以省略
- 在使用中间表进行关联的使用@JoinTable注解

### 2、一对一

这里定义两张表，用户表和用户信息表，一个用户表对应一个用户信息：

```
@Data
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userInfoId", referencedColumnName = "id")
    private UserInfo userInfo;
}

@Data
@Entity
@Table(name = "t_user_info")
public class UserInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Integer sex;
    private Integer age;
//    @OneToOne(cascade = {},mappedBy = "userInfo")
//    private User user;
}
```

测试：

```
    @Test
    void testOnetoOne() {

        UserInfo userInfo = new UserInfo();
        userInfo.setName("张三");
        userInfo.setAge(18);

        User user = new User();
        user.setUsername("zhangsan");
        user.setPassword("123456");
        user.setUserInfo(userInfo);

        userRepository.save(user);

        List<User> userList = userRepository.findByUsername("zhangsan");
        Assert.notNull(userList, "获取记录失败");
        System.out.println(userList.get(0));
    }
```

### 3、一对多/多对一（直接外键）

这里定义用户表和用户类型表，一种用户类型对应多个用户：

```
@Data
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userInfoId", referencedColumnName = "id")
    private UserInfo userInfo;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "typeId", referencedColumnName = "id")
    private UserType userType;

}

@Data
@Entity
@Table(name = "t_user_type")
public class UserType {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String typeName;
    private String memo;
//    @OneToMany(mappedBy = "userType", cascade = {})
//    // 防止json序列化死循环
//    @JsonBackReference
//    private List<User> userList;
}
```

测试：

```
    @Test
    void testOnetoMany() {

        UserType userType = new UserType();
        userType.setTypeName("VIP PLUS");

        User user = new User();
        user.setUsername("lisi");
        user.setPassword("123456");
        user.setUserType(userType);

        userRepository.save(user);

        List<User> userList = userRepository.findByUsername("lisi");
        Assert.notNull(userList, "获取记录失败");
        System.out.println(userList.get(0));
    }
```

### 4、一对多/多对一（中间表）

这里定义用户表、角色表以及用户角色表，一个角色对应多个用户：

```
@Data
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userInfoId", referencedColumnName = "id")
    private UserInfo userInfo;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "typeId", referencedColumnName = "id")
    private UserType userType;

    @ManyToOne(cascade = {})
    @JoinTable(name = "t_user_role",
            joinColumns = @JoinColumn(name="userId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "id"))
    private Role role;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userInfo=" + userInfo +
                ", userType=" + userType +
                '}';
    }
}

@Data
@Entity
@Table(name = "t_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String role;
    private String memo;

    @OneToMany(cascade = {},targetEntity = User.class,fetch = FetchType.EAGER,mappedBy = "role")
    private List<User> userList;
}

@Data
@Entity
@Table(name = "t_user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer roleId;
    private Integer userId;
}
```

测试：

```
    @Test
    void testOnetoMany2() {

        User user = new User();
        user.setUsername("wangwu");
        user.setPassword("123456");
        userRepository.save(user);
        User user2 = new User();
        user2.setUsername("chenshi");
        user2.setPassword("123456");
        userRepository.save(user2);

        Role role = new Role();
        role.setRole("系统管理员");
        roleRepository.save(role);

        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getId());
        userRole.setUserId(user.getId());
        userRoleRepository.save(userRole);

        UserRole userRole2 = new UserRole();
        userRole2.setRoleId(role.getId());
        userRole2.setUserId(user2.getId());
        userRoleRepository.save(userRole2);


        Iterable<Role> all = roleRepository.findAll();
        Assert.notNull(all, "获取记录失败");
        System.out.println(all.iterator().next());
    }
```


#### 5、多对多

这里使用角色表、权限表以及角色权限中间表，权限和角色之间为多对多关系

```
@Data
@Entity
@Table(name = "t_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String role;
    private String memo;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "t_role_permission", joinColumns = @JoinColumn(name = "roleId"),
            inverseJoinColumns = @JoinColumn(name = "permissionId"))
    private List<Permission> permissionList;
}

@Data
@Entity
@Table(name = "t_permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String permission;
    private String memo;
    @ManyToMany(mappedBy = "permissionList", targetEntity = Role.class,fetch = FetchType.EAGER)
    private List<Role> roleList;

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permission='" + permission + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}

@Data
@Entity
@Table(name = "t_role_permission")
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer roleId;
    private Integer permissionId;
}
```

测试：

```
    @Test
    void testManytoMany() {
        Role role1 = new Role();
        role1.setRole("账户管理员");
        roleRepository.save(role1);
        Role role2 = new Role();
        role2.setRole("日志管理员");
        roleRepository.save(role2);
        Role role3 = new Role();
        role3.setRole("审批管理员");
        roleRepository.save(role3);

        Permission permission1 = new Permission();
        permission1.setPermission("/user/info");
        permissionRepository.save(permission1);
        Permission permission2 = new Permission();
        permission2.setPermission("/user/list");
        permissionRepository.save(permission2);

        RolePermission rolePermission1 = new RolePermission();
        rolePermission1.setPermissionId(permission1.getId());
        rolePermission1.setRoleId(role1.getId());
        rolePermissionRepository.save(rolePermission1);
        RolePermission rolePermission2 = new RolePermission();
        rolePermission2.setPermissionId(permission1.getId());
        rolePermission2.setRoleId(role2.getId());
        rolePermissionRepository.save(rolePermission2);
        RolePermission rolePermission3 = new RolePermission();
        rolePermission3.setPermissionId(permission1.getId());
        rolePermission3.setRoleId(role3.getId());
        rolePermissionRepository.save(rolePermission3);
        RolePermission rolePermission4 = new RolePermission();
        rolePermission4.setPermissionId(permission2.getId());
        rolePermission4.setRoleId(role1.getId());
        rolePermissionRepository.save(rolePermission4);
        RolePermission rolePermission5 = new RolePermission();
        rolePermission5.setPermissionId(permission2.getId());
        rolePermission5.setRoleId(role2.getId());
        rolePermissionRepository.save(rolePermission5);
        RolePermission rolePermission6 = new RolePermission();
        rolePermission6.setPermissionId(permission2.getId());
        rolePermission6.setRoleId(role3.getId());
        rolePermissionRepository.save(rolePermission6);

        Iterable<Role> all = roleRepository.findAll();
        Assert.notNull(all, "获取记录失败");
        all.forEach(e -> {
            System.out.println(e);
        });

    }
```

## 参考

https://blog.csdn.net/u013089490/article/details/84956736