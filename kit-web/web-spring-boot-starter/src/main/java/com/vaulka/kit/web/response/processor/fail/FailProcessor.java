package com.vaulka.kit.web.response.processor.fail;

import com.vaulka.kit.common.response.GlobalResult;
import com.vaulka.kit.common.response.annotation.ResponseResult;
import com.vaulka.kit.web.utils.SpringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * 【失败】全局响应处理器
 *
 * @author Vaulka
 */
public interface FailProcessor<T extends Throwable> {

    /**
     * log
     */
    Logger LOG = LoggerFactory.getLogger(FailProcessor.class);

    /**
     * 返回的浏览器状态码
     *
     * @return 返回的浏览器状态码
     */
    default HttpStatus httpStatus() {
        return HttpStatus.OK;
    }

    /**
     * 返回的 code
     *
     * @return 返回的 code
     */
    Integer code();

    /**
     * 判断是否命中处理器
     *
     * @param exception 异常
     * @return 判断是否命中处理器
     */
    boolean isHitProcessor(Throwable exception);

    /**
     * 处理器选择顺序
     * <p>
     * 从小到大排序，只取符合 {@link FailProcessor#isHitProcessor(Throwable)} 条件的最大排序值处理器
     *
     * @return 处理器选择顺序
     */
    default int order() {
        return 0;
    }

    /**
     * 返回的响应结果
     *
     * @param exception 异常
     * @return 返回的响应结果
     */
    default Object exec(T exception) {
        String message = exception.getLocalizedMessage();
        return this.exec(exception, message);
    }

    /**
     * 返回的响应结果
     *
     * @param exception 异常
     * @param message   自定义异常信息
     * @return 返回的响应结果
     */
    default Object exec(T exception, String message) {
        if (message == null) {
            message = exception.getLocalizedMessage();
        }
        HttpServletRequest request = SpringUtils.getHttpServletRequest(true);
        if (request == null) {
            return message;
        }
        boolean isGlobalResult = request.getAttribute(ResponseResult.class.getName()) != null;
        return isGlobalResult ? this.buildResult(message) : message;
    }

    /**
     * 是否打印异常堆栈信息
     *
     * @return 是否打印异常堆栈信息
     */
    default boolean isSprintStackTrace() {
        return false;
    }

    /**
     * 日志打印
     *
     * @param exception 异常
     */
    default void log(T exception) {
        String reqId = SpringUtils.getReqId();
        LOG.error("req ID [{}] exception message: [{}]", reqId, Optional.ofNullable(exception.getLocalizedMessage()).orElse(exception.getMessage()));
        if (isSprintStackTrace()) {
            exception.printStackTrace();
        }
    }

    /**
     * 执行 {@link FailProcessor#exec(Throwable)} 后的后置操作
     * <p>
     * 应用场景：
     * 失败后，进行邮箱告警等...
     *
     * @param exception 异常
     */
    default void execAfter(T exception) {
    }

    /**
     * 构建全局响应数据
     *
     * @param message message
     * @return 构建全局响应数据
     */
    default GlobalResult<Void> buildResult(String message) {
        return new GlobalResult<Void>()
                .setCode(code())
                .setMessage(message);
    }

}
