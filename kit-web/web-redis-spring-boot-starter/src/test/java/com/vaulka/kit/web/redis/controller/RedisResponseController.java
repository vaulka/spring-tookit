package com.vaulka.kit.web.redis.controller;

import com.vaulka.kit.common.response.annotation.ResponseResult;
import com.vaulka.kit.web.redis.annotation.CacheRemove;
import com.vaulka.kit.web.redis.annotation.PreventDuplication;
import com.vaulka.kit.web.redis.annotation.RemoveCaching;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vaulka
 */
@Tag(name = "接口列表")
@ResponseResult
@RestController
@RequiredArgsConstructor
@RequestMapping("api/response")
public class RedisResponseController {

    @PreventDuplication(frequency = 1000)
    @Operation(summary = "测试接口")
    @GetMapping("/get")
    public User get(User user) {
        return user;
    }

    @Caching(cacheable = {
            @Cacheable(cacheNames = "jd", key = "#user.id+':info'"),
            @Cacheable(cacheNames = "tb", key = "#user.id+':test'")
    })
    @Operation(summary = "测试接口")
    @PostMapping("/post")
    public User post(@RequestBody User user) {
        return user;
    }

    @RemoveCaching(remove = {
            @CacheRemove(cacheNames = "jd", key = "#user.id+':*'"),
            @CacheRemove(cacheNames = "tb", key = "#user.id+':*'")
    })
    @Operation(summary = "测试接口")
    @PutMapping("/put")
    public User put(@RequestBody User user) {
        return user;
    }

    @Operation(summary = "测试接口")
    @DeleteMapping("/delete")
    public User delete(@RequestBody User user) {
        return user;
    }

}
