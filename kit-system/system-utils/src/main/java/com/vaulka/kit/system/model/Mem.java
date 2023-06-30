package com.vaulka.kit.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MEM 指标信息
 *
 * @author Vaulka
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mem {

    /**
     * 主机 容量
     */
    private String capacity;

    /**
     * 主机 已使用容量
     */
    private String usedCapacity;

    /**
     * 主机 空闲容量
     */
    private String freeCapacity;

    /**
     * 主机 使用率
     */
    private String usage;

}
