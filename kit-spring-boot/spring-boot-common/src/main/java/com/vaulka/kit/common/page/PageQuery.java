package com.vaulka.kit.common.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.io.Serializable;

/**
 * 分页请求参数
 *
 * @author Vaulka
 */
@Data
@NoArgsConstructor
public class PageQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 7054779301680019390L;

    public PageQuery(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    /**
     * 当前页号
     */
    @Schema(description = "当前页号 最小 1")
    @Range(min = 1)
    private int pageNumber = 1;

    /**
     * 一页数量
     */
    @Schema(description = "一页数量 最小 1 最大 100")
    @Range(min = 1, max = 100)
    private int pageSize = 20;

    /**
     * 偏移量
     *
     * @return 偏移量
     */
    @Schema(description = "偏移量", hidden = true)
    public Long getOffset() {
        return (long) (this.getPageNumber() - 1) * this.getPageSize();
    }

}
