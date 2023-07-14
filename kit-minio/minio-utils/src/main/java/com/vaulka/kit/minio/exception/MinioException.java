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

    public MinioException(String message) {
        super(message);
    }

    public MinioException(String message, Exception e) {
        super(message, e);
    }

}
