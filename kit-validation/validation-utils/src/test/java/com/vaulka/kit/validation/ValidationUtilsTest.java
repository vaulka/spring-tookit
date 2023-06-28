package com.vaulka.kit.validation;


import com.pongsky.kit.validation.annotation.Property;
import com.pongsky.kit.validation.annotation.validator.MinNumAndMaxNum;
import com.pongsky.kit.validation.enums.ClassType;
import com.pongsky.kit.validation.utils.ValidationUtils;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.junit.jupiter.api.Test;

/**
 * @author Vaulka
 */
public class ValidationUtilsTest {

    @MinNumAndMaxNum(
            minNumFieldName = "minIntegral", minNumName = "积分最小值",
            maxNumFieldName = "maxIntegral", maxNumName = "积分最大值"
    )
    public static class User {

        @Property("姓名")
        private String name;
        @Property("年龄")
        @Null
        @Range(min = 1, max = 2)
        private Integer age;
        @Property("描述")
        @Length(min = 100)
        private String desc;

        /**
         * 积分最小值
         */
        @Property("积分最小值")
        @NotNull
        @Range
        private Integer minIntegral;

        /**
         * 积分最大值
         */
        @NotNull
        @Range
        private Integer maxIntegral;

        public Integer getMinIntegral() {
            return minIntegral;
        }

        public void setMinIntegral(Integer minIntegral) {
            this.minIntegral = minIntegral;
        }

        public Integer getMaxIntegral() {
            return maxIntegral;
        }

        public void setMaxIntegral(Integer maxIntegral) {
            this.maxIntegral = maxIntegral;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    @Test
    public void test() {
        User user = new User();
        user.setName("张杰");
        user.setAge(18);
        user.setDesc("张杰（Jason Zhang），1982年12月20日出生于四川省成都市，中国流行男歌手。2004年参加歌唱类选秀《我型我秀》，获得全国总冠军并出道。");
        user.setMinIntegral(30);
        user.setMaxIntegral(20);
        String message = ValidationUtils.validation(user);
        System.out.println(message);

        ClassType classType = ClassType.parserType(user);
        System.out.println(classType);
    }

}
