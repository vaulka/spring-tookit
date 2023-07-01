package com.vaulka.kit.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 响应数据体
 *
 * @author Vaulka
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class GlobalResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -8691252594451970912L;

    /**
     * 成功 code
     */
    public static final int SUCCESS_CODE = 0;

    /**
     * 成功 message
     */
    public static final String SUCCESS_MESSAGE = "成功";

    /**
     * 接口响应结果标识码
     * <p>
     * 有数据的情况：成功、失败
     */
    @Schema(description = "接口响应结果标识码")
    private Integer code;

    /**
     * 接口响应结果信息
     * <p>
     * 有数据的情况：成功、失败
     */
    @Schema(description = "接口响应结果信息")
    private String message;

    /**
     * 接口响应数据体
     * <p>
     * 有数据的情况：成功
     */
    @Schema(description = "接口响应数据体")
    private T data;

}
