package com.vaulka.kit.web.redis.aspect.around;

import com.vaulka.kit.common.exception.FrequencyException;
import com.vaulka.kit.web.redis.annotation.DistributedLock;
import com.vaulka.kit.web.redis.properties.DistributedLockProperties;
import com.vaulka.kit.web.redis.utils.SpElUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.integration.redis.util.RedisLockRegistry;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

/**
 * 分布式锁实现
 *
 * @author Vaulka
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class DistributedLockAspect {

    private final RedisLockRegistry registry;
    private final DistributedLockProperties properties;

    /**
     * 井号
     */
    private static final String HASHTAG = "#";

    /**
     * 分布式锁实现业务逻辑
     *
     * @param point point
     * @return 业务信息
     * @throws Throwable Throwable
     */
    @Around("@within(com.vaulka.kit.web.redis.annotation.DistributedLock)" +
            "|| @annotation(com.vaulka.kit.web.redis.annotation.DistributedLock) ")
    public Object exec(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        // 先在方法上寻找该注解
        DistributedLock distributedLock = Optional.ofNullable(AnnotationUtils.findAnnotation(method, DistributedLock.class))
                // 方法上没有的话，则再类上寻找该注解
                .orElse(AnnotationUtils.findAnnotation(signature.getDeclaringType(), DistributedLock.class));
        if (distributedLock == null) {
            return point.proceed();
        }
        String key = distributedLock.value();
        key = key.contains(HASHTAG) ? SpElUtils.parse(key, method, point.getArgs()) : key;
        Lock lock = registry.obtain(key);
        boolean isHaveLock = switch (distributedLock.getLockMethod()) {
            case lock -> {
                lock.lock();
                yield true;
            }
            case tryLockTime -> lock.tryLock(distributedLock.time(), distributedLock.unit());
            case tryLock -> lock.tryLock();
        };
        if (!isHaveLock) {
            throw new FrequencyException(properties.getExceptionMessage());
        }
        try {
            return point.proceed();
        } finally {
            lock.unlock();
        }
    }

}
