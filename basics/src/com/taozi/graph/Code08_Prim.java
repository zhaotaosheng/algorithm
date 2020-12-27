package com.taozi.graph;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 最小生成树算法之Prim
 * 1）可以从任意节点出发来寻找最小生成树
 * 2）某个点加入到被选取的点中后，解锁这个点出发的所有新的边
 * 3）在所有解锁的边中选最小的边，然后看看这个边会不会形成环
 * 4）如果会，不要当前边，继续考察剩下解锁的边中最小的边，重复3）
 * 5）如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2）
 * 6）当所有点都被选取，最小生成树就得到了
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/27 13:57
 */
public class Code08_Prim {

    /**
     * P算法最小生成树
     *
     * @param graph 给定的图
     * @return 边组成的集合
     */
    public Set<Graph.Edge> primMST(Graph graph) {
        // 解锁出来的边放到小根堆中
        PriorityQueue<Graph.Edge> smallEdge =
                new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));
        // 保存解锁出来的点
        Set<Graph.Node> completeNode = new HashSet<>();
        // 挑选出来的边放到结果集中
        Set<Graph.Edge> res = new HashSet<>();
        for (Graph.Node value : graph.nodes.values()) {
            // 任意找到一个点开始处理
            if (!completeNode.contains(value)) {
                // 将该点加入解锁点集合中
                completeNode.add(value);
                // 将该点的边加入解锁边的小根堆中
                smallEdge.addAll(value.edges);
                while (!smallEdge.isEmpty()) {
                    // 依次弹出当前权重最小的边，贪心
                    Graph.Edge edge = smallEdge.poll();
                    // to点可能是一个处理过点也可能是没处理过，如果处理过的话该边不应加入到结果集中
                    Graph.Node to = edge.to;
                    // 如果将要解锁的点不在解锁点集合中，则解锁该点和该点的边
                    if (!completeNode.contains(to)) {
                        completeNode.add(to);
                        res.add(edge);
                        smallEdge.addAll(to.edges);
                    }
                }
            }
        }
        return res;
    }
}
