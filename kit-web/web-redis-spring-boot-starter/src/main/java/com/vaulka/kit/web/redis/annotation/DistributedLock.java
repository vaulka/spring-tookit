package com.vaulka.kit.web.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 分布式锁
 * <p>
 * 错误示范：如无特殊情况请勿将注解作用于 controller，如业务代码调用 service 后续 {@link Lock#unlock()} 抛出异常后无法进行事务回滚。
 * 正解：应作用于 service 或 其他业务方法。
 *
 * @author Vaulka
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DistributedLock {

    /**
     * 用法同 {@link org.springframework.cache.annotation.Cacheable#key()}
     *
     * @return key
     */
    String value() default "";

    /**
     * 获取锁的方式
     *
     * @return 获取锁的方式
     */
    GetLockMethod getLockMethod() default GetLockMethod.lock;

    /**
     * {@link Lock#tryLock(long, TimeUnit)} 其中的 time 参数
     *
     * @return time
     */
    long time() default 10;

    /**
     * {@link Lock#tryLock(long, TimeUnit)} 其中的 unit 参数
     *
     * @return unit
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 获取锁的方式
     */
    enum GetLockMethod {

        /**
         * {@link Lock#lock()}
         */
        lock,

        /**
         * {@link Lock#tryLock()}
         */
        tryLock,

        /**
         * {@link Lock#tryLock(long, TimeUnit)}
         */
        tryLockTime

    }
}