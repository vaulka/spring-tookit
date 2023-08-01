package com.vaulka.kit.web.response.processor.supports.springdoc;

import com.vaulka.kit.web.response.processor.supports.SupportsReturnTypeProcessor;
import org.springdoc.webmvc.api.MultipleOpenApiWebMvcResource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.MethodParameter;

/**
 * 不拦截 {@link MultipleOpenApiWebMvcResource} 相关资源请求
 *
 * @author Vaulka
 */
@ConditionalOnClass(MultipleOpenApiWebMvcResource.class)
public class MultipleOpenApiWebMvcResourceSupportsReturnTypeProcessor implements SupportsReturnTypeProcessor {

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getContainingClass() == MultipleOpenApiWebMvcResource.class;
    }

}
