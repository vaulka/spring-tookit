package com.vaulka.kit.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 系统资源信息
 *
 * @author Vaulka
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemResource implements Serializable {

    @Serial
    private static final long serialVersionUID = -1886364707847806653L;

    /**
     * OS 信息
     */
    private Os os;

    /**
     * CPU 指标信息
     */
    private Cpu cpu;

    /**
     * MEM 指标信息
     */
    private Mem mem;

    /**
     * JDK 信息
     */
    private Jdk jdk;

    /**
     * JVM 指标信息
     */
    private Jvm jvm;

    /**
     * Disk 信息
     */
    private List<Disk> disks;

}
