package com.vaulka.kit.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;


/**
 * XSS 防御 参数配置
 *
 * @author Vaulka
 **/
@Data
@ConfigurationProperties("kit.web.xss")
public class XssProperties {

    /**
     * URL 匹配规则列表
     */
    private List<String> urlPatterns = Collections.singletonList("/*");

    /**
     * 排除列表
     */
    private List<String> excludes = Collections.emptyList();

}
