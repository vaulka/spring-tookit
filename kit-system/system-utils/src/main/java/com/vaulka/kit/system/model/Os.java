package com.vaulka.kit.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * OS 信息
 *
 * @author Vaulka
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Os implements Serializable {

    @Serial
    private static final long serialVersionUID = -3968626480151979558L;

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
