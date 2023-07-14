package com.vaulka.kit.minio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 重命名类型
 *
 * @author Vaulka
 **/
@Getter
@AllArgsConstructor
public enum RenameType {

    /**
     * 保持不变
     */
    REMAIN() {
        @Override
        public String rename(String fileName) {
            return fileName;
        }
    },

    /**
     * 时间戳
     */
    TIMESTAMP() {
        @Override
        public String rename(String fileName) {
            int index = fileName.lastIndexOf(".");
            if (index == -1) {
                return fileName;
            }
            long timestamp = System.currentTimeMillis();
            String formatName = fileName.substring(index);
            String path = fileName.substring(0, index);
            return path + "-" + timestamp + formatName;
        }
    },

    /**
     * 时间戳
     */
    UUID() {
        @Override
        public String rename(String fileName) {
            int index = fileName.lastIndexOf(".");
            if (index == -1) {
                return fileName;
            }
            String uuid = java.util.UUID.randomUUID().toString().toLowerCase().replace("-", "");
            String formatName = fileName.substring(index);
            String path = fileName.substring(0, index);
            return path + "-" + uuid + formatName;
        }
    },

    ;

    /**
     * 重命名
     *
     * @param fileName 文件名称
     * @return 重命名后的名称
     */
    public abstract String rename(String fileName);

}
