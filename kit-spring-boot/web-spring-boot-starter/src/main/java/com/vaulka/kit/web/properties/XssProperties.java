package com.vaulka.kit.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

import static com.vaulka.kit.web.properties.XssProperties.PREFIX;

/**
 * XSS 防御 参数配置
 *
 * @author Vaulka
 **/
@Data
@ConfigurationProperties(PREFIX)
public class XssProperties {

    /**
     * 参数配置 前缀
     */
    public static final String PREFIX = "kit.web.xss";

    /**
     * URL 匹配规则列表
     */
    private List<String> urlPatterns = Collections.singletonList("/*");

    /**
     * 排除列表
     */
    private List<String> excludes = Collections.emptyList();

}
