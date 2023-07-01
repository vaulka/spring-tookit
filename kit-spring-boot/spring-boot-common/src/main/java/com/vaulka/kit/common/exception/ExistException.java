package com.vaulka.kit.common.exception;

import java.io.Serial;

/**
 * 存在异常
 *
 * @author Vaulka
 */
public class ExistException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4469874622807634893L;

    public ExistException(String message) {
        super(message);
    }

}
