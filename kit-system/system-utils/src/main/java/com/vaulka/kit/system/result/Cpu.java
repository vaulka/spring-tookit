package com.vaulka.kit.system.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * CPU 指标信息
 *
 * @author Vaulka
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Cpu {

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
