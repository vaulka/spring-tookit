package com.vaulka.kit.web.response.processor.supports.actuator;

import com.vaulka.kit.web.response.processor.supports.SupportsReturnTypeProcessor;
import com.vaulka.kit.web.utils.SpringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.MethodParameter;


/**
 * 不拦截 Actuator 相关资源请求
 *
 * @author Vaulka
 */
@ConditionalOnClass(Endpoint.class)
public class ActuatorReturnTypeProcessor implements SupportsReturnTypeProcessor {

    /**
     * Actuator 默认 URL 前缀
     */
    private static final String DEFAULT_ACTUATOR_URL_PREFIX = "/actuator";

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        HttpServletRequest request = SpringUtils.getHttpServletRequest(true);
        return request != null && request.getRequestURI().startsWith(DEFAULT_ACTUATOR_URL_PREFIX);
    }

}
