package com.vaulka.kit.validation.model;

import com.vaulka.kit.validation.enums.ClassType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Vaulka
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FieldUser extends Page {

    private final Byte by1 = Byte.valueOf("1");

    private byte by2 = (byte) 1;

    private Short sh1 = Short.valueOf("1");

    private short sh2 = (short) 1;

    private Integer in1 = Integer.valueOf("1");

    private int in2 = 1;

    private Long lo1 = Long.valueOf("1");

    private long lo2 = 1;

    private Float fl1 = Float.valueOf("1");

    private float fl2 = (float) 1;

    private Double do1 = Double.valueOf("1");

    private double do2 = 1;

    private BigInteger bi = new BigInteger("1");

    private BigDecimal bd = new BigDecimal("1");

    private Boolean bo1 = Boolean.valueOf("true");

    private boolean bo2 = true;

    private Character ch1 = '1';

    private char ch2 = 1;

    private String st = "1";

    private User[] array = new User[]{new User(), new User()};

    private List<Integer> list = List.of(1, 2);

    private Set<Integer> set = Set.of(1, 2);

    private Map<Integer, Integer> map = Map.of(1, 2);

    private Date date = new Date();

    private LocalDate localDate = LocalDate.now();

    private LocalTime localTime = LocalTime.now();

    private LocalDateTime localDateTime = LocalDateTime.now();

    private MonthDay monthDay = MonthDay.now();

    private Instant instant = Instant.now();

    private OffsetDateTime offsetDateTime = OffsetDateTime.now();

    private OffsetTime offsetTime = OffsetTime.now();

    private Year year = Year.now();

    private YearMonth yearMonth = YearMonth.now();

    private ZonedDateTime zonedDateTime = ZonedDateTime.now();

    private DayOfWeek dayOfWeek = DayOfWeek.FRIDAY;

    private Month month = Month.DECEMBER;

    private ClassType classType = ClassType.BOOLEAN;

    private Object nu = null;

    private User user = new User();

}
