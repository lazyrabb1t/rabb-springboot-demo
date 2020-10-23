package xyz.lazyrabbit.data.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import xyz.lazyrabbit.data.redis.pojo.User;

import java.io.Serializable;

@SpringBootTest
@Slf4j
class RedisApplicationTests {

    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;
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
