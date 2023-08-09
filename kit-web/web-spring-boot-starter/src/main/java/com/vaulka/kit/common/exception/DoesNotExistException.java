package com.vaulka.kit.common.exception;

import java.io.Serial;

/**
 * 不存在异常
 *
 * @author Vaulka
 */
public class DoesNotExistException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1875633352439939019L;

    /**
     * 不存在异常
     *
     * @param message 异常信息
     */
    public DoesNotExistException(String message) {
        super(message);
    }

}
