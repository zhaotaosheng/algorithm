package com.taozi.graph;

import java.util.*;

/**
 * 图：由点的集合与的集合边组成的数据结构
 * 虽然存在有向图和无向图的概念，但实际上都可以用有向图来表达
 * 边上可能带有权值
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/26 19:37
 */
public class Graph {
    // 维护点值与点的对应关系的集合
    public Map<Integer, Node> nodes;
    // 线段的集合
    public Set<Edge> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }

    // 图中的一个点结构描述
    public static class Node {
        // 当前点的值
        public int value;
        // 当前点的入度，既有多少点指向该点
        public int in;
        // 当前点的出度，既该点指向多少下一个点
        public int out;
        // 当前点指向下一个点的集合
        public List<Node> nexts;
        // 当前点指向下一个点的边
        public List<Edge> edges;

        public Node(int value) {
            this.value = value;
            this.in = 0;
            this.out = 0;
            this.nexts = new ArrayList<>();
            this.edges = new ArrayList<>();
        }
    }

    // 图中的一个边结构描述
    public static class Edge {
        // 边的权重
        public int weight;
        // 线起始于哪个点
        public Node from;
        // 线指向于哪个点
        public Node to;

        public Edge(int weight, Node from, Node to) {
            this.weight = weight;
            this.from = from;
            this.to = to;
        }
    }

    /**
     * 通过所有边的信息来生成图
     *
     * @param matrix 所有的边信息，是N*3矩阵，信息是[权重，出发点，终止点]
     * @return 图
     */
    public static Graph generateGraph(int[][] matrix) {
        Graph graph = new Graph();
        for (int[] edgeInfo : matrix) {
            int weight = edgeInfo[0];
            int from = edgeInfo[0];
            int to = edgeInfo[0];
            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }
            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            Edge edge = new Edge(weight, fromNode, toNode);
            toNode.in++;
            fromNode.out++;
            fromNode.nexts.add(toNode);
            fromNode.edges.add(edge);
            graph.edges.add(edge);
        }
        return graph;
    }
}
