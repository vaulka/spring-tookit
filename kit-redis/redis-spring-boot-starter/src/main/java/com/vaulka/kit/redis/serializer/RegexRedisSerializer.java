package com.vaulka.kit.redis.serializer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Redis 前缀序列化配置
 * <p>
 * 增加统一的前缀，适用于多项目用同一个 Redis 库时，通过项目前缀进行区分
 *
 * @author Vaulka
 **/
public class RegexRedisSerializer implements RedisSerializer<String> {

    private final String regex;

    /**
     * 初始化 Redis 前缀序列化配置
     *
     * @param prefix 前缀信息
     */
    public RegexRedisSerializer(String prefix) {
        if (StringUtils.isBlank(prefix)) {
            this.regex = "";
        } else {
            this.regex = prefix + ":";
        }
    }

    private final Charset UTF_8 = StandardCharsets.UTF_8;

    @Override
    public String deserialize(@Nullable byte[] bytes) {
        return bytes == null ? null : new String(bytes, UTF_8).replaceFirst(regex, "");
    }

    @Override
    public byte[] serialize(@Nullable String key) {
        return key == null ? null : (regex + key).getBytes(UTF_8);
    }

}