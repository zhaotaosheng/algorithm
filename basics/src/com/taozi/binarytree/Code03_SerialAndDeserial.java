package com.taozi.binarytree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树序列化和反序列化
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/13 12:06
 */
public class Code03_SerialAndDeserial {
    // 二叉树节点
    private static class Node {
        public Integer value;
        public Node left;
        public Node right;

        public Node(Integer value) {
            this.value = value;
        }
    }

    /**
     * 序列化
     *
     * @param root 根节点
     * @return 序列化到一个队列里
     */
    public Queue<String> serial(Node root) {
        Queue<String> res = new LinkedList<>();
        preSerial(root, res);
//        inSerial(root, res);
//        postSerial(root, res);
//        levelSerial(root, res);
        return res;
    }

    /**
     * 前序序列化
     *
     * @param root 根节点
     * @param res  序列化目的地
     */
    public void preSerial(Node root, Queue<String> res) {
        if (root == null) {
            res.add(null);
        } else {
            res.add(String.valueOf(root.value));
            preSerial(root.left, res);
            preSerial(root.right, res);
        }
    }

    /**
     * 中序序列化
     *
     * @param root 根节点
     * @param res  序列化目的地
     */
    public void inSerial(Node root, Queue<String> res) {
        if (root == null) {
            res.add(null);
        } else {
            inSerial(root.left, res);
            res.add(String.valueOf(root.value));
            inSerial(root.right, res);
        }
    }

    /**
     * 后序序列化
     *
     * @param root 根节点
     * @param res  序列化目的地
     */
    public void postSerial(Node root, Queue<String> res) {
        if (root == null) {
            res.add(null);
        } else {
            postSerial(root.left, res);
            postSerial(root.right, res);
            res.add(String.valueOf(root.value));
        }
    }

    /**
     * 按层序列化
     *
     * @param root 根节点
     * @param res  序列化目的地
     */
    public void levelSerial(Node root, Queue<String> res) {
        if (root == null) {
            res.add(null);
            return;
        }
        res.add(String.valueOf(root.value));
        // 用来按层遍历的队列
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            root = queue.poll();
            // 维持序列化队列与遍历队列一致
            // 子节点的序列化是在父节点循环里执行的
            if (root.left != null) {
                res.add(String.valueOf(root.left.value));
                queue.add(root.left);
            } else {
                res.add(null);
            }
            if (root.right != null) {
                res.add(String.valueOf(root.right.value));
                queue.add(root.right);
            } else {
                res.add(null);
            }
        }
    }

    /**
     * 反序列化
     *
     * @param res 序列化队列
     * @return 树根节点
     */
    public Node deserial(Queue<String> res) {
        if (res == null || res.size() < 1) return null;
        return preDeserial(res);
//        return postDeserial(res);
//        return levelDeserial(res);
    }

    /**
     * 前序反序列化
     *
     * @param preList 前序序列化队列
     * @return 树根节点
     */
    private Node preDeserial(Queue<String> preList) {
        String value = preList.poll();
        if (value == null) {
            return null;
        }
        // preList是前序遍历循序 -> 头左右
        Node root = generateNode(value);
        root.left = preDeserial(preList);
        root.right = preDeserial(preList);
        return root;
    }

    /**
     * 后序反序列化
     *
     * @param postList 后续序列化队列
     * @return 树根节点
     */
    private Node postDeserial(Queue<String> postList) {
        Stack<String> stack = new Stack<>();
        // postList是后序遍历顺序 -> 左右头
        // 转换成stack的顺序顺序就是 -> 头右左
        while (!postList.isEmpty()) {
            stack.push(postList.poll());
        }
        return postDeserial(stack);
    }

    /**
     * 将后序转换的栈反序列化
     *
     * @param stack 栈
     * @return 根节点
     */
    private Node postDeserial(Stack<String> stack) {
        // 和前序反序列化类似
        String value = stack.pop();
        if (value == null) {
            return null;
        }
        // 栈的顺序是 头右左，所以按 头右左 的顺序反序列化
        Node root = generateNode(value);
        root.right = postDeserial(stack);
        root.left = postDeserial(stack);
        return root;
    }

    /**
     * 按层序列化
     *
     * @param levelList 按层序列化队列
     * @return 树根节点
     */
    private Node levelDeserial(Queue<String> levelList) {
        Node root = generateNode(levelList.poll());
        // 用来按层遍历的队列
        Queue<Node> queue = new LinkedList<>();
        if (root != null) queue.add(root);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            cur.left = generateNode(levelList.poll());
            cur.right = generateNode(levelList.poll());
            if (cur.left != null) queue.add(cur.left);
            if (cur.right != null) queue.add(cur.right);
        }
        return root;
    }

    /**
     * 根据给定值生成节点
     *
     * @param value 值
     * @return 节点
     */
    private Node generateNode(String value) {
        if (value == null) return null;
        return new Node(Integer.valueOf(value));
    }
}
