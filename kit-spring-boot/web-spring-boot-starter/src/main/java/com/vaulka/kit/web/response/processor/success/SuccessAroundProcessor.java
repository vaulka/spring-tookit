package com.vaulka.kit.web.response.processor.success;

/**
 * 【成功】全局响应环绕处理器
 *
 * @author Vaulka
 */
public interface SuccessAroundProcessor {

    /**
     * 判断是否命中处理器
     * <p>
     * 如果命中，则不会执行 {@link SuccessProcessor} 处理器相关业务处理
     *
     * @param body body
     * @return 判断是否命中处理器
     */
    boolean isHitProcessor(Object body);

    /**
     * 处理器选择顺序
     * <p>
     * 从小到大排序，只取符合 {@link SuccessAroundProcessor#isHitProcessor(Object)} 条件的第一个处理器
     *
     * @return 处理器选择顺序
     */
    default Integer order() {
        return Integer.MAX_VALUE;
    }

    /**
     * 返回的响应结果
     * <p>
     * 应用场景：
     * 譬如对接某第三方系统，成功后，也要返回特定的响应结果格式等...
     *
     * @param body body
     * @return 返回的响应结果
     */
    Object exec(Object body);

    /**
     * 执行 {@link SuccessProcessor#exec(Object)} 后的后置操作
     * <p>
     * 应用场景：
     * 成功后，进行邮箱告警等...
     *
     * @param body body
     */
    default void execAfter(Object body) {
    }

}
