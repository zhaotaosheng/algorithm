package com.taozi.dynamicprogramming;

/**
 * 给定一个二维数组matrix，一个人必须从左上角出发，最后到达右下角
 * 沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和
 * 返回最小距离累加和
 *
 * @author taosheng.zhao
 * @since 2021/1/14 12:14
 */
public class Code10_MinPathSum {

    public static int minPathSum(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = matrix[0][0];
        for (int cur = 1; cur < col; cur++) {
            dp[0][cur] = matrix[0][cur] + dp[0][cur - 1];
        }
        for (int cur = 1; cur < row; cur++) {
            dp[cur][0] = matrix[cur][0] + dp[cur - 1][0];
        }
        for (int x = 1; x < row; x++) {
            for (int y = 1; y < col; y++) {
                dp[x][y] = matrix[x][y] + Math.min(dp[x - 1][y], dp[x][y - 1]);
            }
        }
        return dp[row - 1][col - 1];
    }

    public static int minPathSum1(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[] dp = new int[col];
        dp[0] = matrix[0][0];
        // 第一行的信息维护完整
        for (int cur = 1; cur < col; cur++) {
            // 每个位置的值 = 当前位值 + 前一位值
            dp[cur] = matrix[0][cur] + dp[cur - 1];
        }
        // 迭代更新每行的信息
        for (int x = 1; x < row; x++) {
            // 每行第一个位置值 = 当前位值 + 上一位值，而dp[0]本身就代表上一位值
            dp[0] += matrix[x][0];
            // 更新当前行中列的信息
            for (int y = 1; y < col; y++) {
                // 位置值 = 当前位值 + Math.min(前一位值, 上一位值)
                // 前一位值dp[y - 1]，上一位值dp[y]
                dp[y] = matrix[x][y] + Math.min(dp[y - 1], dp[y]);
            }
        }
        return dp[col - 1];
    }
}
