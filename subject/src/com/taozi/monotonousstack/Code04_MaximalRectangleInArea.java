package com.taozi.monotonousstack;

import java.util.Stack;

/**
 * 给定一个二维数组matrix，其中的值不是0就是1，
 * 返回全部由1组成的最大子矩形，内部有多少个1
 *
 * @author taosheng.zhao
 * @since 2021/1/29 12:38
 */
public class Code04_MaximalRectangleInArea {

    public static int maximalNumber(char[][] matrix) {
        if (matrix == null || matrix.length < 1 || matrix[0].length < 1) return 0;
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        int[] histogram = new int[matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < histogram.length; col++) {
                histogram[col] = matrix[row][col] == '0' ? 0 : histogram[col] + 1;
            }
            for (int col = 0; col < histogram.length; col++) {
                while (!stack.isEmpty() && histogram[stack.peek()] >= histogram[col]) {
                    Integer pop = stack.pop();
                    int leftLess = stack.isEmpty() ? -1 : stack.peek();
                    res = Math.max(res, (col - leftLess - 1) * histogram[pop]);
                }
                stack.push(col);
            }
            while (!stack.isEmpty()) {
                Integer pop = stack.pop();
                int leftLess = stack.isEmpty() ? -1 : stack.peek();
                res = Math.max(res, (histogram.length - leftLess - 1) * histogram[pop]);
            }
        }
        return res;
    }
}
