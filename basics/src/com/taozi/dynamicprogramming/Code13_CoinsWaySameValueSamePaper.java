package com.taozi.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 认为值相同的货币没有任何不同，
 * 返回组成aim的方法数
 * 例如：arr = {1,2,1,1,2,1,2}，aim = 4
 * 方法：1+1+1+1、1+1+2、2+2
 * 一共就3种方法，所以返回3
 *
 * @author taosheng.zhao
 * @since 2021/1/14 14:21
 */
public class Code13_CoinsWaySameValueSamePaper {

    public int coinsWaySameValueSamePaper(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) return 0;
        Info info = getInfo(arr);
        return process(info.coins, info.numbers, 0, aim);
    }

    /**
     * 将货币数组去重，并整理一个对应的张数数组
     *
     * @param arr 原始货币数组
     * @return 货币、张数
     */
    private Info getInfo(int[] arr) {
        Map<Integer, Integer> info = new HashMap<>();
        for (int coin : arr) {
            if (info.containsKey(coin)) {
                info.put(coin, info.get(coin) + 1);
            } else {
                info.put(coin, 1);
            }
        }
        int[] coins = new int[info.size()];
        int[] numbers = new int[info.size()];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : info.entrySet()) {
            coins[index] = entry.getKey();
            numbers[index] = entry.getValue();
            index++;
        }
        return new Info(coins, numbers);
    }

    public static class Info {
        public int[] coins;
        public int[] numbers;

        public Info(int[] c, int[] z) {
            coins = c;
            numbers = z;
        }
    }

    /**
     * 在[cue，arr.length)范围上凑够remain钱数的方法
     *
     * @param coins   面值数组
     * @param numbers 张数数组
     * @param cur     当前面值
     * @param remain  剩余金钱
     * @return 方法数
     */
    private int process(int[] coins, int[] numbers, int cur, int remain) {
        if (cur == coins.length) return remain == 0 ? 1 : 0;
        if (remain == 0) return 1;
        int ways = 0;
        // 当前面值用0张的方法数 + 用1张的方法数 + 用2张的方法数....用numbers[cur]张的方法数
        for (int num = 0; num <= numbers[cur] && coins[cur] * num <= remain; num++) {
            ways += process(coins, numbers, cur + 1, remain - coins[cur] * num);
        }
        return ways;
    }


    public int coinsWaySameValueSamePaper1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) return 0;
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] numbers = info.numbers;
        int row = coins.length;
        int[][] dp = new int[row + 1][aim + 1];
        for (int i = 0; i < row + 1; i++) {
            dp[i][0] = 1;
        }
        for (int cur = row - 1; cur >= 0; cur--) {
            for (int remain = 0; remain < aim + 1; remain++) {
                int ways = 0;
                for (int num = 0; num <= numbers[cur] && coins[cur] * num <= remain; num++) {
                    ways += dp[cur + 1][remain - coins[cur] * num];
                }
                dp[cur][remain] = ways;
            }
        }
        return dp[0][aim];
    }

    public int coinsWaySameValueSamePaper2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) return 0;
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] numbers = info.numbers;
        int row = coins.length;
        int[][] dp = new int[row + 1][aim + 1];
        for (int i = 0; i < row + 1; i++) {
            dp[i][0] = 1;
        }
        for (int cur = row - 1; cur >= 0; cur--) {
            for (int remain = 0; remain < aim + 1; remain++) {
                // 根据表格可以看出，(cur,remain)依赖于cur+1行的remain、remain-x、remain-2x...remain-num*x
                // 我们还知道(cur,remain-x)依赖于cur+1行的remain-x、remain-2x、remain-3x...remain-(num+1)*x
                // 所以(cur,remain) = (cur+1,remain)+(cur,remain-1)-(cur+1,remain-(num+1)*x)
                dp[cur][remain] = dp[cur + 1][remain];
                if (remain - coins[cur] >= 0) {
                    dp[cur][remain] += dp[cur][remain - coins[cur]];
                    if (remain - coins[cur] * (numbers[cur] + 1) >= 0) {
                        dp[cur][remain] -= dp[cur + 1][remain - coins[cur] * (numbers[cur] + 1)];
                    }
                }
            }
        }
        return dp[0][aim];
    }
}
