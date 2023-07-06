package com.vaulka.kit.validation.model;

import com.vaulka.kit.validation.annotation.Property;
import com.vaulka.kit.validation.annotation.validator.MinNumAndMaxNum;
import com.vaulka.kit.validation.annotation.validator.StartTimeAndEndTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

/**
 * @author Vaulka
 */
@MinNumAndMaxNum(
        minNumFieldName = "minIntegral", minNumName = "积分最小值",
        maxNumFieldName = "maxIntegral", maxNumName = "积分最大值"
)
@StartTimeAndEndTime
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * 姓名
     */
    @Property("姓名")
    private String name;

    /**
     * 年龄
     */
    @Property("年龄")
    @Null
    @Range(min = 1, max = 2)
    private Integer age;

    /**
     * 描述
     */
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

    /**
     * 开始时间
     */
    private LocalDate startTime;

    /**
     * 结束时间
     */
    private LocalDate endTime;

}
