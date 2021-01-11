package com.taozi.dynamicprogramming;

/**
 * 给定一个字符串str，返回这个字符串的最长回文子序列长度
 * 比如 ： str = “a12b3c43def2ghi1kpm”
 * 最长回文子序列是“1234321”或者“123c321”，返回长度7
 *
 * @author ZhaoTaoSheng
 * @since 2021/1/11 20:12
 */
public class Code07_PalindromeSubSequence {

    /**
     * 最长回文子序列长度
     *
     * @param str 目标字符串
     * @return 最长回文子序列长度
     */
    public int palindromeSubSequence(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return process(0, str.length() - 1, str.toCharArray());
    }

    /**
     * str在[l，r]范围上的最大回文长度
     *
     * @param l   左边界
     * @param r   右边界
     * @param str 目标字符串
     * @return 最长回文子序列长度
     */
    private int process(int l, int r, char[] str) {
        // 1个字符本身就是回文，长度为1
        if (l == r) {
            return 1;
        }
        // 相邻的2个字符如果相等的话，就是2个回文长度，否则就是1个字符自身回文本身就是回文
        else if (l == r - 1) {
            return str[l] == str[r] ? 2 : 1;
        } else {
            // 不考虑l字符的最大回文子序列，r可要可不要
            int p1 = process(l + 1, r, str);
            // 不考虑r字符的最大回文子序列，l可要可不要
            int p2 = process(l, r - 1, str);
            int p3 = 0;
            // 如果l与r相等，那么这两点形成回文长度2
            if (str[l] == str[r]) {
                p3 = process(l + 1, r - 1, str) + 2;
            }
            return Math.max(p1, Math.max(p2, p3));
        }
    }

    public static int palindromeSubSequence1(String str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        int[][] dp = new int[length][length];
        dp[length - 1][length - 1] = 1;
        for (int l = 0; l < length - 1; l++) {
            dp[l][l] = 1;
            dp[l][l + 1] = chars[l] == chars[l + 1] ? 2 : 1;
        }
        for (int i = 2; i < length; i++) {
            int l = 0;
            int r = l + i;
            while (r < length) {
                int p1 = dp[l + 1][r];
                int p2 = dp[l][r - 1];
                int p3 = 0;
                if (chars[l] == chars[r]) {
                    p3 = dp[l + 1][r - 1] + 2;
                }
                dp[l][r] = Math.max(p1, Math.max(p2, p3));
                l++;
                r++;
            }
        }
        return dp[0][length - 1];
    }
}
