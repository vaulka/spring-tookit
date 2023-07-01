package com.vaulka.kit.redis.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Vaulka
 */
@Data
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = -5369434086315445703L;

    private Integer id;

    private String bankCard = "123";

    private Integer age = 456;

    private BigDecimal money = new BigDecimal("123.456");

    private CustomUser user = new CustomUser();

    private List<CustomUser> users = List.of(user, user);

    private Date date = new Date();

}
