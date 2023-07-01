package com.vaulka.kit.redis.service;

import com.vaulka.kit.redis.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

import java.text.MessageFormat;

/**
 * @author Vaulka
 **/
@RequiredArgsConstructor

public class Service {

    public static final String KEY = "user";

    public String getKey(Integer id) {
        return MessageFormat.format("{0}::{1}", KEY, id.toString());
    }

    @Cacheable(value = KEY + "#5m", key = "#id")
    public User m(int id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @Cacheable(value = KEY + "#10d", key = "#id")
    public User d(int id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @Cacheable(value = KEY, key = "#id")
    public User de(int id) {
        User user = new User();
        user.setId(id);
        return user;
    }

}
