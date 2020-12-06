package com.taozi.linkedlist;

/**
 * 将单向链表按某值划分成左边小、中间相等、右边大的形式
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/6 22:13
 */
public class Code03_Partition {
    // 单链表节点
    private class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 将链表转为数组，然后按荷兰国旗问题处理，最后转回链表
     *
     * @param head   头节点
     * @param target 目标值
     * @return 新的头节点
     */
    public Node partition_container(Node head, int target) {
        if (head == null || head.next == null) return head;
        // num表示链表元素个数
        int num = 0;
        Node cur = head;
        while (cur != null) {
            num++;
            cur = cur.next;
        }
        // 生成一个和链表等长的数组
        Node[] arr = new Node[num];
        cur = head;
        // num表示数组下标
        num = 0;
        // 将链表里的节点按照顺序落在数组中
        while (cur != null) {
            arr[num++] = cur;
            cur = cur.next;
        }
        // 将数组分区，荷兰国旗问题
        int less = -1;
        int more = arr.length;
        // num表示数组下标，既当前看的数组元素
        num = 0;
        while (num < more) {
            if (arr[num].value < target) {
                Node temp = arr[++less];
                arr[less] = arr[num];
                arr[num] = temp;
                num++;
            } else if (arr[num].value == target) {
                num++;
            } else {
                Node temp = arr[--more];
                arr[more] = arr[num];
                arr[num] = temp;
            }
        }
        // 将数组串回链表
        for (int i = 1; i < arr.length; i++) {
            arr[i - 1].next = arr[i];
        }
        arr[arr.length - 1].next = null;
        return arr[0];
    }

    /**
     * 使用6个变量，辨识小于区、等于区、大于区的头尾节点，最后相连
     *
     * @param head   头节点
     * @param target 目标值
     * @return 新的头节点
     */
    public Node partition(Node head, int target) {
        // 小于区的头节点和尾节点
        Node sH = null;
        Node sT = null;
        // 等于区的头节点和尾节点
        Node eH = null;
        Node eT = null;
        // 大于区的头节点和尾节点
        Node bH = null;
        Node bT = null;
        // 因为节点要脱离，用来保存下一个节点的指针
        Node next = null;
        while (head != null) {
            next = head.next;
            head.next = null;
            if (head.value < target) {
                if (sH == null) {
                    sH = head;
                    sT = head;
                } else {
                    sT.next = head;
                    sT = head;
                }
            } else if (head.value == target) {
                if (eH == null) {
                    eH = head;
                    eT = head;
                } else {
                    eT.next = head;
                    eT = head;
                }
            } else {
                if (bH == null) {
                    bH = head;
                    bT = head;
                } else {
                    bT.next = head;
                    bT = head;
                }
            }
            head = next;
        }
        // 将各个区串起来
        if (sH != null) {
            sT.next = eH;
            // 选出来去连接大于区头节点的尾节点
            eT = eT == null ? sT : eT;
        }
        // eT如果不为null的话，可能是小于区尾节点可能是等于区尾节点
        if (eT != null) {
            eT.next = bH;
        }
        return sH != null ? sH : (eH != null ? eH : bH);
    }
}
