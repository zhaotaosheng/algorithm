package com.taozi.dynamicprogramming;

/**
 * 请同学们自行搜索或者想象一个象棋的棋盘，
 * 然后把整个棋盘放入第一象限，棋盘的最左下角是(0,0)位置
 * 那么整个棋盘就是横坐标上9条线、纵坐标上10条线的区域
 * 给你三个 参数 x，y，k
 * 返回“马”从(0,0)位置出发，必须走k步
 * 最后落在(x,y)上的方法数有多少种?
 *
 * @author taosheng.zhao
 * @since 2021/1/12 12:48
 */
public class Code08_HorseJump {

    /**
     * 从(a,b)经过k步跳到(x,y)的方法数
     *
     * @param x 目标横坐标
     * @param y 目标纵坐标
     * @param k 步数
     * @return 方法数
     */
    public int jump(int x, int y, int k) {
        return process(0, 0, k, x, y, 8, 9);
    }

    /**
     * 从(a,b)经过k步跳到(x,y)的方法数
     *
     * @param a  起始点横坐标
     * @param b  起始点纵坐标
     * @param k  剩余的步数
     * @param x  终止点横坐标
     * @param y  起始点纵坐标
     * @param xL 棋盘的横轴长度
     * @param yL 棋盘的纵轴长度
     * @return 方法数
     */
    private int process(int a, int b, int k, int x, int y, int xL, int yL) {
        if (k == 0) {
            return a == x && b == y ? 1 : 0;
        }
        if (a < 0 || b < 0 || a > xL || b > yL) {
            return 0;
        }
        int ways = process(a + 1, b + 2, k - 1, x, y, xL, yL);
        ways += process(a + 2, b + 1, k - 1, x, y, xL, yL);
        ways += process(a + 2, b - 1, k - 1, x, y, xL, yL);
        ways += process(a + 1, b - 2, k - 1, x, y, xL, yL);
        ways += process(a - 1, b - 2, k - 1, x, y, xL, yL);
        ways += process(a - 2, b - 1, k - 1, x, y, xL, yL);
        ways += process(a - 2, b + 1, k - 1, x, y, xL, yL);
        ways += process(a - 1, b + 2, k - 1, x, y, xL, yL);
        return ways;
    }

    public int jump1(int x, int y, int k) {
        int[][][] dp = new int[9][10][k + 1];
        dp[x][y][0] = 1;
        for (int i = 1; i < k + 1; i++) {
            for (int a = 0; a < dp.length; a++) {
                for (int b = 0; b < dp[a].length; b++) {
                    int ways = pick(a + 1, b + 2, i - 1, 8, 9, dp);
                    ways += pick(a + 2, b + 1, i - 1, 8, 9, dp);
                    ways += pick(a + 2, b - 1, i - 1, 8, 9, dp);
                    ways += pick(a + 1, b - 2, i - 1, 8, 9, dp);
                    ways += pick(a - 1, b - 2, i - 1, 8, 9, dp);
                    ways += pick(a - 2, b - 1, i - 1, 8, 9, dp);
                    ways += pick(a - 2, b + 1, i - 1, 8, 9, dp);
                    ways += pick(a - 1, b + 2, i - 1, 8, 9, dp);
                    dp[a][b][i] = ways;
                }
            }
        }
        return dp[0][0][k];
    }

    private int pick(int a, int b, int k, int xL, int yL, int[][][] dp) {
        if (a < 0 || b < 0 || a > xL || b > yL) {
            return 0;
        }
        return dp[a][b][k];
    }
}
