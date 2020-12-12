package com.taozi.binarytree;

import java.util.Stack;

/**
 * 二叉树遍历
 * 先序、中序、后序都可以在递归序的基础上加工出来
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/12 18:06
 */
public class Code01_Traversal {
    // 二叉树节点
    private static class Node {
        public Integer value;
        public Node left;
        public Node right;
    }

    /**
     * 先序：任何子树的处理顺序都是，先头节点、再左子树、然后右子树
     *
     * @param root 树的根节点
     */
    private void pre(Node root) {
        if (root == null) return;
        System.out.println(root.value);
        pre(root.left);
        pre(root.right);
    }

    private void pre_unRecursive(Node root) {
        if (root == null) return;
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            root = stack.pop();
            System.out.println(root.value);
            if (root.right != null) stack.push(root.right);
            if (root.left != null) stack.push(root.left);
        }
    }

    /**
     * 中序：任何子树的处理顺序都是，先左子树、再头节点、然后右子树
     *
     * @param root 树的根节点
     */
    private void in(Node root) {
        if (root == null) return;
        in(root.left);
        System.out.println(root.value);
        in(root.right);
    }

    private void in_unRecursive(Node root) {
        if (root == null) return;
        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                System.out.println(root.value);
                root = root.right;
            }
        }
    }

    /**
     * 后序：任何子树的处理顺序都是，先左子树、再右子树、然后头节点
     *
     * @param root 树的根节点
     */
    private void post(Node root) {
        if (root == null) return;
        post(root.left);
        post(root.right);
        System.out.println(root.value);
    }

    private void post_unRecursive(Node root) {
        if (root == null) return;
        Stack<Node> stack = new Stack<>();
        Stack<Node> help = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            root = stack.pop();
            help.push(root);
            if (root.left != null) stack.push(root.left);
            if (root.right != null) stack.push(root.right);
        }
        while (!help.isEmpty()) {
            System.out.println(help.pop().value);
        }
    }
}
