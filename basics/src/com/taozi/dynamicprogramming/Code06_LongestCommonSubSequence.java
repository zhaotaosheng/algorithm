package com.taozi.dynamicprogramming;

/**
 * 给定两个字符串str1和str2，
 * 返回这两个字符串的最长公共子序列长度
 * <p>
 * 比如 ： str1 = “a12b3c456d”,str2 = “1ef23ghi4j56k”
 * 最长公共子序列是“123456”，所以返回长度6
 *
 * @author ZhaoTaoSheng
 * @since 2021/1/11 19:30
 */
public class Code06_LongestCommonSubSequence {

    /**
     * 两个字符串的最长公共子序列长度
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 最长公共子序列长度
     */
    public int longestCommonSubSequence(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        return process(0, 0, str1.toCharArray(), str2.toCharArray());
    }

    /**
     * str1在[cur1，str1.length]与str2在[cur2，str2.length]上最大子序列长度
     *
     * @param cur1 当前str1考虑的位置
     * @param cur2 当前str2考虑的位置
     * @param str1 给定str1
     * @param str2 给定str2
     * @return 最长公共子序列长度
     */
    private int process(int cur1, int cur2, char[] str1, char[] str2) {
        if (cur1 == str1.length) return 0;
        if (cur2 == str2.length) return 0;
        // 不要cur1字符的最大长度，cur2可要可不要
        int p1 = process(cur1 + 1, cur2, str1, str2);
        // 不要cur2字符的最大长度，cur1可要可不要
        int p2 = process(cur1, cur2 + 1, str1, str2);
        int p3 = 0;
        if (str1[cur1] == str2[cur2]) {
            p3 = process(cur1 + 1, cur2 + 1, str1, str2) + 1;
        }
        return Math.max(p1, Math.max(p2, p3));
    }

    public int longestCommonSubSequence1(String str1, String str2) {
        int length1;
        int length2;
        if (str1 == null || str2 == null
                || (length1 = str1.length()) == 0 || (length2 = str2.length()) == 0) {
            return 0;
        }
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        int[][] dp = new int[length1 + 1][length2 + 1];
        for (int cur1 = length1 - 1; cur1 >= 0; cur1--) {
            for (int cur2 = length2 - 1; cur2 >= 0; cur2--) {
                int p1 = dp[cur1 + 1][cur2];
                int p2 = dp[cur1][cur2 + 1];
                int p3 = 0;
                if (chars1[cur1] == chars2[cur2]) {
                    p3 = dp[cur1 + 1][cur2 + 1] + 1;
                }
                dp[cur1][cur2] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[0][0];
    }
}
