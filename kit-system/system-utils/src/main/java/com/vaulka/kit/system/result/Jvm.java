package com.vaulka.kit.system.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * JVM 指标信息
 *
 * @author Vaulka
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
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
