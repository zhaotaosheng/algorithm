package com.taozi.D02_MonotonousStack;

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
        // 求出分别以row行为底的直方图的最大面积就是子矩形中最多1的数量
        for (int row = 0; row < matrix.length; row++) {
            // 构建以row行为底的直方图，如果matrix[row][col] == '0'表示它与上一行无法连通，所以高度归零
            for (int col = 0; col < histogram.length; col++) {
                histogram[col] = matrix[row][col] == '0' ? 0 : histogram[col] + 1;
            }
            // 下边就是常规求直方图最大矩形面积
            for (int col = 0; col < histogram.length; col++) {
                while (!stack.isEmpty() && histogram[stack.peek()] >= histogram[col]) {
                    Integer cur = stack.pop();
                    int leftLess = stack.isEmpty() ? -1 : stack.peek();
                    res = Math.max(res, (col - leftLess - 1) * histogram[cur]);
                }
                stack.push(col);
            }
            while (!stack.isEmpty()) {
                Integer cur = stack.pop();
                int leftLess = stack.isEmpty() ? -1 : stack.peek();
                res = Math.max(res, (histogram.length - leftLess - 1) * histogram[cur]);
            }
        }
        return res;
    }
}
