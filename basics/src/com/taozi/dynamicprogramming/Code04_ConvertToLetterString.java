package com.taozi.dynamicprogramming;

/**
 * 规定1和A对应、2和B对应、3和C对应...
 * 那么一个数字字符串比如"111”就可以转化为:
 * "AAA"、"KA"和"AK"
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 *
 * @author ZhaoTaoSheng
 * @since 2021/1/11 16:07
 */
public class Code04_ConvertToLetterString {

    /**
     * 将数字转化成字符串的方法
     *
     * @param str 给定字符串
     * @return 转化方法数
     */
    public int convertToLetterString(String str) {
        return process(0, str.toCharArray());
    }

    /**
     * 考虑[cur，str.length]位置，有多少种转化方法
     *
     * @param cur 当前考虑的位置
     * @param str 给定字符串
     * @return 转化方法数
     */
    private int process(int cur, char[] str) {
        // 所有字符考量完后，代表1种结果
        if (cur == str.length) return 1;
        // 当单蹦出现个'0'代表该结果无效
        if (str[cur] == '0') return 0;
        // 自己单独转换成一个字母后，后续字符有多少种转换方法
        int p1 = process(cur + 1, str);
        // 自己与下一位转换成一个字母，后续字符有多少种转换方法
        int p2 = 0;
        // 前提是保证两个数字合起来得是小于27的才是有效字符
        if (cur + 1 != str.length && (str[cur] - 'a') * 10 + str[cur + 1] < 27) {
            p2 = process(cur + 2, str);
        }
        return p1 + p2;
    }

    /**
     * 将数字转化成字符串的方法
     *
     * @param str 给定字符串
     * @return 转化方法数
     */
    public int convertToLetterString1(String str) {
        char[] chars = str.toCharArray();
        int length = str.length();
        int[] dp = new int[length + 1];
        dp[length] = 1;
        for (int cur = length - 1; cur >= 0; cur--) {
            if (chars[cur] != '0') {
                int p1 = dp[cur + 1];
                int p2 = 0;
                if (cur + 1 != chars.length && (chars[cur] - 'a') * 10 + chars[cur + 1] < 27) {
                    p2 = dp[cur + 2];
                }
                dp[cur] = p1 + p2;
            }
        }
        return dp[0];
    }
}
