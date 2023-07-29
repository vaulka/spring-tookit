package com.vaulka.kit.dynamic.datasource.around;

import com.vaulka.kit.dynamic.datasource.annotation.DynamicDatasource;
import com.vaulka.kit.dynamic.datasource.core.DynamicDatasourceContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;

import java.util.Optional;

/**
 * 动态数据源切换处理
 * <p>
 * "@Order" 保证执行顺序优先级在最前面
 *
 * @author Vaulka
 **/
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class DynamicDatasourceAspect {

    @Around("@within(com.vaulka.kit.dynamic.datasource.annotation.DynamicDatasource) " +
            "|| (@annotation(com.vaulka.kit.dynamic.datasource.annotation.DynamicDatasource)) ")
    public Object exec(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 先在方法上寻找该注解
        DynamicDatasource dataSource = Optional.ofNullable(AnnotationUtils.findAnnotation(signature.getMethod(), DynamicDatasource.class))
                // 方法上没有的话，则再类上寻找该注解
                .orElse(AnnotationUtils.findAnnotation(signature.getDeclaringType(), DynamicDatasource.class));
        if (dataSource != null && StringUtils.isNotBlank(dataSource.value())) {
            DynamicDatasourceContextHolder.set(dataSource.value());
        }
        try {
            return point.proceed();
        } finally {
            DynamicDatasourceContextHolder.clear();
        }
    }

}
