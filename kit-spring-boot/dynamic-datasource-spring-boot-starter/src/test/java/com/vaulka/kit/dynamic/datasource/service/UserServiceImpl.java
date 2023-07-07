package com.vaulka.kit.dynamic.datasource.service;

import com.vaulka.kit.dynamic.datasource.annotation.DynamicDatasource;
import com.vaulka.kit.dynamic.datasource.controller.User;
import com.vaulka.kit.dynamic.datasource.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vaulka
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserMapper userMapper;

    @DynamicDatasource
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<User> xxx() {
        List<User> users = new ArrayList<>();
        Iterable<User> all = userMapper.findAll();
        for (User user : all) {
            users.add(user);
        }
        return users;
    }

    @DynamicDatasource("master")
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<User> yyy() {
        List<User> users = new ArrayList<>();
        Iterable<User> all = userMapper.findAll();
        for (User user : all) {
            users.add(user);
        }
        return users;

    }

    @DynamicDatasource("master-1")
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<User> zzz() {
        List<User> users = new ArrayList<>();
        Iterable<User> all = userMapper.findAll();
        for (User user : all) {
            users.add(user);
        }
        return users;
    }

    @DynamicDatasource("master-2")
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<User> xyz() {
        List<User> users = new ArrayList<>();
        Iterable<User> all = userMapper.findAll();
        for (User user : all) {
            users.add(user);
        }
        return users;
    }

}
