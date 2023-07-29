package com.vaulka.kit.web.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 令牌桶限流配置
 *
 * @author Vaulka
 **/
@Data
@ConfigurationProperties(prefix = "kit.redis.rate-limit")
public class RateLimitProperties {

    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * 令牌每秒恢复个数
     * <p>
     * 默认 QPS：500
     */
    private int bucketRate = 500;

    /**
     * 令牌桶大小
     */
    private int bucketMax = bucketRate * 60;

    /**
     * 异常信息
     */
    private String exceptionMessage = "当前重复请求过于频繁，请稍后再试";

}
