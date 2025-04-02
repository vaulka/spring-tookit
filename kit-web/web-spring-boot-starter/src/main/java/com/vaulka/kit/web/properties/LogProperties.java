package com.vaulka.kit.web.properties;

import com.vaulka.kit.web.enums.HttpMethod;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;

/**
 * 日志打印参数配置
 *
 * @author Vaulka
 **/
@Data
@ConfigurationProperties("kit.web.log")
public class LogProperties {

    /**
     * 控制层日志是否启用
     */
    private boolean controllerLogEnabled = true;

    /**
     * URL 匹配规则列表
     */
    private List<HttpMethod> types = Arrays.asList(HttpMethod.values());

    /**
     * 请求成功日志是否启用
     */
    private boolean successLogEnabled = true;

    /**
     * 请求失败日志是否启用
     */
    private boolean failLogEnabled = true;

}
