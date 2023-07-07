package com.vaulka.kit.dynamic.datasource.mapper;

import com.vaulka.kit.dynamic.datasource.controller.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vaulka
 */
@Repository
public interface UserMapper extends CrudRepository<User, Long> {
}
