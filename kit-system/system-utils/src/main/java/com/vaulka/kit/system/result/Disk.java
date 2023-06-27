package com.vaulka.kit.system.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Disk 指标信息
 *
 * @author Vaulka
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Disk {

    /**
     * Disk 挂载盘符
     */
    private String mount;

    /**
     * Disk 类型
     */
    private String type;

    /**
     * Disk 名称
     */
    private String name;

    /**
     * Disk 容量
     */
    private String capacity;

    /**
     * Disk 已使用容量
     */
    private String usedCapacity;

    /**
     * Disk 空闲容量
     */
    private String freeCapacity;

    /**
     * Disk 使用率
     */
    private String usage;

}
