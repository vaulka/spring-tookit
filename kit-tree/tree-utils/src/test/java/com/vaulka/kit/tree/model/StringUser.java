package com.vaulka.kit.tree.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author Vaulka
 */
@Data
public class StringUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 6575309502023938960L;

    public StringUser(String id, String parentId, String name, Integer sex) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.sex = sex;
    }

    /**
     * ID
     */
    private String id;

    /**
     * 父节点 ID
     */
    private String parentId;

    /**
     * 子节点列表
     */
    private List<StringUser> children;

    /**
     * 节点ID列表
     */
    private List<String> rootIds;

    /**
     * 名称
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

}
