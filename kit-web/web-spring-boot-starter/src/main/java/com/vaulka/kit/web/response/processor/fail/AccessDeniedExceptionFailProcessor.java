package com.vaulka.kit.web.response.processor.fail;


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.security.access.AccessDeniedException;

/**
 * 访问权限异常处理器
 *
 * @author Vaulka
 */
@ConditionalOnClass(AccessDeniedException.class)
public class AccessDeniedExceptionFailProcessor implements FailProcessor<AccessDeniedException> {

    @Override
    public Integer code() {
        return 403;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == AccessDeniedException.class;
    }

    /**
     * 默认错误信息
     */
    public static final String MESSAGE = "访问凭证已过期，请重新登录";

    @Override
    public Object exec(AccessDeniedException exception) {
        return exec(exception, MESSAGE);
    }

}
