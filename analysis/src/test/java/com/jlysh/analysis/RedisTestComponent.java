package com.jlysh.analysis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 江冷易水寒
 * @data 2021/5/12 10:02
 */
@Component
public class RedisTestComponent {
    @Resource
    private RedisTemplate redisTemplate;
    public void testSave()  {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("total",1);

    }
}
