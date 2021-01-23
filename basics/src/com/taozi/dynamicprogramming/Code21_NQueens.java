package com.taozi.dynamicprogramming;

/**
 * N皇后问题是指在N*N的棋盘上要摆N个皇后，
 * 要求任何两个皇后不同行、不同列， 也不在同一条斜线上
 * <p>
 * 给定一个整数n，返回n皇后的摆法有多少种。  n=1，返回1
 * n=2或3，2皇后和3皇后问题无论怎么摆都不行，返回0
 * n=8，返回92
 *
 * @author ZhaoTaoSheng
 * @since 2021/1/23 20:45
 */
public class Code21_NQueens {

    public static int ways(int n) {
        if (n < 0) return 0;
        return process(new int[n], 0, n);
    }

    /**
     * cur之前的皇后都已经摆好了，从第cur个皇后开始摆，全摆好的方法数
     *
     * @param allPos cur之前所有皇后的位置，index表示第几个皇后，value表示其所在列
     * @param cur    当前要考虑的皇后
     * @param n      皇后的个数
     * @return 可以摆好的方法数
     */
    private static int process(int[] allPos, int cur, int n) {
        // 所有皇后都摆好了，那就就代表是一种方法
        if (cur == n) return 1;
        int ways = 0;
        // 当前cur皇后放的列位置，从第0列一直尝试到n-1列
        for (int pos = 0; pos < n; pos++) {
            allPos[cur] = pos;
            // 如果当前摆的列有效，那么去摆下一个皇后，如果无效就跳过
            if (isEffectivePosition(allPos, cur, pos)) {
                ways += process(allPos, cur + 1, n);
            }
        }
        return ways;
    }

    /**
     * 当前位置是否是一个有效的位置
     *
     * @param allPos cur之前所有皇后的位置
     * @param cur    当前要考虑的皇后
     * @param pos    当前皇后要摆放的位置
     * @return 是否可以摆放
     */
    private static boolean isEffectivePosition(int[] allPos, int cur, int pos) {
        boolean isEffective = true;
        // 从第0个皇后开始遍历看是否有冲突
        for (int i = 0; i < cur; i++) {
//            // pos i皇后与其同列
//            if (allPos[i] == pos) {
//                isEffective = false;
//                break;
//            }
//            // pos - (cur - i) i皇后在其左上成对角的位置
//            if (allPos[i] == pos - (cur - i)) {
//                isEffective = false;
//                break;
//            }
//            // pos + (cur - i) i皇后在其右上成对角的位置
//            if (allPos[i] == pos + (cur - i)) {
//                isEffective = false;
//                break;
//            }
            if (allPos[i] == pos || Math.abs(cur - i) == Math.abs(pos - allPos[i])) {
                isEffective = false;
                break;
            }
        }
        return isEffective;
    }

    public static void main(String[] args) {
        int n = 8;

        long start = System.currentTimeMillis();
        System.out.println(num2(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(ways(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

    }

    // 请不要超过32皇后问题
    public static int num2(int n) {
        if (n < 1 || n > 32) {
            return 0;
        }
        // 如果你是13皇后问题，limit 最右13个1，其他都是0
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return process2(limit, 0, 0, 0);
    }

    // 7皇后问题
    // limit : 0....0 1 1 1 1 1 1 1
    // 之前皇后的列影响：colLim
    // 之前皇后的左下对角线影响：leftDiaLim
    // 之前皇后的右下对角线影响：rightDiaLim
    public static int process2(int limit, int colLim, int leftDiaLim, int rightDiaLim) {
        if (colLim == limit) {
            return 1;
        }
        // pos中所有是1的位置，是你可以去尝试皇后的位置
        int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
        int mostRightOne = 0;
        int res = 0;
        while (pos != 0) {
            mostRightOne = pos & (~pos + 1);
            pos = pos - mostRightOne;
            res += process2(limit, colLim | mostRightOne, (leftDiaLim | mostRightOne) << 1,
                    (rightDiaLim | mostRightOne) >>> 1);
        }
        return res;
    }
}
