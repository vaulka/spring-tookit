package com.vaulka.kit.minio.exception;


import java.io.Serial;

/**
 * MinIO 异常
 *
 * @author Vaulka
 */
public class MinioException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8939776616730992066L;

    /**
     * MinIO 异常
     *
     * @param message 异常信息
     */
    public MinioException(String message) {
        super(message);
    }

    /**
     * MinIO 异常
     *
     * @param message 异常信息
     * @param e       异常
     */
    public MinioException(String message, Exception e) {
        super(message, e);
    }

}
