package com.taozi.dynamicprogramming;

/**
 * 给定3个参数，N，M，K
 * 怪兽有N滴血，等着英雄来砍自己
 * 英雄每一次打击，都会让怪兽流失[0~M]的血量
 * 到底流失多少？每一次在[0~M]上等概率的获得一个值
 * 求K次打击之后，英雄把怪兽砍死的概率
 *
 * @author taosheng.zhao
 * @since 2021/1/18 16:25
 */
public class Code17_KillMonster {

    public static double killMonster(int N, int M, int k) {
        if (N < 1 || M < 1 || k < 1) {
            return 0;
        }
        return kill(N, k, M) / Math.pow(M + 1, k);
    }

    /**
     * 把怪兽从当前血量砍死的方法数
     *
     * @param hp     当前怪兽还剩的血量
     * @param counts 当前还剩的刀数
     * @param M      每刀掉血的范围
     * @return 可以砍死怪兽的方法数
     */
    private static long kill(int hp, int counts, int M) {
        // 没刀砍了，怪兽死了就获取1点
        if (counts == 0) {
            return hp > 0 ? 0 : 1;
        }
        // 有刀砍，但是怪兽已经死了，那么后续所有砍法都应该搜集
        if (hp <= 0) {
            return (long) Math.pow(M + 1, counts);
        }
        int ans = 0;
        for (int i = 0; i <= M; i++) {
            ans += kill(hp - i, counts - 1, M);
        }
        return ans;
    }

    public static double dp1(int N, int M, int k) {
        if (N < 1 || M < 1 || k < 1) {
            return 0;
        }
        long[][] dp = new long[k + 1][N + 1];
        dp[0][0] = 1;
        for (int counts = 1; counts < k + 1; counts++) {
            dp[counts][0] = (long) Math.pow(M + 1, counts);
            for (int hp = 1; hp < N + 1; hp++) {
                int ans = 0;
                for (int i = 0; i <= M; i++) {
                    if (hp - i < 0) {
                        ans += (long) Math.pow(M + 1, counts - 1);
                    } else {
                        ans += dp[counts - 1][hp - i];
                    }
                }
                dp[counts][hp] = ans;
            }
        }
        return dp[k][N] / Math.pow(M + 1, k);
    }

    public static double dp2(int N, int M, int k) {
        if (N < 1 || M < 1 || k < 1) {
            return 0;
        }
        long[][] dp = new long[k + 1][N + 1];
        dp[0][0] = 1;
        for (int counts = 1; counts < k + 1; counts++) {
            dp[counts][0] = (long) Math.pow(M + 1, counts);
            for (int hp = 1; hp < N + 1; hp++) {
                dp[counts][hp] = dp[counts][hp - 1] + dp[counts - 1][hp];
                if (hp - 1 - M < 0) {
                    dp[counts][hp] -= Math.pow(M + 1, counts - 1);
                } else {
                    dp[counts][hp] -= dp[counts - 1][hp - 1 - M];
                }
            }
        }
        return dp[k][N] / Math.pow(M + 1, k);
    }
}
