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
        int[] nextArray = nextArray(str2);
        int pos1 = 0;
        int pos2 = 0;
        while (pos1 < str1.length) {
            if (pos2 == str2.length) return pos1 - pos2;
            if (str1[pos1] == str2[pos2]) {
                pos1++;
                pos2++;
            } else if (pos2 != 0) {
                pos2 = nextArray[pos2];
            } else {
                pos1++;
            }
        }
        return pos2 == str2.length ? pos1 - pos2 : -1;
    }

    public static int[] nextArray(char[] str) {
        if (str.length == 1) {
            return new int[]{-1};
        }
        int[] nextArray = new int[str.length];
        nextArray[0] = -1;
        nextArray[1] = 0;
        for (int i = 2; i < str.length; i++) {
            int res = 0;
            nextArray[i] = res;
        }
        return nextArray;
    }

    public static int getIndexOf(String s, String m) {
        if (s == null || m == null || m.length() < 1 || s.length() < m.length()) {
            return -1;
        }
        char[] str = s.toCharArray();
        char[] match = m.toCharArray();
        int x = 0;
        int y = 0;
        int[] next = getNextArray(match);
        while (x < str.length && y < match.length) {
            if (str[x] == match[y]) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }
        return y == match.length ? x - y : -1;
    }

    public static int[] getNextArray(char[] match) {
        if (match.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[match.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0;
        while (i < next.length) {
            if (match[i - 1] == match[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 10000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            if (kmp(str, match) != str.indexOf(match)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
