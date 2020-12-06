package com.taozi.linkedlist;

import java.util.Stack;

/**
 * 给定一个单链表的头节点head，请判断该链表是否为回文结构
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/6 21:06
 */
public class Code02_PalindromicList {
    // 单链表节点
    private class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 将链表压入栈中，这样弹栈就是逆序，通过与正序对比判断是否回文
     *
     * @param head 头节点
     * @return 是否回文
     */
    private boolean isPalindromicContainer(Node head) {
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        // 将所有节点压入栈
        while (cur != null) {
            stack.push(head);
            cur = cur.next;
        }
        // 依次弹出，弹出就是逆序，如果和正序一致就是回文
        while (head != null) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 将链表以中点划分，右侧反转，依次对比
     *
     * @param head 头节点
     * @return 是否回文
     */
    private boolean isPalindromic(Node head) {
        if (head == null || head.next == null) return true;
        Node n1 = head;
        Node n2 = head;
        // n1为链表中点，奇数个为正中，偶数个为上中
        while (n2.next != null && n2.next.next != null) {
            n1 = n1.next;
            n2 = n2.next.next;
        }
        // n2是右侧区第一个节点
        n2 = n1.next;
        // 将左侧链表结束
        n1.next = null;
        // 用于链表反转时记录下一个节点
        Node n3 = null;
        while (n2 != null) {
            // 保存n2的下一个引用
            n3 = n2.next;
            // 将n2的下一个节点反向指向n1
            n2.next = n1;
            // 将n1右移，n1相当于记录节点的prev节点
            n1 = n2;
            // 将n2右移
            n2 = n3;
        }
        // 循环结束后n1相当于最后一个节点
        n3 = n1;
        n2 = head;
        boolean res = true;
        while (n1 != null && n2 != null) {
            if (n1.value != n2.value) {
                res = false;
                break;
            }
            n1 = n1.next;
            n2 = n2.next;
        }
        // 将链表还原
        n1 = n3.next;
        n3.next = null;
        while (n1 != null) {
            // 保存n1的下一个引用
            n2 = n1.next;
            // 将n1的下一个节点指向n3
            n1.next = n3;
            // 将n1左移，n1相当于记录节点的prev节点
            n1 = n2;
            // 将n3左移
            n3 = n1;
        }
        return res;
    }
}
