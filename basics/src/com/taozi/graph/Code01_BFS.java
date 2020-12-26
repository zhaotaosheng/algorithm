package com.taozi.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * 宽度优先遍历
 * 1，利用队列实现
 * 2，从源节点开始依次按照宽度进队列，然后弹出
 * 3，每弹出一个点，把该节点所有没有进过队列的邻接点放入队列
 * 4，直到队列变空
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/26 20:31
 */
public class Code01_BFS {

    /**
     * 根据提供的起始点按照宽度优先的方式遍历图
     *
     * @param node 遍历起始点
     */
    public void bfs(Graph.Node node) {
        if (node == null) return;
        // 用来保存已经打印过的节点，因为图中一个节点可能会出现多次
        Set<Graph.Node> completeNodes = new HashSet<>();
        // 队列用来按宽度优先遍历
        Queue<Graph.Node> queue = new LinkedList<>();
        queue.add(node);
        completeNodes.add(node);
        Graph.Node cur;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            // 弹出队列时打印
            System.out.println(cur);
            for (Graph.Node next : cur.nexts) {
                // 如果下一节点没有处理过那么就要处理，否则跳过
                if (!completeNodes.contains(next)) {
                    queue.add(next);
                    completeNodes.add(next);
                }
            }
        }
    }
}
