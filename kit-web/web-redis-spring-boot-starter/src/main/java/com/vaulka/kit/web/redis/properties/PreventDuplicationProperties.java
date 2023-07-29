package com.vaulka.kit.web.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 防重检测配置
 *
 * @author Vaulka
 **/
@Data
@ConfigurationProperties(prefix = "kit.redis.prevent-duplication")
public class PreventDuplicationProperties {

    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * 异常信息
     */
    private String exceptionMessage = "当前请求过于频繁，请稍后再试";

}
