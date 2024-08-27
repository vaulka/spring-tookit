package com.vaulka.kit.tree.utils;


import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 索引树状结构工具
 *
 * @author Vaulka
 */
@AllArgsConstructor
public class IndexTreeUtils<T, F> {

    /**
     * 顶层节点 ID
     */
    private final Function<T, F> idFun;

    /**
     * 子节点列表
     */
    private final Function<T, List<T>> childrenFun;

    /**
     * 根据索引ID查找节点及其路径
     *
     * @param results  数据列表
     * @param targetId 索引ID
     * @return 路径
     */
    public List<T> findPath(List<T> results, F targetId) {
        List<T> path = new ArrayList<>();
        for (T result : results) {
            boolean isExist = findPathHelper(result, targetId, path);
            if (isExist) {
                return path;
            }
        }
        return path;
    }

    /**
     * 判断是否索引到对应节点
     *
     * @param node     节点
     * @param targetId 索引ID
     * @param path     路径列表
     * @return 是否索引到对应节点
     */
    private boolean findPathHelper(T node, F targetId, List<T> path) {
        // 将当前节点添加到路径中
        path.add(node);
        // 如果存在则跳出循环
        if (targetId.equals(idFun.apply(node))) {
            return true;
        }
        // 如果不存在则迭代子节点
        List<T> children = childrenFun.apply(node);
        if (children != null) {
            for (T child : childrenFun.apply(node)) {
                if (findPathHelper(child, targetId, path)) {
                    // 如果在子树中找到了目标节点则跳出循环
                    return true;
                }
            }
        }
        // 如果当前节点不是目标节点且在它的子树中也没有找到目标节点，则从路径中移除它
        path.remove(path.size() - 1);
        return false;
    }

}
