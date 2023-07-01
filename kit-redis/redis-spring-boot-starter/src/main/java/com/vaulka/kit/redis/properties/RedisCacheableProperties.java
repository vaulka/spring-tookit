package com.vaulka.kit.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * Redis 缓存配置
 *
 * @author Vaulka
 **/
@Data
@ConfigurationProperties(prefix = "kit.redis.cacheable")
public class RedisCacheableProperties {

    /**
     * {@link org.springframework.cache.annotation.Cacheable} 默认缓存时长
     */
    private Long time = 30L;

    /**
     * {@link org.springframework.cache.annotation.Cacheable} 默认缓存时长单位
     * <p>
     * 仅支持 DAYS、HOURS
     */
    private TimeUnit timeUnit = TimeUnit.DAYS;

}
