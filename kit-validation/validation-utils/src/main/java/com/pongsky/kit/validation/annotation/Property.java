package com.pongsky.kit.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验时表示的属性名称
 *
 * @author Vaulka
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {

    /**
     * 属性名称
     *
     * @return 属性名称
     */
    String value();

}
