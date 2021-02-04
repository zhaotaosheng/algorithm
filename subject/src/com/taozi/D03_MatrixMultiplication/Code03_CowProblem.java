package com.taozi.D03_MatrixMultiplication;

/**
 * 第一年农场有1只成熟的母牛A，往后的每年：
 * 1）每一只成熟的母牛都会生一只母牛
 * 2）每一只新出生的母牛都在出生的第三年成熟
 * 3）每一只母牛永远不会死
 * 返回N年后牛的数量
 *
 * @author taosheng.zhao
 * @since 2021/2/4 13:48
 */
public class Code03_CowProblem {


    /**
     * f(n) = f(n-1) + f(n-3)，最高为n-3，所以base是3阶矩阵
     * f1 = 1、f2 = 2、f3 = 3、f4 = 4、f5 = 6
     * |f4,f3,f2| = |f3,f2,f1| * base
     * |f5,f4,f3| = |f4,f3,f2| * base
     * 得出base = {{ 1, 1, 0 },
     *            { 0, 0, 1 },
     *             { 1, 0, 0 }
     * |fn,fn-1,fn-2| = |3,2,1| * base的n-3次方
     *
     * @param n 第n项
     * @return 第n项值
     */
    public static int cow(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        int[][] base = {
                { 1, 1, 0 },
                { 0, 0, 1 },
                { 1, 0, 0 } };
        int[][] res = matrixPower(base, n - 3);
        return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
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
