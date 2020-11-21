package com.taozi.structure;

/**
 * 双链表实现队列、双端队列、栈
 *
 * @author ZhaoTaoSheng
 * @since 2020/11/21 18:39
 */
public class Code03_QueueAndStackListImpl {

    /**
     * 双链表节点
     *
     * @param <T>
     */
    private static class Node<T> {
        public T value;
        public Node<T> prev;
        public Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            if (prev == null) {
                return "null" + " <- " + value + " <--> " + next;
            }
            return value + " <--> " + next;
        }
    }

    /**
     * 双向链表实现队列结构，使用一个Node指针指向队列头
     * ------head
     * null <- A <--> null
     * -------------head------put("B")
     * null <- B <--> A <--> null
     * ------head------take()
     * null <- B <--> null
     *
     * @param <T>
     */
    public static class Queue<T> {
        // 队列头节点
        public Node<T> head;

        /**
         * 找到队列尾，将新节点插入
         *
         * @param value 新节点值
         */
        public void put(T value) {
            Node<T> cur = new Node<>(value);
            if (head == null) {
                head = cur;
                return;
            }
            Node<T> node = head;
            while (true) {
                if (node.next == null) {
                    node.next = cur;
                    cur.prev = node;
                    break;
                }
                node = node.next;
            }
        }

        /**
         * 从队列头取数据，将head指针后移
         *
         * @return 队列头数据
         */
        public T take() {
            T value = null;
            if (head != null) {
                value = head.value;
                head = head.next;
                head.prev = null;
            }
            return value;
        }

        @Override
        public String toString() {
            Node<T> node = head;
            StringBuilder builder = new StringBuilder();
            while (node != null) {
                builder.append(node.value).append(" , ");
                node = node.next;
            }
            return builder.toString();
        }
    }

    /**
     * 双向链表实现双端队列，一个Node指针指向队列头，一个Node指针指向队列尾
     * ----head/tail
     * null <- A -> null
     * ------tail----head------putForTail("B")
     * null <- B <--> A -> null
     * ------tail-----------head------putForHead("C")
     * null <- B <--> A <--> C -> null
     * ------tail----head------takeForTail()
     * null <- A <--> C -> null
     * ----tail/head------takeForHead()
     * null <- C -> null
     *
     * @param <T>
     */
    public static class Deque<T> {
        // 头节点
        public Node<T> head;
        // 尾节点
        public Node<T> tail;

        /**
         * 从头放入数据，移动head指针
         *
         * @param value 新的head节点值
         */
        public void putForHead(T value) {
            Node<T> cur = new Node<>(value);
            // 为空说明队列里没有节点，初始化head和tail节点
            if (head == null) {
                head = cur;
                tail = cur;
            }
            // 否则将head指针前移
            else {
                cur.next = head;
                head.prev = cur;
                head = cur;
            }
        }

        /**
         * 从尾放入数据，移动tail指针
         *
         * @param value 新的tail节点值
         */
        public void putForTail(T value) {
            Node<T> cur = new Node<>(value);
            // 为空说明队列里没有节点，初始化head和tail节点
            if (tail == null) {
                head = cur;
                tail = cur;
            }
            // 否则将head指针后移
            else {
                cur.prev = tail;
                tail.next = cur;
                tail = cur;
            }
        }

        /**
         * 从头取出数据，移动head指针
         *
         * @return head节点值
         */
        public T takeForHead() {
            if (head == null) return null;
            Node<T> cur = head;
            // 队列中只有一个节点时head和tail才相等
            if (head == tail) {
                head = null;
                tail = null;
            }
            // 否则将head指针后移
            else {
                head = head.next;
                head.prev = null;
                cur.next = null;
            }
            return cur.value;
        }

        /**
         * 从尾取出数据，移动tail指针
         *
         * @return head节点值
         */
        public T takeForTail() {
            if (tail == null) return null;
            Node<T> cur = tail;
            // 队列中只有一个节点时head和tail才相等
            if (head == tail) {
                head = null;
                tail = null;
            }
            // 否则将tail指针前移
            else {
                tail = tail.prev;
                tail.next = null;
                cur.prev = null;
            }
            return cur.value;
        }
    }

    /**
     * 双向链表实现栈结构，用一个Node指针指向栈顶
     * ------stackTop
     * null <- A <--> null
     * ------stackTop------poll("B")
     * null <- B <--> A <--> null
     * ------stackTop------pop()
     * null <- A <--> null
     *
     * @param <T>
     */
    public static class Stack<T> {
        // 栈顶指针
        public Node<T> stackTop;

        /**
         * 入栈将原栈顶的next指针指向新入栈的Node，
         * 将新入栈的Node的prev指针指向原栈顶，将栈顶后移
         *
         * @param value 新的栈顶值
         */
        public void poll(T value) {
            Node<T> cur = new Node<>(value);
            // 栈顶不为null的时候说明栈内有元素
            if (stackTop != null) {
                // 将新加入节点关联到栈顶下一位
                stackTop.next = cur;
                cur.prev = stackTop;
            }
            // 新节点成为栈顶
            stackTop = cur;
        }

        /**
         * 出栈将栈顶前移，栈顶的next应该永远指向null
         *
         * @return 栈顶值
         */
        public T pop() {
            T value = null;
            // 栈顶不为null的时候说明栈内有元素
            if (stackTop != null) {
                // 获取栈顶值
                value = stackTop.value;
                // 将栈顶前移一个节点
                stackTop = stackTop.prev;
                // 栈顶的next应永远为null
                stackTop.next = null;
            }
            return value;
        }

        @Override
        public String toString() {
            Node<T> node = stackTop;
            StringBuilder builder = new StringBuilder();
            while (node != null) {
                builder.append(node.value).append(" , ");
                node = node.prev;
            }
            return builder.toString();
        }
    }
}
