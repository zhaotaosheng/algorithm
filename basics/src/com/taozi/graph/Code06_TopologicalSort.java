package com.taozi.graph;

import java.util.*;

/**
 * 根据提供的点结构DirectedGraphNode生成拓扑排序（宽度遍历）
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/27 12:39
 */
public class Code06_TopologicalSort {

    // 已知图中的点描述
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }


    /**
     * 根据点集合来获取拓扑排序
     *
     * @param graph 图
     * @return 拓扑顺序
     */
    public List<DirectedGraphNode> sort(List<DirectedGraphNode> graph) {
        // 点与点入度的映射表
        Map<DirectedGraphNode, Integer> inDegreeMap = new HashMap<>();
        // 初始化inDegreeMap，初始值都为0
        for (DirectedGraphNode directedGraphNode : graph) {
            inDegreeMap.put(directedGraphNode, 0);
        }
        // 遍历所有的点，将各个点入度维护正确
        for (DirectedGraphNode directedGraphNode : graph) {
            for (DirectedGraphNode neighbor : directedGraphNode.neighbors) {
                inDegreeMap.put(neighbor, inDegreeMap.get(neighbor) + 1);
            }
        }
        Queue<DirectedGraphNode> zeroInDegreeQueue = new LinkedList<>();
        // 找到最初的入度为0的点
        for (Map.Entry<DirectedGraphNode, Integer> node : inDegreeMap.entrySet()) {
            if (node.getValue() == 0) zeroInDegreeQueue.add(node.getKey());
        }
        List<DirectedGraphNode> res = new ArrayList<>();
        while (!zeroInDegreeQueue.isEmpty()) {
            // 出队一个入度为0的点就加入到结果集中
            DirectedGraphNode node = zeroInDegreeQueue.poll();
            res.add(node);
            // 遍历该点的直接邻居，将邻居的入度减1，如果减到0就加入队列
            for (DirectedGraphNode neighbor : node.neighbors) {
                inDegreeMap.put(neighbor, inDegreeMap.get(neighbor) - 1);
                if (inDegreeMap.get(neighbor) == 0) zeroInDegreeQueue.add(neighbor);
            }
        }
        return res;
    }
}
