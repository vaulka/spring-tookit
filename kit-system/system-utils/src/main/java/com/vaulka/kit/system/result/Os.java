package com.vaulka.kit.system.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * OS 信息
 *
 * @author Vaulka
 **/
@Data
@Accessors(chain = true)
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
