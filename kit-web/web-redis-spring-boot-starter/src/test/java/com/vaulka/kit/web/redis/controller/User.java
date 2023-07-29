package com.vaulka.kit.web.redis.controller;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author Vaulka
 **/
@Data
public class User {

    private Long id;

    private Date date;

    private LocalDate localDate;

    private LocalTime localTime;

    private LocalDateTime localDateTime;

    private String message;

}
