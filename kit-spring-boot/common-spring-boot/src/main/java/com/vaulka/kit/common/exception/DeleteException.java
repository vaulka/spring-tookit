package com.vaulka.kit.common.exception;

import java.io.Serial;

/**
 * 删除异常
 *
 * @author Vaulka
 */
public class DeleteException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8412329462247258183L;

    public DeleteException(String message) {
        super(message);
    }

    /**
     * 校验删除 SQL
     *
     * @param message 异常信息
     * @param result  结果
     */
    public static void validation(String message, Boolean result) {
        if (!result) {
            throw new DeleteException(message);
        }
    }

    /**
     * 校验删除 SQL
     *
     * @param message     异常信息
     * @param deleteCount 删除总数
     */
    public static void validation(String message, Integer deleteCount) {
        validation(message, deleteCount, 1);
    }

    /**
     * 校验删除 SQL
     *
     * @param message   异常信息
     * @param rowCount  删除总数
     * @param dataCount 数据总数
     */
    public static void validation(String message, Integer rowCount, Integer dataCount) {
        if (!rowCount.equals(dataCount)) {
            throw new DeleteException(message);
        }
    }

}
