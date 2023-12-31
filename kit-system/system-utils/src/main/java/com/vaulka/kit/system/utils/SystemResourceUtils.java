package com.vaulka.kit.system.utils;

import com.vaulka.kit.system.model.Cpu;
import com.vaulka.kit.system.model.Disk;
import com.vaulka.kit.system.model.Jdk;
import com.vaulka.kit.system.model.Jvm;
import com.vaulka.kit.system.model.Mem;
import com.vaulka.kit.system.model.Os;
import com.vaulka.kit.system.model.SystemResource;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 系统资源工具
 *
 * @author Vaulka
 **/
public class SystemResourceUtils {

    /**
     * 获取系统资源信息
     *
     * @return 获取系统资源信息
     */
    public static SystemResource read() {
        Os os = SystemResourceUtils.readOs();
        Cpu cpu = SystemResourceUtils.readCpu();
        Mem mem = SystemResourceUtils.readMem();
        Jdk jdk = SystemResourceUtils.readJdk();
        Jvm jvm = SystemResourceUtils.readJvm();
        List<Disk> disks = SystemResourceUtils.readDisks();
        return SystemResource.builder()
                .os(os)
                .cpu(cpu)
                .mem(mem)
                .jdk(jdk)
                .jvm(jvm)
                .disks(disks)
                .build();
    }

    /**
     * 系统属性配置
     */
    private static final Properties PROPS = System.getProperties();

    /**
     * 获取 OS 信息
     *
     * @return 获取 OS 信息
     */
    public static Os readOs() {
        String name = PROPS.getProperty("os.name");
        String arch = PROPS.getProperty("os.arch");
        String hostName;
        String hostAddress;
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            hostName = inetAddress.getHostName();
            hostAddress = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            hostName = null;
            hostAddress = null;
        }
        return Os.builder()
                .name(name)
                .arch(arch)
                .hostName(hostName)
                .hostName(hostAddress)
                .build();
    }

    /**
     * 查询系统信息
     */
    private static final SystemInfo SYSTEM_INFO = new SystemInfo();

    /**
     * 获取 CPU 指标信息
     *
     * @return 获取 CPU 指标信息
     */
    public static Cpu readCpu() {
        CentralProcessor processor = SYSTEM_INFO.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ignored) {
        }
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softIrq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long system = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long ioWait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + system + idle + ioWait + irq + softIrq + steal;
        return Cpu.builder()
                .model(processor.getProcessorIdentifier().getName())
                .microArchitecture(processor.getProcessorIdentifier().getMicroarchitecture())
                .physicalProcessorCount(processor.getPhysicalProcessorCount())
                .logicalProcessorCount(processor.getLogicalProcessorCount())
                .systemUsage(SystemResourceUtils.formatUsage(system * 1.0 / totalCpu))
                .userUsage(SystemResourceUtils.formatUsage(user * 1.0 / totalCpu))
                .ioWaitUsage(SystemResourceUtils.formatUsage(ioWait * 1.0 / totalCpu))
                .totalUsage(SystemResourceUtils.formatUsage(1.0 - (idle * 1.0 / totalCpu)))
                .build();
    }

    /**
     * 获取 MEM 指标信息
     *
     * @return 获取 MEM 指标信息
     */
    public static Mem readMem() {
        GlobalMemory memory = SYSTEM_INFO.getHardware().getMemory();
        long totalByte = memory.getTotal();
        long availableByte = memory.getAvailable();
        String capacity = SystemResourceUtils.formatByte(totalByte);
        String usedCapacity = SystemResourceUtils.formatByte(totalByte - availableByte);
        String freeCapacity = SystemResourceUtils.formatByte(availableByte);
        return Mem.builder()
                .capacity(capacity)
                .usedCapacity(usedCapacity)
                .freeCapacity(freeCapacity)
                .usage(SystemResourceUtils.formatUsage((totalByte - availableByte) * 1.0 / totalByte))
                .build();
    }

    /**
     * 获取 JDK 信息
     *
     * @return 获取 JDK 信息
     */
    public static Jdk readJdk() {
        String name = ManagementFactory.getRuntimeMXBean().getVmName();
        String version = PROPS.getProperty("java.version");
        String home = PROPS.getProperty("java.home");
        String runHome = PROPS.getProperty("user.dir");
        return Jdk.builder()
                .name(name)
                .version(version)
                .home(home)
                .runHome(runHome)
                .build();
    }

    /**
     * 获取 JVM 指标信息
     *
     * @return 获取 JVM 指标信息
     */
    public static Jvm readJvm() {
        Runtime runtime = Runtime.getRuntime();
        long jvmTotalMemoryByte = runtime.totalMemory();
        long jvmFreeMemoryByte = runtime.freeMemory();
        String capacity = SystemResourceUtils.formatByte(jvmTotalMemoryByte);
        String usedCapacity = SystemResourceUtils.formatByte(jvmTotalMemoryByte - jvmFreeMemoryByte);
        String freeCapacity = SystemResourceUtils.formatByte(jvmFreeMemoryByte);
        return Jvm.builder()
                .capacity(capacity)
                .usedCapacity(usedCapacity)
                .freeCapacity(freeCapacity)
                .usage(SystemResourceUtils.formatUsage((jvmTotalMemoryByte - jvmFreeMemoryByte) * 1.0 / jvmTotalMemoryByte))
                .build();
    }

    /**
     * 获取 Disk 指标信息
     *
     * @return 获取 Disk 指标信息
     */
    private static List<Disk> readDisks() {
        FileSystem fileSystem = SYSTEM_INFO.getOperatingSystem().getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();
        if (fileStores.size() == 0) {
            return Collections.emptyList();
        }
        List<Disk> disks = new ArrayList<>(fileStores.size());
        for (OSFileStore fileStore : fileStores) {
            String mount = fileStore.getMount();
            String type = fileStore.getType();
            String name = fileStore.getName();
            long free = fileStore.getUsableSpace();
            long total = fileStore.getTotalSpace();
            long used = total - free;
            String capacity = SystemResourceUtils.formatByte(total);
            String usedCapacity = SystemResourceUtils.formatByte(used);
            String freeCapacity = SystemResourceUtils.formatByte(free);
            Disk disk = Disk.builder()
                    .mount(mount)
                    .type(type)
                    .name(name)
                    .capacity(capacity)
                    .usedCapacity(usedCapacity)
                    .freeCapacity(freeCapacity)
                    .usage(SystemResourceUtils.formatUsage(used * 1.0 / total))
                    .build();
            disks.add(disk);
        }
        return disks;
    }

    /**
     * 格式化使用率
     *
     * @param number 数值
     * @return 格式化使用率
     */
    private static String formatUsage(Double number) {
        if (number.equals(Double.NaN)) {
            return "";
        }
        return new DecimalFormat("#.##%").format(number);
    }

    /**
     * 字节换算单位
     */
    private static final double FORMAT = 1024;

    /**
     * 格式化字节
     *
     * @param byteNumber 字节
     * @return 格式化字节
     */
    private static String formatByte(long byteNumber) {
        double kbNumber = byteNumber / FORMAT;
        if (kbNumber < FORMAT) {
            return new DecimalFormat("#.##KB").format(kbNumber);
        }
        double mbNumber = kbNumber / FORMAT;
        if (mbNumber < FORMAT) {
            return new DecimalFormat("#.##MB").format(mbNumber);
        }
        double gbNumber = mbNumber / FORMAT;
        if (gbNumber < FORMAT) {
            return new DecimalFormat("#.##GB").format(gbNumber);
        }
        double tbNumber = gbNumber / FORMAT;
        return new DecimalFormat("#.##TB").format(tbNumber);
    }


}
