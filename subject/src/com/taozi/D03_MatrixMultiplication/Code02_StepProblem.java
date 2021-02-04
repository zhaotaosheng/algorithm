package com.taozi.D03_MatrixMultiplication;

/**
 * 一个人可以一次往上迈1个台阶，也可以迈2个台阶
 * 返回这个人迈上N级台阶的方法数
 *
 * @author taosheng.zhao
 * @since 2021/2/4 13:22
 */
public class Code02_StepProblem {

    /**
     * f(n) = f(n-1) + f(n-2)，最高为n-2，所以base是2阶矩阵
     * f1 = 1、f2 = 2、f3 = 3、f4 = 5
     * |f3,f2| = |f2,f1| * base
     * |f4,f3| = |f3,f2| * base
     * 得出base = {{1，1},
     *             {1，0}}
     * |fn,fn-1| = |2,1| * base的n-2次方
     *
     * @param n 第n项
     * @return 第n项值
     */
    public static int step(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return n;
        int[][] base = {{1, 1},
                        {1, 0}};
        int[][] res = matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }

    private static int[][] matrixPower(int[][] base, int power) {
        int[][] res = new int[base.length][base[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        int[][] t = base;
        while (power != 0) {
            if ((power & 1) != 0) {
                res = multiMatrix(res, t);
            }
            t = multiMatrix(t, t);
            power >>= 1;
        }
        return res;
    }

    private static int[][] multiMatrix(int[][] m1, int[][] m2) {
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }
}
