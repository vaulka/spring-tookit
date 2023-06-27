package com.vaulka.kit.ip.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


/**
 * IP 参数配置
 *
 * @author Vaulka
 */
@Getter
@Setter
@Validated
@ConfigurationProperties("kit.ip")
public class IpProperties {

    /**
     * ip2region.xdb 文件路径
     */
    @NotBlank
    private String dbPath = "classpath:ip2region/ip2region.xdb";

}
