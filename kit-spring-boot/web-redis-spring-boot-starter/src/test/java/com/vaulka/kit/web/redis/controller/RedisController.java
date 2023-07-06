package com.vaulka.kit.web.redis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class RedisController {

    @Operation(summary = "测试接口")
    @GetMapping("/get")
    public User get(User user) {
        return user;
    }

    @Operation(summary = "测试接口")
    @PostMapping("/post")
    public User post(@RequestBody User user) {
        return user;
    }

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
