package com.taozi.D05_Manacher;

/**
 * 假设字符串str长度为N，想返回最长回文子串的长度
 * 时间复杂度O(N)
 *
 * @author ZhaoTaoSheng
 * @since 2021/2/12 19:33
 */
public class Code01_Manacher {

    /**
     * 前置知识
     * 1.回文直径，回文半径，回文半径数组
     * 2.最右回文右边界
     * 3.最右回文右边界所对应的中心
     *
     * @param s 要求的字符串
     * @return 最大回文子串长度
     */
    public static int manacher(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        // "12132" -> "#1#2#1#3#2#"
        char[] str = manacherString(s);
        // 回文半径数组
        int[] pArr = new int[str.length];
        // 最右回文右边界的下一个位置
        int R = -1;
        // 最右回文右边界所对应的中心
        int C = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < str.length; i++) {
            // 1.i没被R罩住，只有暴力解
            // 2.i被R罩住，i关于C的对称点i`,R关于C的对称点L
            // 1）.当i`的回文区域在(L,R)范围内，则i的回文区域与i`一样大
            // 2）.当i`的回文区域在(L,R)范围外，则i的回文半径就是i~R-1
            // 3）.当i`的回文区域左侧与L相等，则i的回文半径至少和i`一样大，可以看看它是否能扩更远
            // i位置扩出来的答案，i位置扩的区域，至少是多大，也就是不用验证的区域
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
            // 继续往两边扩
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if (str[i + pArr[i]] == str[i - pArr[i]])
                    pArr[i]++;
                else {
                    break;
                }
            }
            // 如果把R推的更往右，则更新R和C
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            max = Math.max(max, pArr[i]);
        }
        return max - 1;
    }

    /**
     * 将数组填充#字符
     *
     * @param str 要填充的字符串
     * @return 填充完的数组
     */
    public static char[] manacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i != res.length; i++) {
            // 偶数位置就是#，奇数位置就是原数组元素
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }
}
