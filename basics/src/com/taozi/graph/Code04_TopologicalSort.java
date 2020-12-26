package com.taozi.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据提供的点结构DirectedGraphNode生成拓扑排序（深度遍历）
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/26 21:56
 */
public class Code04_TopologicalSort {

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
        Map<DirectedGraphNode, Record> table = new HashMap<>();
        // 将所有点都生成点次存到缓存表里
        for (DirectedGraphNode directedGraphNode : graph) {
            getRecord(directedGraphNode, table);
        }
        List<Record> records = new ArrayList<>(table.values());
        // 将中间对象按照点次从大到小排序，如果点次大的则拓扑序小
        records.sort((r1, r2) -> r2.times - r1.times);
        List<DirectedGraphNode> res = new ArrayList<>();
        for (Record record : records) {
            res.add(record.node);
        }
        return res;
    }

    /**
     * 获取当前点的总点次中间对象
     *
     * @param node  当前点
     * @param table 缓存表
     * @return 中间对象
     */
    private Record getRecord(DirectedGraphNode node, Map<DirectedGraphNode, Record> table) {
        // 如果缓存有则直接返回
        if (table.containsKey(node)) return table.get(node);
        int times = 0;
        // 递归获取所有直接邻居的总点次
        for (DirectedGraphNode neighbor : node.neighbors) {
            times += getRecord(neighbor, table).times;
        }
        // 生成当前点的中间对象放到缓存中
        Record value = new Record(node, times + 1);
        table.put(node, value);
        return value;
    }

    // 记录点与点次的对象，一个中间对象
    private static class Record {
        public DirectedGraphNode node;
        public int times;

        public Record(DirectedGraphNode node, int times) {
            this.node = node;
            this.times = times;
        }
    }
}
