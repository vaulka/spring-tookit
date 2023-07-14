package com.vaulka.kit.minio.autoconfigure;

import com.vaulka.kit.minio.properties.MinioProperties;
import com.vaulka.kit.minio.utils.MinioUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 文件服务器自动装配
 *
 * @author Vaulka
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({MinioProperties.class})
public class MinioAutoConfiguration {

    /**
     * 注入文件服务器工具
     *
     * @param properties 文件服务器参数配置
     * @return 注入文件服务器工具
     */
    @Bean
    public MinioUtils minioUtils(MinioProperties properties) {
        return new MinioUtils(properties.getEndpoint(), properties.getBucket(),
                properties.getAccessKey(), properties.getSecretKey(),
                properties.getFilePrefix(), properties.getRenameType());
    }

}
