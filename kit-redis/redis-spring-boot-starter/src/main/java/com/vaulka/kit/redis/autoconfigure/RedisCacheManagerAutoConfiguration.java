package com.vaulka.kit.redis.autoconfigure;

import com.vaulka.kit.redis.manager.RedisAutoCacheManager;
import com.vaulka.kit.redis.properties.RedisCacheProperties;
import com.vaulka.kit.redis.properties.RedisCacheableProperties;
import com.vaulka.kit.redis.serializer.RegexRedisSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Redis 缓存自动装配
 *
 * @author Vaulka
 **/
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties({CacheProperties.class, RedisCacheableProperties.class})
public class RedisCacheManagerAutoConfiguration {

    /**
     * 配置 RedisCacheConfiguration
     *
     * @param cacheProperties     cacheProperties
     * @param cacheableProperties cacheableProperties
     * @param keySerializer       keySerializer
     * @param valueSerializer     valueSerializer
     * @return RedisCacheConfiguration
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(RedisCacheProperties cacheProperties,
                                                           RedisCacheableProperties cacheableProperties,
                                                           RegexRedisSerializer keySerializer,
                                                           GenericJackson2JsonRedisSerializer valueSerializer) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer));
        // 设置缓存过期时间
        return switch (cacheableProperties.getTimeUnit()) {
            case SECONDS -> configuration.entryTtl(Duration.ofSeconds(cacheableProperties.getTime()));
            case MINUTES -> configuration.entryTtl(Duration.ofMinutes(cacheableProperties.getTime()));
            case HOURS -> configuration.entryTtl(Duration.ofHours(cacheableProperties.getTime()));
            default -> configuration.entryTtl(Duration.ofDays(cacheableProperties.getTime()));
        };
    }

    @Bean
    @ConditionalOnMissingBean(CacheManagerCustomizers.class)
    public CacheManagerCustomizers cacheManagerCustomizers(ObjectProvider<List<CacheManagerCustomizer<?>>> customizers) {
        return new CacheManagerCustomizers(customizers.getIfAvailable());
    }

    @Bean
    @Primary
    public RedisCacheManager redisCacheManager(CacheProperties cacheProperties,
                                               RedisConnectionFactory connectionFactory,
                                               CacheManagerCustomizers customizerInvoker,
                                               RedisCacheConfiguration redisCacheConfiguration) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        List<String> cacheNames = cacheProperties.getCacheNames();
        Map<String, RedisCacheConfiguration> initialCaches = new LinkedHashMap<>();
        if (!cacheNames.isEmpty()) {
            Map<String, RedisCacheConfiguration> cacheConfigMap = new LinkedHashMap<>(cacheNames.size());
            cacheNames.forEach(it -> cacheConfigMap.put(it, redisCacheConfiguration));
            initialCaches.putAll(cacheConfigMap);
        }
        RedisAutoCacheManager cacheManager = new RedisAutoCacheManager(redisCacheWriter, redisCacheConfiguration, true, initialCaches);
        return customizerInvoker.customize(cacheManager);
    }

}
