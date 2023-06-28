package com.vaulka.kit.desensitization.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.vaulka.kit.desensitization.annotation.Desensitization;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 数据脱敏序列化配置
 *
 * @author Vaulka
 */
@NoArgsConstructor
@AllArgsConstructor
public class DesensitizationJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 数据脱敏
     */
    private Desensitization desensitization;

    /**
     * 数据脱敏处理器列表
     */
    private static final Map<String, Function<String, String>> HANDLERS = new ConcurrentHashMap<>(16);

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Function<String, String> handler;
        String result = value;
        try {
            handler = this.getHandler(desensitization);
            result = handler.apply(value);
        } catch (InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException
                 | NoSuchMethodException e) {
            e.printStackTrace();
        }
        gen.writeString(result);
    }

    /**
     * 获取数据脱敏处理器
     *
     * @param desensitization 数据脱敏
     * @return 数据脱敏处理器
     * @throws NoSuchMethodException     无搜索方法异常
     * @throws InvocationTargetException 调用目标异常
     * @throws InstantiationException    实例化异常
     * @throws IllegalAccessException    非法访问异常
     */
    private Function<String, String> getHandler(Desensitization desensitization) throws
            NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Class<? extends Function<String, String>> type = desensitization.handler();
        String key = type.getName();
        Function<String, String> handler = HANDLERS.get(key);
        if (handler != null) {
            return handler;
        }
        handler = type.getDeclaredConstructor().newInstance();
        HANDLERS.put(key, handler);
        return handler;
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Desensitization desensitization = property.getAnnotation(Desensitization.class);
        if (property.getType().getRawClass() == String.class
                && desensitization != null) {
            return new DesensitizationJsonSerializer(desensitization);
        }
        return prov.findValueSerializer(property.getType(), property);
    }

}
