package com.vaulka.kit.tree.utils;


import lombok.AllArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 过滤树状结构工具
 *
 * @author Vaulka
 */
@AllArgsConstructor
public class FilterTreeUtils<T> {

    /**
     * 子节点列表
     */
    private final Function<T, List<T>> childrenFun;

    /**
     * 删除无效数据树状节点
     *
     * @param data      树状结构数据
     * @param predicate 节点保留规则
     * @return 节点是否删除
     */
    public Boolean removeInvalidNode(List<T> data, Predicate<T> predicate) {
        boolean isReserve = false;
        Iterator<T> iterator = data.iterator();
        while (iterator.hasNext()) {
            T treeNode = iterator.next();
            if (this.removeInvalidNode(childrenFun.apply(treeNode), predicate) || predicate.test(treeNode)) {
                isReserve = true;
                continue;
            }
            iterator.remove();
        }
        return isReserve;
    }

}
