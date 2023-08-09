package com.vaulka.kit.common.exception;

import java.io.Serial;

/**
 * HTTP 请求异常
 *
 * @author Vaulka
 */
public class HttpException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5701953556548839172L;

    /**
     * HTTP 请求异常
     *
     * @param message 异常信息
     */
    public HttpException(String message, Exception e) {
        super(message, e);
    }

}