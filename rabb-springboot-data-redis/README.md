### 一、关于redis

Redis 是一个基于内存的高性能key-value数据库，支持多种数据结构。

### 二、springboot集成

##### 1、添加依赖

```
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-pool2</artifactId>
      <version>2.6.2</version>
    </dependency>
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
      <exclusions>
        <exclusion>
          <groupId>org.junit.vintage</groupId>
          <artifactId>junit-vintage-engine</artifactId>
        </exclusion>
      </exclusions>
    </dependency>
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-core</artifactId>
      <version>2.10.4</version>
    </dependency>
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.10.4</version>
    </dependency>
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-annotations</artifactId>
      <version>2.10.4</version>
    </dependency>
  </dependencies>
```

##### 2、配置application.ym

```
spring:
  redis:
    database: 0
    host: localhost
    port: 6379
    password:
    timeout: 3000
    lettuce:
      pool:
        max-active: 200
        max-wait: 10000
        max-idle: 20
```

##### 3、添加配置类，自定义RedisTemplate

```
@Configuration
public class RedisConfig {

    /**
     * 默认使用JDK自带序列化方式
     * 这里修改为其他方式
     */
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
}
```

##### 4、实体类


```
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Integer id;
    private String name;
}
```

##### 5、测试

```
@SpringBootTest
@Slf4j
class RedisApplicationTests {

    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;
    // 取消redisconfi中的自定义模板可用
//    @Autowired
//    private RedisTemplate<Object, Object> objRedisTemplate;
    @Autowired
    private RedisTemplate<String, Serializable> serializableRedisTemplate;

    @Test
    void contextLoads() {
        strRedisTemplate.opsForValue().set("url", "lazyrabbit.xyz");
        log.info("get url from redis:{}", strRedisTemplate.opsForValue().get("url"));

//        User user = new User(1, "rabbit");
//        objRedisTemplate.opsForValue().set("user", user);
//        log.info("get user from redis:{}", objRedisTemplate.opsForValue().get("user"));
        User user2 = new User(2, "chenshi");
        serializableRedisTemplate.opsForValue().set("user2", user2);
        log.info("get user2 from redis:{}", serializableRedisTemplate.opsForValue().get("user2"));
    }
}
```