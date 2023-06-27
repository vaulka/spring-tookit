package com.vaulka.kit.system.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OS 信息
 *
 * @author Vaulka
 **/
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Os {

    /**
     * 操作系统名称
     */
    private String name;

    /**
     * 操作系统架构
     */
    private String arch;

    /**
     * 主机名称
     */
    private String hostName;

    /**
     * 主机 IP 地址
     */
    private String hostAddress;

}
