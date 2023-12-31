package com.vaulka.kit.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * CPU 指标信息
 *
 * @author Vaulka
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cpu implements Serializable {

    @Serial
    private static final long serialVersionUID = 2818428853998678247L;

    /**
     * CPU 名称
     */
    private String model;

    /**
     * CPU 架构
     */
    private String microArchitecture;

    /**
     * CPU 内核总数
     */
    private Integer physicalProcessorCount;

    /**
     * CPU 逻辑处理器总数
     */
    private Integer logicalProcessorCount;

    /**
     * CPU 系统使用率
     */
    private String systemUsage;

    /**
     * CPU 用户使用率
     */
    private String userUsage;

    /**
     * CPU 等待率
     */
    private String ioWaitUsage;

    /**
     * CPU 总使用率
     */
    private String totalUsage;

}
