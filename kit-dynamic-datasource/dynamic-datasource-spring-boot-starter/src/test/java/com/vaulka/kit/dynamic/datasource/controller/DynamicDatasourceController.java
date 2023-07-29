package com.vaulka.kit.dynamic.datasource.controller;

import com.vaulka.kit.dynamic.datasource.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Vaulka
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/response")
public class DynamicDatasourceController {

    private final UserServiceImpl service;

    @GetMapping("/xxx")
    public List<User> xxx() {
        return service.xxx();
    }

    @GetMapping("/yyy")
    public List<User> yyy() {
        return service.yyy();
    }

    @GetMapping("/zzz")
    public List<User> zzz() {
        return service.zzz();
    }

    @GetMapping("/xyz")
    public List<User> xyz() {
        return service.xyz();
    }

}
