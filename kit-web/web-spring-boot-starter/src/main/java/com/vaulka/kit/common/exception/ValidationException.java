package com.vaulka.kit.common.exception;

import java.io.Serial;

/**
 * 校验异常
 *
 * @author Vaulka
 */
public class ValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6511791210548696965L;

    /**
     * 校验异常
     *
     * @param message 异常信息
     */
    public ValidationException(String message) {
        super(message);
    }

}
