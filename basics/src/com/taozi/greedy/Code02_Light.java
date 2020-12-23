package com.taozi.greedy;

/**
 * 给定一个字符串str，只由‘X’和‘.’两种字符构成。
 * ‘X’表示墙，不能放灯，也不需要点亮
 * ‘.’表示居民点，可以放灯，需要点亮
 * 如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮
 * 返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/23 14:25
 */
public class Code02_Light {

    /**
     * 让三个相邻的居民点公用中间点的灯
     *
     * @param str 数组
     * @return 灯的数量
     */
    public int getLightNum(String str) {
        int res = 0;
        char[] chars = str.toCharArray();
        int cur = 0;
        // 遍历数组
        while (cur < chars.length) {
            // 如果当前点是墙则跳过
            if (chars[cur] == 'X') {
                cur++;
            }
            // 如果没有下个位置则结束
            else if (cur + 1 == chars.length) {
                break;
            }
            // 有下一位置，说明至少需要一盏灯
            else {
                res++;
                // 如果下一位置是墙，则直接来到下下一个位置
                if (chars[cur + 1] == 'X') {
                    cur += 2;
                }
                // 如果下一位置不是墙，则直接来到下下下一个位置，cur ~ cur+2 这三个点用一个灯就够
                else {
                    cur += 3;
                }
            }
        }
        return res;
    }
}
