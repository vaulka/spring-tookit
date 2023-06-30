package com.vaulka.kit.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JVM 指标信息
 *
 * @author Vaulka
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Jvm {

    /**
     * JVM 内存容量
     */
    private String capacity;

    /**
     * JVM 已使用容量
     */
    private String usedCapacity;

    /**
     * JVM 空闲容量
     */
    private String freeCapacity;

    /**
     * JVM 使用率
     */
    private String usage;

}
