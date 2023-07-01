package com.vaulka.kit.redis.autoconfigure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.vaulka.kit.redis.properties.RedisCacheProperties;
import com.vaulka.kit.redis.serializer.RegexRedisSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Redis 缓存自动装配
 *
 * @author Vaulka
 **/
@EnableCaching
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({RedisCacheProperties.class})
public class RedisCacheAutoConfiguration {

    /**
     * 配置 KEY 序列化
     *
     * @param properties properties
     * @return KEY 序列化
     */
    @Bean
    public RegexRedisSerializer keySerializer(RedisCacheProperties properties) {
        return new RegexRedisSerializer(properties.getPrefix());
    }

    /**
     * 配置 VALUE 序列化
     *
     * @return VALUE 序列化
     */
    @Bean
    public GenericJackson2JsonRedisSerializer valueSerializer() {
        ObjectMapper objectMapper = JsonMapper.builder()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .build()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    /**
     * 配置 RedisTemplate
     *
     * @param factory         factory
     * @param properties      properties
     * @param keySerializer   keySerializer
     * @param valueSerializer valueSerializer
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory,
                                                       RedisCacheProperties properties,
                                                       RegexRedisSerializer keySerializer,
                                                       GenericJackson2JsonRedisSerializer valueSerializer) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置是否开启事务（仅支持单机，不支持集群）
        template.setEnableTransactionSupport(properties.getEnableTransactionSupport());
        template.setConnectionFactory(factory);
        template.setDefaultSerializer(valueSerializer);
        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashKeySerializer(keySerializer);
        template.setHashValueSerializer(valueSerializer);
        return template;
    }

    /**
     * 将 Redis 事务与 JDBC 事务共同管理
     *
     * @param dataSource dataSource
     * @return 事务管理器
     */
    @ConditionalOnProperty(value = "kit.redis.enable-transaction-support", havingValue = "true")
    @ConditionalOnClass(DataSourceTransactionManager.class)
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
