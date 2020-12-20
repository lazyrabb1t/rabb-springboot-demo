package xyz.lazyrabbit.data.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import xyz.lazyrabbit.data.redis.pojo.User;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

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

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    public static final String key = "key";

    @Test
    void test1() {
        strRedisTemplate.opsForValue().set("url", "lazyrabbit.xyz");
        log.info("get url from redis:{}", strRedisTemplate.opsForValue().get("url"));
//        User user = new User(1, "rabbit");
//        objRedisTemplate.opsForValue().set("user", user);
//        log.info("get user from redis:{}", objRedisTemplate.opsForValue().get("user"));
        User user2 = new User(2, "chenshi");
        serializableRedisTemplate.opsForValue().set("user2", user2);
        log.info("get user2 from redis:{}", serializableRedisTemplate.opsForValue().get("user2"));

    }

    @Test
    void test2() throws InterruptedException {
        Lock lock = redisLockRegistry.obtain(key);
        lock.lock();
        log.info("第一次上锁");
        TimeUnit.SECONDS.sleep(5);
        Lock lock2 = redisLockRegistry.obtain(key);
        lock2.lock();
        log.info("第二次上锁");
        TimeUnit.SECONDS.sleep(5);
        lock2.unlock();
        lock.unlock();

    }

    @Test
    void test3(){
        String key = "DAU";
        User user = new User(1, "rabbit");
        User user2 = new User(13, "messi");
        User user3 = new User(88, "lucas");
        serializableRedisTemplate.opsForValue().setBit("DAU", user.getId(), true);
        serializableRedisTemplate.opsForValue().setBit("DAU", user2.getId(), true);
        serializableRedisTemplate.opsForValue().setBit("DAU", user3.getId(), true);
        Long count = serializableRedisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
        log.info("dau:{}", count);
    }
}
