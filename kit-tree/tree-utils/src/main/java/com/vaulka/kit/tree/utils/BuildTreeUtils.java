package com.vaulka.kit.tree.utils;


import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 构建树状结构工具
 *
 * @author Vaulka
 */
@AllArgsConstructor
public class BuildTreeUtils<T, F> {

    /**
     * 顶层节点 ID
     */
    private final Function<T, F> idFun;

    /**
     * 父节点 ID
     */
    private final Function<T, F> parentIdFun;

    /**
     * 子节点列表
     */
    private final BiConsumer<T, List<T>> childrenFun;

    /**
     * 构建树状结构
     *
     * @return 树状结构数据
     */
    public List<T> buildNode(List<T> data, F rootId) {
        return this.buildNode(data, rootId, null);
    }

    /**
     * 构建树状结构
     *
     * @param comparator 排序规则
     * @return 树状结构数据
     */
    public List<T> buildNode(List<T> data, F rootId, Comparator<? super T> comparator) {
        if (data == null || data.size() == 0) {
            return Collections.emptyList();
        }
        Map<F, List<T>> nodeGroup = data.stream()
                .collect(Collectors.groupingBy(parentIdFun));
        // 取出顶层节点
        List<T> results = Optional.ofNullable(nodeGroup.remove(rootId)).orElse(Collections.emptyList());
        // 排序
        Optional.ofNullable(comparator).ifPresent(results::sort);
        // 递归构建子节点
        this.buildChildNode(results, nodeGroup, comparator);
        return results;
    }

    /**
     * 构建子节点树状结构
     *
     * @param results    顶层树状结构数据
     * @param nodeGroup  树状结构结构
     * @param comparator 排序规则
     */
    private void buildChildNode(List<T> results, Map<F, List<T>> nodeGroup, Comparator<? super T> comparator) {
        for (T result : results) {
            // 获取子节点列表
            List<T> children = Optional.ofNullable(nodeGroup.remove(idFun.apply(result))).orElse(Collections.emptyList());
            childrenFun.accept(result, children);
            // 递归构建子孙节点树状结构
            this.buildChildNode(children, nodeGroup, comparator);
            // 排序
            Optional.ofNullable(comparator).ifPresent(children::sort);
        }
    }

}
