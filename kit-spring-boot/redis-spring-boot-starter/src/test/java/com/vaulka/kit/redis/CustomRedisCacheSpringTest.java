package com.vaulka.kit.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaulka.kit.redis.autoconfigure.RedisCacheAutoConfiguration;
import com.vaulka.kit.redis.autoconfigure.RedisCacheManagerAutoConfiguration;
import com.vaulka.kit.redis.model.User;
import com.vaulka.kit.redis.properties.RedisCacheProperties;
import com.vaulka.kit.redis.service.Service;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.TestPropertySource;

/**
 * @author Vaulka
 */
@EnableAutoConfiguration
@TestPropertySource("classpath:application-custom.properties")
@SpringBootTest(classes = {RedisCacheAutoConfiguration.class
        , RedisCacheManagerAutoConfiguration.class
        , Service.class
})
public class CustomRedisCacheSpringTest {

    @Resource
    private Service service;

    @Resource
    private RedisCacheProperties properties;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 测试 redis 序列化、反序列化
     */
    @Test
    public void redis() throws JsonProcessingException {
        Long expire;
        int mid = RandomUtils.nextInt();
        User mUser = service.m(mid);
        User mKuser = (User) redisTemplate.opsForValue().get(service.getKey(mid));
        assert objectMapper.writeValueAsString(mUser).equals(objectMapper.writeValueAsString(mKuser));
        expire = redisTemplate.getExpire(service.getKey(mid));
        assert 300 >= expire && expire > 300 - 5;

        mUser = service.m(mid);
        mKuser = (User) redisTemplate.opsForValue().get(service.getKey(mid));
        assert objectMapper.writeValueAsString(mUser).equals(objectMapper.writeValueAsString(mKuser));
        expire = redisTemplate.getExpire(service.getKey(mid));
        assert 300 >= expire && expire > 300 - 5;

        int did = RandomUtils.nextInt();
        User dUser = service.d(did);
        User dKuser = (User) redisTemplate.opsForValue().get(service.getKey(did));
        assert objectMapper.writeValueAsString(dUser).equals(objectMapper.writeValueAsString(dKuser));
        expire = redisTemplate.getExpire(service.getKey(did));
        assert 864000 >= expire && expire > 864000 - 5;

        dUser = service.d(did);
        dKuser = (User) redisTemplate.opsForValue().get(service.getKey(did));
        assert objectMapper.writeValueAsString(dUser).equals(objectMapper.writeValueAsString(dKuser));
        expire = redisTemplate.getExpire(service.getKey(did));
        assert 864000 >= expire && expire > 864000 - 5;

        int deid = RandomUtils.nextInt();
        User deUser = service.de(deid);
        User deKuser = (User) redisTemplate.opsForValue().get(service.getKey(deid));
        assert objectMapper.writeValueAsString(deUser).equals(objectMapper.writeValueAsString(deKuser));
        expire = redisTemplate.getExpire(service.getKey(deid));
        assert 604800 >= expire && expire > 604800 - 5;

        deUser = service.de(deid);
        deKuser = (User) redisTemplate.opsForValue().get(service.getKey(deid));
        assert objectMapper.writeValueAsString(deUser).equals(objectMapper.writeValueAsString(deKuser));
        expire = redisTemplate.getExpire(service.getKey(deid));
        assert 604800 >= expire && expire > 604800 - 5;

        String prefix = StringUtils.isBlank(properties.getPrefix()) ? "" : properties.getPrefix() + ":";
        assert stringRedisTemplate.hasKey(prefix+service.getKey(deid));
    }

}
