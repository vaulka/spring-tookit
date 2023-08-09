package com.vaulka.kit.common.exception;

import java.io.Serial;

/**
 * 频率异常
 *
 * @author Vaulka
 */
public class FrequencyException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2596232230763766530L;

    /**
     * 频率异常
     *
     * @param message 异常信息
     */
    public FrequencyException(String message) {
        super(message);
    }

}
