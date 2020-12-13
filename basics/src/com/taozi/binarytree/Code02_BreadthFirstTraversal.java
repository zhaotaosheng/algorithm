package com.taozi.binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树宽度优先遍历遍历，按层遍历
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/12 19:06
 */
public class Code02_BreadthFirstTraversal {
    // 二叉树节点
    private static class Node {
        public Integer value;
        public Node left;
        public Node right;
    }

    /**
     * 二叉树宽度优先遍历遍历，按层遍历
     *
     * @param root 树的根节点
     */
    private void traversal(Node root) {
        if (root == null) return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.println(cur);
            if (cur.left != null)
                queue.add(root.left);
            if (cur.right != null)
                queue.add(root.right);
        }
    }
}
