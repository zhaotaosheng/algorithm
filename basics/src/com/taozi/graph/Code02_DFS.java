package com.taozi.graph;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 深度优先遍历
 * 1，利用栈实现
 * 2，从源节点开始把节点按照深度放入栈，然后弹出
 * 3，每弹出一个点，把该节点下一个没有进过栈的邻接点放入栈
 * 4，直到栈变空
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/26 20:46
 */
public class Code02_DFS {

    public void dfs(Graph.Node node) {
        if (node == null) return;
        // 用来保存已经打印过的节点，因为图中一个节点可能会出现多次
        Set<Graph.Node> completeNodes = new HashSet<>();
        // 栈用来保存整条路径
        Stack<Graph.Node> stack = new Stack<>();
        stack.push(node);
        completeNodes.add(node);
        // 进栈就打印
        System.out.println(node);
        Graph.Node cur;
        while (!stack.isEmpty()) {
            cur = stack.pop();
            for (Graph.Node next : cur.nexts) {
                // 遍历到未处理的下一节点就跳出
                if (!completeNodes.contains(next)) {
                    // 将当前节点与下一节点压栈
                    stack.push(cur);
                    stack.push(next);
                    completeNodes.add(next);
                    // 进栈就打印
                    System.out.println(next);
                    break;
                }
            }
        }
    }
}
