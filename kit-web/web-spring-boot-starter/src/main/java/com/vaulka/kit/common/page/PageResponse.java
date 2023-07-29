package com.vaulka.kit.common.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页响应数据
 *
 * @author Vaulka
 */
@Data
@NoArgsConstructor
public class PageResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 544302473094363807L;

    public PageResponse(List<T> content, PageQuery pageQuery, long totalElements) {
        this.content = content;
        this.totalElements = totalElements;
        this.pageNumber = pageQuery.getPageNumber();
        this.pageSize = pageQuery.getPageSize();
        this.totalPages = this.totalElements % this.pageSize == 0
                ? this.totalElements / this.pageSize
                : this.totalElements / this.pageSize + 1;
    }

    /**
     * 数据集合
     */
    @Schema(description = "数据集合")
    private List<T> content;

    /**
     * 总页数
     */
    @Schema(description = "总页数")
    private Long totalPages;

    /**
     * 总数量
     */
    @Schema(description = "总数量")
    private Long totalElements;

    /**
     * 当前页码
     */
    @Schema(description = "当前页码")
    private Integer pageNumber;

    /**
     * 页数量
     */
    @Schema(description = "页数量")
    private Integer pageSize;

}
