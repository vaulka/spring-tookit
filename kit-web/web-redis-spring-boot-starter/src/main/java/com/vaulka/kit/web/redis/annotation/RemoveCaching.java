package com.vaulka.kit.web.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模糊删除 key 列表
 *
 * @author Vaulka
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RemoveCaching {

    /**
     * 模糊删除 key 列表
     *
     * @return 模糊删除 key 列表
     */
    CacheRemove[] remove() default {};

}
