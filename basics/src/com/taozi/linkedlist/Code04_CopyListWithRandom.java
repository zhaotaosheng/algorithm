package com.taozi.linkedlist;

/**
 * 一种特殊的单链表节点类描述如下
 * class Node {
 * int value;
 * Node next;
 * Node rand;
 * Node(int val) { value = val; }
 * }
 * rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节点，也可能指向null。
 * 给定一个由Node节点类型组成的无环单链表的头节点 head，请实现一个函数完成这个链表的复制，并返回复制的新链表的头节点。
 * 【要求】
 * 时间复杂度O(N)，额外空间复杂度O(1)
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/7 18:15
 */
public class Code04_CopyListWithRandom {
    // 带有随机指针的节点
    private class Node {
        int value;
        Node next;
        Node rand;

        Node(int val) {
            value = val;
        }
    }

    /**
     * 通过原链表扩展出复制节点后将原节点与新节点分离
     *
     * @param head 要复制的头节点
     * @return 复制的头节点
     */
    private Node copy(Node head) {
        if (head == null) return null;
        Node cur = head;
        Node next = null;
        Node copy = null;
        // 将复制的节点串在原链表里，1 -> 1` -> 2 -> 2` ->3 -> 3`...
        while (cur != null) {
            // 保留原有的下一个节点
            next = cur.next;
            copy = new Node(cur.value);
            // 将cur -> next断开，变成cur -> copy -> next
            copy.next = next;
            cur.next = copy;
            // 处理下一个节点
            cur = next;
        }
        cur = head;
        // 处理复制节点的rand指针
        while (cur != null) {
            // 找到下一个原有节点
            next = cur.next.next;
            // 下一个复制节点
            copy = cur.next;
            // 复制节点的rand指针指向原有节点的rand指针的下一位，如果不为空的话
            copy.rand = cur.rand != null ? cur.rand.next : null;
            // 处理下一个节点
            cur = next;
        }
        cur = head;
        Node res = head.next;
        // 将链表分离，1 -> 1` -> 2 -> 2` ->3 -> 3`...分为 1->2->3...和1`->2`>3`...
        while (cur != null) {
            // 找到原有的下一个节点
            next = cur.next.next;
            // 找到复制的下一个节点
            copy = cur.next;
            // 分离
            cur.next = next;
            copy.next = next != null ? next.next : null;
            // 处理下一个节点
            cur = next;
        }
        return res;
    }
}
