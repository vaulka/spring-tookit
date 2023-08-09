package com.vaulka.kit.common.exception;

import java.io.Serial;

/**
 * 保存异常
 *
 * @author Vaulka
 */
public class InsertException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4776662733913410838L;

    /**
     * 保存异常
     *
     * @param message 异常信息
     */
    public InsertException(String message) {
        super(message);
    }

    /**
     * 校验保存 SQL
     *
     * @param message 异常信息
     * @param result  结果
     */
    public static void validation(String message, Boolean result) {
        if (!result) {
            throw new InsertException(message);
        }
    }

    /**
     * 校验保存 SQL
     *
     * @param message  异常信息
     * @param rowCount 保存总数
     */
    public static void validation(String message, Integer rowCount) {
        validation(message, rowCount, 1);
    }

    /**
     * 校验保存 SQL
     *
     * @param message   异常信息
     * @param rowCount  保存总数
     * @param dataCount 数据总数
     */
    public static void validation(String message, Integer rowCount, Integer dataCount) {
        if (!rowCount.equals(dataCount)) {
            throw new InsertException(message);
        }
    }

}
