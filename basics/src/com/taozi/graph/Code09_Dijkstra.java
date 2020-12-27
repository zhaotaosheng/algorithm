package com.taozi.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Dijkstra算法
 * 1）Dijkstra算法必须指定一个源点
 * 2）生成一个源点到各个点的最小距离表，一开始只有一条记录，即原点到自己的最小距离为0，源点到其他所有点的最小距离都为正无穷大
 * 3）从距离表中拿出没拿过记录里的最小记录，通过这个点发出的边，更新源点到各个点的最小距离表，不断重复这一步
 * 4）源点到所有的点记录如果都被拿过一遍，过程停止，最小距离表得到了
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/27 14:16
 */
public class Code09_Dijkstra {

    public Map<Graph.Node, Integer> dijkstra(Graph.Node from) {
        // 点与到该点距离的映射冠以
        Map<Graph.Node, Integer> distanceMap = new HashMap<>();
        // 初始点距离为0
        distanceMap.put(from, 0);
        // 已经处理完的节点，进入这个集合后该点的距离不需要在考虑了
        Set<Graph.Node> completeNodes = new HashSet<>();
        // 当前距离最小的点
        Graph.Node minNode = from;
        while (minNode != null) {
            // 初始点到minNode的距离，minNode其实是一个跳转点
            Integer distance = distanceMap.get(minNode);
            // 去更新minNode所有直接邻居的距离
            for (Graph.Edge edge : minNode.edges) {
                Graph.Node to = edge.to;
                // 如果邻居没有在处理过的节点集合中，则初始化距离为minNode的距离+边权重
                if (!completeNodes.contains(to)) {
                    distanceMap.put(to, distance + edge.weight);
                }
                // 如果邻居在处理过的节点集合中，则考虑是原先的距离短还是现在minNode的距离+边权重短
                else {
                    distanceMap.put(to, Math.min(distanceMap.get(to), distance + edge.weight));
                }
            }
            // 将minNode锁定
            completeNodes.add(minNode);
            // 找到剩余未完成的继续，如果都处理完了，minNode = null循环结束
            minNode = null;
            int minDistance = Integer.MAX_VALUE;
            for (Map.Entry<Graph.Node, Integer> entry : distanceMap.entrySet()) {
                // 如果该点未处理且该点的距离较小则更新minNode和minDistance来找到最小的minNode
                if (!completeNodes.contains(entry.getKey()) && entry.getValue() < minDistance) {
                    minNode = entry.getKey();
                    minDistance = entry.getValue();
                }
            }
        }
        return distanceMap;
    }
}
