package com.taozi.structure;

/**
 * 数组实现队列、栈
 *
 * @author ZhaoTaoSheng
 * @since 2020/11/21 20:38
 */
public class Code03_QueueAndStackArrayImpl {


    /**
     * 数组实现队列结构，使用一个head指向队列头,使用tail指向队列尾
     * head/tail
     * |       |       |       |       |       |
     * ------put("a")
     * --head------tail
     * |   a   |       |       |       |       |
     * ------put("c")
     * --head-------------tail
     * |   a   |   c   |       |       |       |
     * ------take()
     * -----------head----tail
     * |       |   c   |       |       |       |
     */
    public static class Queue {
        // 存放数据的数组
        public int[] arr;
        // 下一个可以取值的位置
        public int head;
        // 下一个可以存值的位置
        public int tail;
        // 数组中总数据数量
        public int size;
        // 最大数据数量
        public final int arrLength;

        /**
         * 初始化数组大小
         *
         * @param arrLength 数组长度
         */
        public Queue(int arrLength) {
            arr = new int[arrLength];
            this.arrLength = arrLength;
        }

        /**
         * 找到队列尾，将新值插入
         *
         * @param value 新值
         */
        public void add(int value) {
            // size小于数组长度，才有空位继续插入值
            if (size < arrLength) {
                arr[tail] = value;
                // 如果下一个tail的位置等于数组长度，则从0开始
                tail = tail + 1 == arrLength ? 0 : tail + 1;
                size++;
            }
            throw new RuntimeException("队列已满");
        }

        /**
         * 找到队列头，取出数据
         *
         * @return 队列头值
         */
        public int poll() {
            // size大于0，数组中才有值可以取
            if (size > 0) {
                int value = arr[head];
                // 如果下一个head的位置等于数组长度，则从0开始
                head = head + 1 == arrLength ? 0 : head + 1;
                size--;
                return value;
            }
            throw new RuntimeException("队列为空");
        }
    }

    /**
     * 数组实现栈结构，用stackTop指向栈顶(未存放元素)
     * -stackTop
     * |       |       |       |       |       |
     * ------poll("a")
     * ---------stackTop
     * |   a   |       |       |       |       |
     * ------poll("b")
     * -----------------stackTop
     * |   a   |   b   |       |       |       |
     * ------pop()
     * ---------stackTop
     * |   a   |       |       |       |       |
     */
    public static class Stack {
        // 存放数据的数组
        public int[] arr;
        // 栈顶、下一个存放数据的位置
        public int stackTop;
        // 最大数据数量
        public final int arrLength;

        /**
         * 初始化数组大小
         *
         * @param arrLength 数组长度
         */
        public Stack(int arrLength) {
            arr = new int[arrLength];
            this.arrLength = arrLength;
        }

        /**
         * stackTop表示下一个存放数据的位置，所以当它等于arrLength时栈已满
         * 将数据入栈后，栈顶在++，表示下一个数据的位置
         *
         * @param value 新的栈顶值
         */
        public void push(int value) {
            if (stackTop < arrLength) {
                arr[stackTop++] = value;
                return;
            }
            throw new RuntimeException("栈已满");
        }

        /**
         * 在数据出栈前将栈顶--，因为栈顶始终指向没有数据的位置
         *
         * @return 栈顶值
         */
        public int pop() {
            if (stackTop > 0) {
                --stackTop;
                int value = arr[stackTop];
                arr[stackTop] = 0;
                return value;
            }
            throw new RuntimeException("栈已空");
        }
    }
}