package com.vaulka.kit.minio.properties;

import com.vaulka.kit.minio.enums.RenameType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文件服务器参数配置
 *
 * @author Vaulka
 */
@Getter
@Setter
@ConfigurationProperties("kit.minio")
public class MinioProperties {

    /**
     * endpoint
     */
    private String endpoint = "http://localhost:9000";

    /**
     * bucket
     */
    private String bucket = "default";

    /**
     * accessKey
     */
    private String accessKey = "minioadmin";

    /**
     * secretKey
     */
    private String secretKey = "minioadmin";

    /**
     * 文件前缀
     */
    private String filePrefix = "/";

    /**
     * 重命名类型
     */
    private RenameType renameType = RenameType.TIMESTAMP;

}
