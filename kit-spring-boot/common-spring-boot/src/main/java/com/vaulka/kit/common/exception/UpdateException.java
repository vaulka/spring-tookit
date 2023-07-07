package com.vaulka.kit.common.exception;

import java.io.Serial;

/**
 * 更新异常
 *
 * @author Vaulka
 */
public class UpdateException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5688016478526820394L;

    public UpdateException(String message) {
        super(message);
    }

    /**
     * 校验更新 SQL
     *
     * @param message 异常信息
     * @param result  结果
     */
    public static void validation(String message, Boolean result) {
        if (!result) {
            throw new UpdateException(message);
        }
    }

    /**
     * 校验更新 SQL
     *
     * @param message  异常信息
     * @param rowCount 更新总数
     */
    public static void validation(String message, Integer rowCount) {
        validation(message, rowCount, 1);
    }

    /**
     * 校验更新 SQL
     *
     * @param message   异常信息
     * @param rowCount  更新总数
     * @param dataCount 数据总数
     */
    public static void validation(String message, Integer rowCount, Integer dataCount) {
        if (!rowCount.equals(dataCount)) {
            throw new UpdateException(message);
        }
    }

}
