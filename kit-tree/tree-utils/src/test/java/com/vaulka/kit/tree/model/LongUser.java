package com.vaulka.kit.tree.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author Vaulka
 */
@Data
public class LongUser implements Serializable {

    @Serial
    private static final long serialVersionUID = -8345082276480338135L;

    public LongUser(Long id, Long parentId, String name, Integer sex) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.sex = sex;
    }

    /**
     * ID
     */
    private Long id;

    /**
     * 父节点 ID
     */
    private Long parentId;

    /**
     * 子节点列表
     */
    private List<LongUser> children;

    /**
     * 节点ID列表
     */
    private List<Long> rootIds;

    /**
     * 名称
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

}
