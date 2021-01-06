package com.taozi.dynamicprogramming;

import java.util.Arrays;

/**
 * 假设有排成一行的N个位置，记为1~N，N 一定大于或等于 2
 * 开始时机器人在其中的M位置上(M 一定是 1~N 中的一个)
 * 如果机器人来到1位置，那么下一步只能往右来到2位置；
 * 如果机器人来到N位置，那么下一步只能往左来到 N-1 位置；
 * 如果机器人来到中间位置，那么下一步可以往左走或者往右走；
 * 规定机器人必须走 K 步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
 * 给定四个参数 N、M、K、P，返回方法数。
 *
 * @author taosheng.zhao
 * @since 2021/1/6 9:22
 */
public class Code01_RobotWalk {

    /**
     * @param N 总位置数
     * @param M 起始位置
     * @param P 目标位置
     * @param K 走的步数
     * @return 方法数
     */
    public int walk(int N, int M, int P, int K) {
        return process(M, K, P, N);
    }

    /**
     * @param cur 当前所处位置
     * @param res 剩余步数
     * @param P   目标位置
     * @param N   总位置数
     */
    private int process(int cur, int res, int P, int N) {
        // 当前没有剩余步数，如果已经来到目标位置则代表为1次方法数
        if (res == 0) {
            return cur == P ? 1 : 0;
        }
        // 还有步数可走
        // 当前位置是1，则下一步一定往右
        if (cur == 1) {
            return process(2, res - 1, P, N);
        }
        // 当前位置是N，则下一步一定往左
        if (cur == N) {
            return process(N - 1, res - 1, P, N);
        }
        // 否则既可以向左也可以向右
        return process(cur - 1, res - 1, P, N) + process(cur + 1, res - 1, P, N);
    }


    /**
     * @param N 总位置数
     * @param M 起始位置
     * @param P 目标位置
     * @param K 走的步数
     * @return 方法数
     */
    public int walk1(int N, int M, int P, int K) {
        int[][] dp = new int[N + 1][K + 1];
        // dp表，默认值都是-1，代表没有计算过
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return process1(M, K, P, N, dp);
    }

    /**
     * @param cur 当前所处位置
     * @param res 剩余步数
     * @param P   目标位置
     * @param N   总位置数
     * @param dp  用来保存(cur，res)的结果
     */
    private int process1(int cur, int res, int P, int N, int[][] dp) {
        // 如果之前计算过(cur，res)这个组合则直接返回
        if (dp[cur][res] > -1) {
            return dp[cur][res];
        }
        int ans = 0;
        if (res == 0) {
            ans = cur == P ? 1 : 0;
        } else if (cur == 1) {
            ans = process1(2, res - 1, P, N, dp);
        } else if (cur == N) {
            ans = process1(N - 1, res - 1, P, N, dp);
        } else {
            ans = process1(cur - 1, res - 1, P, N, dp) + process1(cur + 1, res - 1, P, N, dp);
        }
        // 讲(cur，res)结果缓存
        dp[cur][res] = ans;
        return ans;
    }

    /**
     * dp表，行为当前位置 cur，列为剩余步数 res，按照暴力递归的方式填表
     * -------------------------------------------------
     * |     | (0) | (1) | (2) | (3) | (4) | (5) | (6) |
     * -------------------------------------------------
     * | (1) |  0  |  0  |  0  |  1  |  0  |  4  |  0  |
     * -------------------------------------------------
     * | (2) |  0  |  0  |  1  |  0  |  4  |  0  |  13 |
     * -------------------------------------------------
     * | (3) |  0  |  1  |  0  |  3  |  0  |  9  |  0  |
     * -------------------------------------------------
     * | (4) |  1  |  0  |  2  |  0  |  5  |  0  |  14 |
     * -------------------------------------------------
     * | (5) |  0  |  1  |  0  |  2  |  0  |  5  |  0  |
     * -------------------------------------------------
     * 1）base case 当剩余步数为0时，只有当前位置为4的时候才有结果，所以第一列只有(4，0) = 1，其余都为0
     * 2）当前位置为1时，只能向2方向走，所以(1，1) = (2，0) = 0，既(1,res) = (2,res-1)
     * 2）当前位置为5时，只能向4方向走，所以(5，1) = (4，0) = 0，既(5,res) = (4,res-1)
     * 2）当前位置为其他时，可以往两个方向走，所以(3，1) = (2，0) + (4，0)，既(i,res) = (i-1,res-1) + (i+1,res-1)
     */
    public int walk2(int N, int M, int P, int K) {
        int[][] dp = new int[N + 1][K + 1];
        dp[P][0] = 1;
        for (int col = 1; col < K + 1; col++) {
            dp[1][col] = dp[2][col - 1];
            for (int row = 2; row < N; row++) {
                dp[row][col] = dp[row + 1][col - 1] + dp[row - 1][col - 1];
            }
            dp[N][col] = dp[N - 1][col - 1];
        }
        return dp[M][K];
    }

    public static void main(String[] args) {
        System.out.println(new Code01_RobotWalk().walk(5, 2, 4, 6));
        System.out.println(new Code01_RobotWalk().walk1(5, 2, 4, 6));
        System.out.println(new Code01_RobotWalk().walk2(5, 2, 4, 6));
    }
}
