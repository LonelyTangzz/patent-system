import com.tang.patent.PatentApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author MrTang
 * @version 1.0
 * @date 2021/1/17
 * @name TestRedis
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatentApplication.class)
public class TestRedis {
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Test
    public void redisTest(){
        ValueOperations<String,String> hashOperations = stringRedisTemplate.opsForValue();
        hashOperations.set("33","我是你大巴");
//        String value = hashOperations.get("33");
    }
}
