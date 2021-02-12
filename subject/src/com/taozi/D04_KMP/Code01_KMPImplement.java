package com.taozi.D04_KMP;

/**
 * 假设字符串str长度为N，字符串match长度为M，M <= N
 * 想确定str中是否有某个子串是等于match的。
 * 时间复杂度O(N)
 *
 * @author taosheng.zhao
 * @since 2021/2/7 9:55
 */
public class Code01_KMPImplement {

    public static int kmp(String str, String match) {
        if (str == null || match == null || str.length() < 1 || str.length() < match.length()) {
            return -1;
        }
        char[] str1 = str.toCharArray();
        char[] str2 = match.toCharArray();
        int[] nextArray = getNextArray(str2);
        // 挨个尝试str1中第pos1位置开头的组合
        int pos1 = 0;
        // 匹配到str2的第字符串pos2位置，既pos2之前的字符都已经匹配上了
        int pos2 = 0;
        // 2种可能跳出循环的情况
        // 1.pos1走到底也没有匹配出str2
        // 2.pos2走到底了，说明匹配出str2了
        while (pos1 < str1.length && pos2 < str2.length) {
            // 2个位置字符相同，则比较下一个位置字符
            if (str1[pos1] == str2[pos2]) {
                pos1++;
                pos2++;
            }
            // 2个位置字符不相同，则移动pos2到该位置的next数组中的位置继续比较
            else if (pos2 != 0) {
                pos2 = nextArray[pos2];
            }
            // pos2一直比较到0位置都不同，那么就移动pos1到下一个位置来尝试
            else {
                pos1++;
            }
        }
        // 如果是pos2走到底了，那么就代表匹配出str2了，起始字符就是pos1 - pos2
        return pos2 == str2.length ? pos1 - pos2 : -1;
    }

    /**
     * next数组，表示当前位置的之前字符串中具有前缀字符串和后缀字符串相等的最大长度
     * 不包含当前位置，且前缀字符串与后缀字符串长度包含所有字符串
     * 假设str:[a,b,c,a,b,c,a]
     * 那next:[-1,0,0,0,1,2,3]
     *
     * @param str 要求next数组的字符串
     * @return next数组
     */
    public static int[] getNextArray(char[] str) {
        if (str.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[str.length];
        next[0] = -1;
        next[1] = 0;
        // 当前要求next数组的位置
        int i = 2;
        // 与i-1要对比的位置，因为i=2开始，所以最开始对比的应该是1和0
        // 也代表某一位next数组对应的下一个位置
        int cn = 0;
        while (i < str.length) {
            // 如果中了这个分支，说明0~cn就是i位置要求的答案
            if (str[i - 1] == str[cn]) {
                next[i] = cn + 1;
                i++;
                cn++;
            }
            // 否则往前找cn，维持cn代表某个相等的前后缀长度，可以保证这个前缀与i位置等长后缀相等
            else if (cn > 0) {
                cn = next[cn];
            }
            // 找到0位置都不相等那么就来到下一位置吧
            else {
                next[i] = 0;
                i++;
            }
        }
        return next;
    }
}
