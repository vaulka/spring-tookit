package com.vaulka.kit.validation;


import com.vaulka.kit.validation.utils.ValidationUtils;
import com.vaulka.kit.validation.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/**
 * @author Vaulka
 */
public class ValidationUtilsTest {

    /**
     * 测试校验器
     */
    @Test
    public void validation() {
        User user = User.builder()
                .name("张杰")
                .age(18)
                .desc("张杰（Jason Zhang），1982年12月20日出生于四川省成都市，中国流行男歌手。2004年参加歌唱类选秀《我型我秀》，获得全国总冠军并出道。")
                .minIntegral(30)
                .maxIntegral(20)
                .startTime(LocalDate.of(2023, 6, 13))
                .endTime(LocalDate.of(2023, 6, 12))
                .build();
        String message = ValidationUtils.validation(user);
//        assert message.equals("参数校验失败，一共有 5 处错误，详情如下： 开始时间不能大于结束时间; 描述长度需要在100和2147483647之间; 年龄必须为null; 积分最小值不能大于积分最大值; 年龄需要在1和2之间;");

        user.setMinIntegral(10);
        user.setMaxIntegral(30);
        user.setStartTime(LocalDate.of(2023, 6, 10));
        user.setEndTime(LocalDate.of(2023, 6, 12));
        message = ValidationUtils.validation(user);
//        assert message.equals("参数校验失败，一共有 3 处错误，详情如下： 描述长度需要在100和2147483647之间; 年龄必须为null; 年龄需要在1和2之间;");
    }

}
