package com.taozi.D03_MatrixMultiplication;

/**
 * 斐波那契数列矩阵乘法方式的实现
 *
 * @author taosheng.zhao
 * @since 2021/2/1 20:32
 */
public class Code01_FibonacciProblem {

    /**
     * f(n) = f(n-1) + f(n-2)，最高为n-2，所以base是2阶矩阵
     * |fn,fn-1| = |f2,f1| * base的n-2次方
     *
     * @param n 第n项
     * @return 第n项值
     */
    public static int fibonacci(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return 1;
        int[][] base = {{1, 1},
                        {1, 0}};
        int[][] res = matrixPower1(base, n - 2);
        return res[0][0] + res[1][0];
    }

    /**
     * 求base的power次的矩阵
     *
     * @param base  矩阵base
     * @param power 次方数
     * @return 矩阵
     */
    private static int[][] matrixPower1(int[][] base, int power) {
        int[][] res = new int[base.length][base[0].length];
        // 对角线为1的矩阵就是1
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        // 矩阵1次方
        int[][] t = base;
        // 假如power为80，2进制表示为0101 1010，如果某个位置为1，说明要计算该位置
        // base的80次方 = base的64次方 * base的16次方 * base的8次方 * base的2次方
        for (; power != 0; power >>= 1) {
            if ((power & 1) != 0) {
                // 将当前矩阵乘入结果中
                res = multiMatrix(res, t);
            }
            // 矩阵每次按照平方自增，t从最开始的1次方 -> 2 -> 4 -> 8 ...
            t = multiMatrix(t, t);
        }
        return res;
    }

    /**
     * 计算2 * 2矩阵乘积结果
     *
     * @param matrix1 矩阵1
     * @param matrix2 矩阵2
     * @return 矩阵乘积
     */
    private static int[][] multiMatrix(int[][] matrix1, int[][] matrix2) {
        int[][] res = new int[matrix1.length][matrix2[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                for (int k = 0; k < matrix2.length; k++) {
                    res[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return res;
    }
}
