package com.vaulka.kit.minio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上传信息
 *
 * @author Vaulka
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadInfo {

    /**
     * 分片上传事件ID
     */
    private String uploadId;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件路径
     */
    private String path;

}
