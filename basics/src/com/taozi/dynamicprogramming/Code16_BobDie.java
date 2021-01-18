package com.taozi.dynamicprogramming;

/**
 * 给定5个参数，N，M，row，col，k
 * 表示在N*M的区域上，醉汉Bob初始在(row,col)位置
 * Bob一共要迈出k步，且每步都会等概率向上下左右四个方向走一个单位
 * 任何时候Bob只要离开N*M的区域，就直接死亡
 * 返回k步之后，Bob还在N*M的区域的概率
 *
 * @author taosheng.zhao
 * @since 2021/1/18 15:26
 */
public class Code16_BobDie {


    public static double bobDie(int N, int M, int row, int col, int k) {
        long dieWays = die(row, col, k, N, M);
        long liveWays = live(row, col, k, N, M);
        double allWays = Math.pow(4, k);
        System.out.format("liveWays %s + dieWays %s = %s , allWays = %s",
                liveWays, dieWays, liveWays + dieWays, allWays);
        System.out.println();
        return 1 - dieWays / allWays;// 或者使用 liveWays / allWays
    }

    /**
     * 从(row,col)位置开始走k步后死亡的走法数
     *
     * @param row 横坐标
     * @param col 纵坐标
     * @param k   剩余的步数
     * @param N   区域横坐标
     * @param M   区域纵坐标
     * @return 死亡走法的方法数
     */
    private static long die(int row, int col, int k, int N, int M) {
        if (row < 0 || row == N || col < 0 || col == M) {
            return (long) Math.pow(4, k);
        }
        if (k == 0) return 0;
        long ways = die(row + 1, col, k - 1, N, M);
        ways += die(row - 1, col, k - 1, N, M);
        ways += die(row, col - 1, k - 1, N, M);
        ways += die(row, col + 1, k - 1, N, M);
        return ways;
    }

    /**
     * 从(row,col)位置开始走k步后存活的走法数
     *
     * @param row 横坐标
     * @param col 纵坐标
     * @param k   剩余的步数
     * @param N   区域横坐标
     * @param M   区域纵坐标
     * @return 死亡存活的方法数
     */
    private static long live(int row, int col, int k, int N, int M) {
        if (row < 0 || row == N || col < 0 || col == M) {
            return 0;
        }
        if (k == 0) return 1;
        long ways = live(row + 1, col, k - 1, N, M);
        ways += live(row - 1, col, k - 1, N, M);
        ways += live(row, col - 1, k - 1, N, M);
        ways += live(row, col + 1, k - 1, N, M);
        return ways;
    }

    public static double dp(int N, int M, int row, int col, int k) {
        double allWays = Math.pow(4, k);
        long[][][] dp = new long[N][M][k + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j][0] = 1;
            }
        }
        for (int i = 1; i < k + 1; i++) {
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    dp[r][c][k] = pick(r + 1, c, i - 1, N, M, dp);
                    dp[r][c][k] += pick(r - 1, c, i - 1, N, M, dp);
                    dp[r][c][k] += pick(r, c - 1, i - 1, N, M, dp);
                    dp[r][c][k] += pick(r, c + 1, i - 1, N, M, dp);
                }
            }
        }
        return dp[row][col][k] / allWays;
    }

    private static long pick(int row, int col, int k, int N, int M, long[][][] dp) {
        if (row < 0 || row == N || col < 0 || col == M) {
            return 0;
        }
        return dp[row][col][k];
    }
}
