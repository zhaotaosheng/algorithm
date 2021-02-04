package com.taozi.D02_MonotonousStack;

import java.util.Stack;

/**
 * 给定一个非负数组arr，代表直方图
 * 返回直方图的最大长方形面积
 *
 * @author taosheng.zhao
 * @since 2021/1/29 12:37
 */
public class Code03_LargestRectangleInHistogram {

    public static int largestArea(int[] arr) {
        if (arr == null || arr.length < 1) return 0;
        Stack<Integer> stack = new Stack<>();
        int res = 0;
        for (int cur = 0; cur < arr.length; cur++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[cur]) {
                res = Math.max(res, arr[stack.pop()] * (cur - (stack.isEmpty() ? -1 : stack.peek()) - 1));
            }
            stack.push(cur);
        }
        while (!stack.isEmpty()) {
            res = Math.max(res, arr[stack.pop()] * (arr.length - (stack.isEmpty() ? -1 : stack.peek()) - 1));
        }
        return res;
    }
}
