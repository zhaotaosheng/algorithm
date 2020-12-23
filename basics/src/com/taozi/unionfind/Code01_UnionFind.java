package com.taozi.unionfind;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * 并查集
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/23 18:44
 */
public class Code01_UnionFind<T> {
    // 包装类，将给定数据包装成Node
    public static class Node<T> {
        public T value;

        public Node(T value) {
            this.value = value;
        }
    }

    // 用来得到对应的包装类
    public Map<T, Node<T>> wraps = new HashMap<>();
    // 用来得到它的父节点
    public Map<Node<T>, Node<T>> parents = new HashMap<>();
    // 用来得到它所在集合的大小
    public Map<Node<T>, Integer> sizeMap = new HashMap<>();

    /**
     * 通过给定数据初始化集合
     *
     * @param values 给定数据
     */
    public Code01_UnionFind(List<T> values) {
        for (T value : values) {
            Node<T> wrap = new Node<>(value);
            wraps.put(value, wrap);
            parents.put(wrap, wrap);
            sizeMap.put(wrap, 1);
        }
    }

    /**
     * 查询样本a和样本b是否属于一个集合
     *
     * @param a 样本a
     * @param b 样本b
     * @return 是否属于同一集合
     */
    public boolean isSameSet(T a, T b) {
        return findRoot(wraps.get(a)) == findRoot(wraps.get(b));
    }

    /**
     * 通过给定的Node找到它的根Node
     *
     * @param node 给定Node
     * @return 根Node
     */
    private Node<T> findRoot(Node<T> node) {
        // 使用栈暂时保存沿途节点，最后将沿途节点都挂在根节点下
        Stack<Node<T>> path = new Stack<>();
        // 当节点自身与其父节点是一个时，则其为根节点
        while (node != parents.get(node)) {
            node = parents.get(node);
            path.push(node);
        }
        while (!path.isEmpty()) {
            parents.put(path.pop(), node);
        }
        return node;
    }

    /**
     * 把a和b各自所在集合的所有样本合并成一个集合
     *
     * @param a 样本a
     * @param b 样本b
     */
    public void union(T a, T b) {
        Node<T> aRoot = findRoot(wraps.get(a));
        Node<T> bRoot = findRoot(wraps.get(b));
        if (aRoot == bRoot) return;
        Integer aSize = sizeMap.get(aRoot);
        Integer bSize = sizeMap.get(bRoot);
        // 找到节点数量较大的集合
        Node<T> bigNode = aSize > bSize ? aRoot : bRoot;
        Node<T> smallNode = bigNode == aRoot ? bRoot : aRoot;
        parents.put(smallNode, bigNode);
        // sizeMap仅需要维护根集合所对应大小即可
        sizeMap.remove(smallNode);
        sizeMap.put(bigNode, aSize + bSize);
    }
}
