package com.vaulka.kit.web.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 分布式锁配置
 *
 * @author Vaulka
 **/
@Data
@ConfigurationProperties(prefix = "kit.redis.distributed-lock")
public class DistributedLockProperties {

    /**
     * Redis 分布式锁缓存前缀
     */
    private String prefix = "distributed-lock";

    /**
     * 锁过期时间，单位毫秒
     */
    private long expire = 60000L;

    /**
     * 异常信息
     */
    private String exceptionMessage = "当前请求资源已被其他请求占用，请稍后重试";

}
