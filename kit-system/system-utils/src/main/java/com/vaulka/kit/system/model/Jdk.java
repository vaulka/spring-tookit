package com.vaulka.kit.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * JDK 信息
 *
 * @author Vaulka
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Jdk implements Serializable {

    @Serial
    private static final long serialVersionUID = -4523758047688886431L;

    /**
     * JDK 名称
     */
    private String name;

    /**
     * JDK 版本
     */
    private String version;

    /**
     * JDK 路径
     */
    private String home;

    /**
     * 项目启动路径
     */
    private String runHome;

}
