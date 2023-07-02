package com.vaulka.kit.web.response.processor.supports.springdoc;

import com.vaulka.kit.web.response.processor.supports.SupportsReturnTypeProcessor;
import org.springdoc.webmvc.api.OpenApiWebMvcResource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.MethodParameter;

/**
 * 不拦截 {@link OpenApiWebMvcResource} 相关资源请求
 *
 * @author Vaulka
 */
@ConditionalOnClass(OpenApiWebMvcResource.class)
public class OpenApiWebMvcResourceSupportsReturnTypeProcessor implements SupportsReturnTypeProcessor {

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getContainingClass() == OpenApiWebMvcResource.class;
    }

}
