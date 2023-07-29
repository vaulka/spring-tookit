package com.vaulka.kit.web.redis.autoconfigure;

import com.vaulka.kit.redis.properties.RedisCacheProperties;
import com.vaulka.kit.web.redis.aspect.afterreturning.CacheRemoveAspect;
import com.vaulka.kit.web.redis.aspect.around.DistributedLockAspect;
import com.vaulka.kit.web.redis.aspect.before.PreventDuplicationAspect;
import com.vaulka.kit.web.redis.aspect.before.RateLimitAspect;
import com.vaulka.kit.web.redis.properties.DistributedLockProperties;
import com.vaulka.kit.web.redis.properties.PreventDuplicationProperties;
import com.vaulka.kit.web.redis.properties.RateLimitProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

import java.text.MessageFormat;

/**
 * Web Redis 缓存自动装配
 *
 * @author Vaulka
 **/
@EnableCaching
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({
        DistributedLockProperties.class,
        PreventDuplicationProperties.class,
        RateLimitProperties.class
})
@Import({
        CacheRemoveAspect.class,
        DistributedLockAspect.class,
        PreventDuplicationAspect.class,
        RateLimitAspect.class
})
public class WebRedisCacheAutoConfiguration {

    /**
     * 配置分布式锁实现
     *
     * @param cacheProperties        cacheProperties
     * @param lockProperties         lockProperties
     * @param redisConnectionFactory redisConnectionFactory
     * @return 分布式锁实现
     */
    @Bean
    public RedisLockRegistry redisLockRegistry(RedisCacheProperties cacheProperties,
                                               DistributedLockProperties lockProperties,
                                               RedisConnectionFactory redisConnectionFactory) {
        String registryKey;
        if (StringUtils.isNotBlank(cacheProperties.getPrefix()) && StringUtils.isNotBlank(lockProperties.getPrefix())) {
            registryKey = MessageFormat.format("{0}:{1}", cacheProperties.getPrefix(), lockProperties.getPrefix());
        } else if (StringUtils.isNotBlank(cacheProperties.getPrefix())) {
            registryKey = cacheProperties.getPrefix();
        } else if (StringUtils.isNotBlank(lockProperties.getPrefix())) {
            registryKey = lockProperties.getPrefix();
        } else {
            registryKey = "";
        }
        return new RedisLockRegistry(redisConnectionFactory, registryKey, lockProperties.getExpire());
    }

}
