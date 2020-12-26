package com.taozi.graph;

import java.util.*;

/**
 * 图的拓扑排序算法（宽度遍历）
 * 1）在图中找到所有入度为0的点输出
 * 2）把所有入度为0的点在图中删掉，继续找入度为0的点输出，周而复始
 * 3）图的所有点都被删除后，依次输出的顺序就是拓扑排序
 * <p>
 * 要求：有向图且其中没有环
 * 应用：事件安排、编译顺序
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/26 21:03
 */
public class Code03_TopologicalSort {

    /**
     * 图的拓扑排序
     *
     * @param graph 图
     * @return 排序结果
     */
    public List<Graph.Node> sort(Graph graph) {
        // 保存点与入度的映射
        Map<Graph.Node, Integer> inMap = new HashMap<>();
        // 入度为0的点才能进入队列
        Queue<Graph.Node> zeroInNode = new LinkedList<>();
        // 初始化inMap，并找到初始入度为0的点
        for (Graph.Node value : graph.nodes.values()) {
            inMap.put(value, value.in);
            if (value.in == 0) zeroInNode.add(value);
        }
        List<Graph.Node> res = new ArrayList<>();
        // 周而复始的找到入度为0的点
        while (!zeroInNode.isEmpty()) {
            Graph.Node cur = zeroInNode.poll();
            res.add(cur);
            // 遍历所有的直接邻居点
            for (Graph.Node next : cur.nexts) {
                // 将直接邻居点的入度减1，代表将当前点在图中删除
                inMap.put(next, inMap.get(next) - 1);
                // 如果邻居点的入度为0了，那么将它加入队列
                if (inMap.get(next) == 0) zeroInNode.add(next);
            }
        }
        return res;
    }
}
