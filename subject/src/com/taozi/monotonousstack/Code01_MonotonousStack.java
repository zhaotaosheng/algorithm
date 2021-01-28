package com.taozi.monotonousstack;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 一种特别设计的栈结构，为了解决如下的问题：
 * <p>
 * 给定一个可能含有重复值的数组arr，i位置的数一定存在如下两个信息
 * 1）arr[i]的左侧离i最近并且小于(或者大于)arr[i]的数在哪？
 * 2）arr[i]的右侧离i最近并且小于(或者大于)arr[i]的数在哪？
 * 如果想得到arr中所有位置的两个信息，怎么能让得到信息的过程尽量快。
 * <p>
 * 那么到底怎么设计呢？
 *
 * @author ZhaoTaoSheng
 * @since 2021/1/28 20:35
 */
public class Code01_MonotonousStack {

    /**
     * 在没有重复的数组arr中，找到i位置的左侧和右侧最近且小于arr[i]的位置
     *
     * @param arr 数组
     * @return i位置 -> [左侧最近且小于arr[i]的位置，右侧最近且小于arr[i]的位置]
     */
    public static int[][] getNearLessNoRepeat(int[] arr) {
        int[][] res = new int[arr.length][2];
        // 栈只用来保存位置
        Stack<Integer> stack = new Stack<>();
        for (int cur = 0; cur < arr.length; cur++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[cur]) {
                // 如果当前位置的数引起栈顶弹出，说明当前位置数是第一个在[栈顶,arr.length)范围上小于arr[栈顶]的数
                // 结算当前栈顶的结果
                Integer index = stack.pop();
                // 它本身压着的那个数就是左侧最近且小于arr[栈顶]的位置
                res[index][0] = stack.isEmpty() ? -1 : stack.peek();
                // 引起栈顶弹出的数就是右侧最近且小于arr[栈顶]的位置
                res[index][1] = cur;
            }
            // 将当前位置入栈，等待后续结算
            stack.push(cur);
        }
        // 遍历完数组后，栈中可能还有元素，则依次结算
        while (!stack.isEmpty()) {
            // 结算当前栈顶的结果
            Integer index = stack.pop();
            // 它本身压着的那个数就是左侧最近且小于arr[栈顶]的位置
            res[index][0] = stack.isEmpty() ? -1 : stack.peek();
            // 没有数字引起栈顶弹出，所以右侧没有结
            res[index][1] = -1;
        }
        return res;
    }

    /**
     * 在有重复的数组arr中，找到i位置的左侧和右侧最近且小于arr[i]的位置
     *
     * @param arr 数组
     * @return i位置 -> [左侧最近且小于arr[i]的位置，右侧最近且小于arr[i]的位置]
     */
    public static int[][] getNearLessRepeat(int[] arr) {
        int[][] res = new int[arr.length][2];
        // 栈只用来保存具有相同大小的位置
        Stack<List<Integer>> stack = new Stack<>();
        for (int cur = 0; cur < arr.length; cur++) {
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[cur]) {
                // 如果当前位置的数引起栈顶弹出，说明当前位置数是第一个在[栈顶,arr.length)范围上小于arr[栈顶]的数
                // 结算当前栈顶包含所有位置的链表的结果
                List<Integer> indexList = stack.pop();
                for (Integer integer : indexList) {
                    // 它本身压着的那个链表里的最大值就是左侧最近且小于arr[栈顶]的位置
                    res[integer][0] = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                    // 引起栈顶弹出的数就是右侧最近且小于arr[栈顶]的位置
                    res[integer][1] = cur;
                }
            }
            // 如果当前位置的数与栈顶相等，那么加到链表里
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[cur]) {
                stack.peek().add(cur);
            } else {
                // 将当前位置生成小链表入栈，等待后续结算
                List<Integer> ele = new LinkedList<>();
                ele.add(cur);
                stack.push(ele);
            }
        }
        // 遍历完数组后，栈中可能还有元素，则依次结算
        while (!stack.isEmpty()) {
            // 结算当前栈顶包含所有位置的链表的结果
            List<Integer> indexList = stack.pop();
            for (Integer integer : indexList) {
                // 它本身压着的那个链表里的最大值就是左侧最近且小于arr[栈顶]的位置
                res[integer][0] = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                // 引起栈顶弹出的数就是右侧最近且小于arr[栈顶]的位置
                res[integer][1] = -1;
            }
        }
        return res;
    }
}
