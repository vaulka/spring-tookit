package com.vaulka.kit.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Disk 指标信息
 *
 * @author Vaulka
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Disk implements Serializable {

    @Serial
    private static final long serialVersionUID = -4541412356515370394L;

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
