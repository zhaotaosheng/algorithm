package com.taozi.binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 求二叉树最大宽度
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/13 20:56
 */
public class Code05_MaxWidth {
    // 二叉树节点
    private static class Node {
        public Integer value;
        public Node left;
        public Node right;
    }

    /**
     * 获取树的最大宽度，基于树的宽度遍历，记录每层的最后一个节点
     *
     * @param root 根节点
     * @return 最大宽度
     */
    private int getMaxWidth(Node root) {
        if (root == null) return 0;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        // 当前层的最后一个节点
        Node curEnd = root;
        // 下一层的最后一个节点
        Node nextEnd = null;
        // 最大宽度
        int maxWidth = 0;
        // 当前层节点个数
        int curWidth = 0;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            // 按层遍历，如果有左节点则左节点需要进入队列
            // 并且它属于下一层节点，则需要更新下一层节点尾
            if (cur.left != null) {
                queue.add(cur.left);
                nextEnd = cur.left;
            }
            // 按层遍历，如果有右节点则右节点需要进入队列
            // 并且它属于下一层节点，则需要更新下一层节点尾
            if (cur.right != null) {
                queue.add(cur.right);
                nextEnd = cur.right;
            }
            // 无论该节点是否有子节点，它都属于当前层，则当前层个数加1
            curWidth++;
            // 如果该节点到达该层的尾，则准备进行下一层的遍历
            if (curEnd == cur) {
                maxWidth = Math.max(curWidth, maxWidth);
                curEnd = nextEnd;
            }
        }
        return maxWidth;
    }
}
