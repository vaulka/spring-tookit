package com.vaulka.kit.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * MEM 指标信息
 *
 * @author Vaulka
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mem implements Serializable {

    @Serial
    private static final long serialVersionUID = -2243071358066889765L;

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
