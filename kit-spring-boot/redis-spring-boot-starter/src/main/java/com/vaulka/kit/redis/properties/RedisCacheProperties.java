package com.vaulka.kit.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis 缓存配置
 *
 * @author Vaulka
 **/
@Data
@ConfigurationProperties(prefix = "kit.redis")
public class RedisCacheProperties {

    /**
     * Redis 缓存前缀
     * <p>
     * 增加统一的前缀，适用于多项目用同一个 Redis 库时，通过项目前缀进行区分
     */
    private String prefix = "";

    /**
     * 是否开启事务支持
     * <p>
     * 需引入 spring-boot-starter-jdbc，配合 JDBC 事务时，才能生效
     */
    private Boolean enableTransactionSupport = false;

}
