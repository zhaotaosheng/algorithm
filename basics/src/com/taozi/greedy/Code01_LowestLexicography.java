package com.taozi.greedy;

import java.util.Arrays;

/**
 * 给定一个由字符串组成的数组strs，必须把所有的字符串拼接起来，
 * 返回所有可能的拼接结果中，字典序最小的结果
 *
 * @author taosheng.zhao
 * @since 2020/12/22 12:47
 */
public class Code01_LowestLexicography {

    /**
     * 第一种贪法应该是如果a的字典序小于b的话那a在前，但是反例可举[b,ba]
     * 已知 b < ba，但是拼接出来的bba > bab，所以这种贪心失败
     * 在来贪，我们来保证a.b的字典序小于b.a的话那a在前，这样上述的反例就无效
     * 因为我们知道了bba > bab，所以得把ba放前，b在后
     *
     * @param strs
     * @return
     */
    public String getLowestLexicography(String... strs) {
        if (strs == null || strs.length == 0) return "";
        // 根据贪心条件将字符串数组排序
        Arrays.sort(strs, (a, b) -> (a + b).compareTo(b + a));
        String str = "";
        for (String s : strs) {
            str += s;
        }
        return str;
    }
}
