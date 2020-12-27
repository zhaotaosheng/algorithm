package com.taozi.graph;

import java.util.*;

/**
 * 最小生成树算法之Kruskal
 * 1）总是从权值最小的边开始考虑，依次考察权值依次变大的边
 * 2）当前的边要么进入最小生成树的集合，要么丢弃
 * 3）如果当前的边进入最小生成树的集合中不会形成环，就要当前边
 * 4）如果当前的边进入最小生成树的集合中会形成环，就不要当前边
 * 5）考察完所有边之后，最小生成树的集合也得到了
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/27 12:51
 */
public class Code07_Kruskal {

    /**
     * K算法最小生成树
     *
     * @param graph 给定的图
     * @return 边组成的集合
     */
    public Set<Graph.Edge> kruskalMST(Graph graph) {
        UnionFind uf = new UnionFind(graph);
        // 按照边的权重生成小根堆
        PriorityQueue<Graph.Edge> smallEdge =
                new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));
        smallEdge.addAll(graph.edges);
        Set<Graph.Edge> res = new HashSet<>();
        while (!smallEdge.isEmpty()) {
            // 我每次只取权重最小的边，贪心
            Graph.Edge edge = smallEdge.poll();
            // 如果边的两端不在一个集合里，那么就将这个边加入结果集中并合并两端
            // 如果边的两端在一个集合里，说明这两端点都考察过了，加上这个边的话就会生成环
            if (!uf.isSame(edge.from, edge.to)) {
                uf.union(edge.from, edge.to);
                res.add(edge);
            }
        }
        return res;
    }

    // 哈希表实现的并查集
    private static class UnionFind {
        public Map<Graph.Node, Graph.Node> parents;
        public Map<Graph.Node, Integer> sizes;
        public Stack<Graph.Node> help;

        // 用一个图中的所有点来初始化并查集
        public UnionFind(Graph graph) {
            parents = new HashMap<>();
            sizes = new HashMap<>();
            help = new Stack<>();
            Map<Integer, Graph.Node> nodes = graph.nodes;
            for (Graph.Node node : nodes.values()) {
                parents.put(node, node);
                sizes.put(node, 1);
            }
        }

        // 判断两个点是否属于一个并查集
        public boolean isSame(Graph.Node from, Graph.Node to) {
            return findRoot(from) == findRoot(to);
        }

        // 通过给定的点找到所在集合代表点
        private Graph.Node findRoot(Graph.Node node) {
            while (node != parents.get(node)) {
                node = parents.get(node);
                help.push(node);
            }
            while (!help.isEmpty()) {
                parents.put(help.pop(), node);
            }
            return node;
        }

        // 将两个点在的集合合并
        public void union(Graph.Node a, Graph.Node b) {
            if (a == null || b == null) return;
            Graph.Node aRoot = findRoot(a);
            Graph.Node bRoot = findRoot(b);
            if (aRoot != bRoot) {
                if (sizes.get(aRoot) > sizes.get(bRoot)) {
                    sizes.put(aRoot, sizes.get(aRoot) + sizes.get(bRoot));
                    sizes.put(bRoot, 0);
                    parents.put(bRoot, aRoot);
                } else {
                    sizes.put(bRoot, sizes.get(aRoot) + sizes.get(bRoot));
                    sizes.remove(aRoot);
                    parents.put(aRoot, bRoot);
                }
            }
        }
    }
}
