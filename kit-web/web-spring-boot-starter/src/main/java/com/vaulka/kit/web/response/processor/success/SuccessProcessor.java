package com.vaulka.kit.web.response.processor.success;

import com.vaulka.kit.common.response.GlobalResult;
import org.springframework.http.HttpStatus;

/**
 * 【成功】全局响应处理器
 *
 * @author Vaulka
 */
public interface SuccessProcessor {

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
    default Integer code() {
        return GlobalResult.SUCCESS_CODE;
    }

    /**
     * 判断是否命中处理器
     *
     * @return 判断是否命中处理器
     */
    boolean isHitProcessor();

    /**
     * 处理器选择顺序
     * <p>
     * 从小到大排序，只取符合 {@link SuccessProcessor#isHitProcessor()} 条件的最大排序值处理器
     *
     * @return 处理器选择顺序
     */
    default int order() {
        return 0;
    }

    /**
     * 返回的响应结果
     *
     * @param body body
     * @return 返回的响应结果
     */
    default Object exec(Object body) {
        return this.buildResult(body, GlobalResult.SUCCESS_MESSAGE);
    }

    /**
     * 执行 {@link SuccessProcessor#exec(Object)} 后的后置操作
     * <p>
     * 应用场景：
     * 执行完业务后，进行邮箱告警等...
     *
     * @param body body
     */
    default void execAfter(Object body) {
    }

    /**
     * 构建【成功】的全局响应数据
     *
     * @param <T>     T
     * @param obj     obj
     * @param message message
     * @return 构建【成功】的全局响应数据
     */
    default <T> GlobalResult<T> buildResult(T obj, String message) {
        return new GlobalResult<T>()
                .setCode(this.code())
                .setMessage(message)
                .setData(obj);
    }

}
