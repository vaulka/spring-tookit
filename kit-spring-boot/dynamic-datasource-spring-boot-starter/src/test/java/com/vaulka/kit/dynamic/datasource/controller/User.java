package com.vaulka.kit.dynamic.datasource.controller;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author Vaulka
 **/
@Data
public class User {

    @Id
    private Long id;

    private String message;

}
