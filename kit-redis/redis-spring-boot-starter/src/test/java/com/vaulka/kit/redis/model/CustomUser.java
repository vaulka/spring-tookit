package com.vaulka.kit.redis.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Vaulka
 */
@Data
public class CustomUser implements Serializable {

    @Serial
    private static final long serialVersionUID = -8004047317211078413L;

    private Integer id;

    private String bankCard = "123";

    private Integer age = 456;

    private BigDecimal money = new BigDecimal("123.456");

    private Map<String, Long> map = Map.of("1", 2L);

}
