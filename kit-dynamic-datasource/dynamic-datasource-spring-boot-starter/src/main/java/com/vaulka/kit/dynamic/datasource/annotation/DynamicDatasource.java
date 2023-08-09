package com.vaulka.kit.dynamic.datasource.annotation;


import com.vaulka.kit.dynamic.datasource.core.DynamicDatasourceContextHolder;
import com.vaulka.kit.dynamic.datasource.properties.DynamicDatasourceProperties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义多数据源切换
 * <p>
 * 获取优先级：方法 &gt; 类；如果方法上面有此注解，则优先获取方法上的注解。
 *
 * @author Vaulka
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DynamicDatasource {

    /**
     * 数据源名称
     * <p>
     * 可填写数据源名称或组名，填写规则详情见 {@link DynamicDatasourceProperties}
     * <p>
     * 未填写则使用默认数据源
     *
     * @return 数据源名称
     */
    String value() default DynamicDatasourceContextHolder.DEFAULT_DATA_SOURCE_NAME;

}
