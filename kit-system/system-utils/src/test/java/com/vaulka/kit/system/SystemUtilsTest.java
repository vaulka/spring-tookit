package com.vaulka.kit.system;

import com.vaulka.kit.system.model.SystemResource;
import com.vaulka.kit.system.utils.SystemResourceUtils;
import org.junit.jupiter.api.Test;

/**
 * @author Vaulka
 */
public class SystemUtilsTest {

    /**
     * 获取系统资源信息
     */
    @Test
    public void read() {
        SystemResource systemResource = SystemResourceUtils.read();
        System.out.println(systemResource.toString());
    }

}
