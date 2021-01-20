package com.taozi.dynamicprogramming;

/**
 * 给定一个正数n，求n的裂开方法数，
 * 规定：后面的数不能比前面的数小
 * 比如4的裂开方法有：
 * 1+1+1+1、1+1+2、1+3、2+2、4
 * 5种，所以返回5
 *
 * @author ZhaoTaoSheng
 * @since 2021/1/20 20:08
 */
public class Code18_SplitNumber {

    /**
     * 将n分解的方法数
     *
     * @param n 要分解的数
     * @return 方法数
     */
    public int splitNumber(int n) {
        return process(1, n);
    }

    /**
     * 把remain的数分解掉的方法数
     *
     * @param prev   上一个考虑的数
     * @param remain 剩余的数
     * @return 方法数
     */
    private int process(int prev, int remain) {
        // 数被分解掉了，表示有一种方法
        if (remain == 0) return 1;
        // 如果剩余数小于上一个数，因为有规定后边的数不能比前边小所有就没有方法
        if (prev > remain) return 0;
        // 如果剩余数与上一个数相等，那就代表一种方法，比如4 = 2+2
        if (prev == remain) return 1;
        int ans = 0;
        // 依次考虑prev...remain各个数的情况
        for (int cur = prev; cur <= remain; cur++) {
            ans += process(cur, remain - cur);
        }
        return ans;
    }

    public int dp1(int n) {
        int[][] dp = new int[n + 1][n + 1];
        for (int prev = 1; prev < n + 1; prev++) {
            dp[prev][0] = 1;
            dp[prev][prev] = 1;
        }
        for (int prev = n - 1; prev > 0; prev--) {
            for (int remain = prev + 1; remain < n + 1; remain++) {
                int ans = 0;
                for (int cur = prev; cur <= remain; cur++) {
                    ans += dp[cur][remain - cur];
                }
                dp[prev][remain] = ans;
            }
        }
        return dp[1][n];
    }

    public int dp2(int n) {
        int[][] dp = new int[n + 1][n + 1];
        for (int prev = 1; prev < n + 1; prev++) {
            dp[prev][0] = 1;
            dp[prev][prev] = 1;
        }
        for (int prev = n - 1; prev > 0; prev--) {
            for (int remain = prev + 1; remain < n + 1; remain++) {
                dp[prev][remain] = dp[prev + 1][remain] + dp[prev][remain - prev];
            }
        }
        return dp[1][n];
    }
}
