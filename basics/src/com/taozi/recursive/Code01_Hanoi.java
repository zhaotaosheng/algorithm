package com.taozi.recursive;

/**
 * 打印n层汉诺塔从最左边移动到最右边的全部过程
 *
 * @author taosheng.zhao
 * @since 2021/1/2 15:53
 */
public class Code01_Hanoi {

    /**
     * 将n层汉诺塔从左移到右
     *
     * @param n n层
     */
    public void hanoi(int n) {
        leftToRight(n);
    }

    /**
     * 将第n层从左移到右
     *
     * @param n n层
     */
    private void leftToRight(int n) {
        if (n == 1) {
            System.out.println("Move " + n + " left to right");
            return;
        }
        // 把n-1从左移到中
        leftToMiddle(n - 1);
        System.out.println("Move " + n + " left to right");
        // 把剩下的从中移到右
        middleToRight(n - 1);
    }

    private void middleToLeft(int n) {
        if (n == 1) {
            System.out.println("Move " + n + " middle to left");
            return;
        }
        middleToRight(n - 1);
        System.out.println("Move " + n + " middle to left");
        rightToLeft(n - 1);
    }

    private void rightToMiddle(int n) {
        if (n == 1) {
            System.out.println("Move " + n + " right to middle");
            return;
        }
        rightToLeft(n - 1);
        System.out.println("Move " + n + " right to middle");
        leftToMiddle(n - 1);
    }

    private void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("Move " + n + " right to left");
            return;
        }
        rightToMiddle(n - 1);
        System.out.println("Move " + n + " right to left");
        middleToLeft(n - 1);
    }

    private void middleToRight(int n) {
        if (n == 1) {
            System.out.println("Move " + n + " middle to right");
            return;
        }
        middleToLeft(n - 1);
        System.out.println("Move " + n + " middle to right");
        leftToRight(n - 1);
    }

    private void leftToMiddle(int n) {
        if (n == 1) {
            System.out.println("Move " + n + " left to middle");
            return;
        }
        leftToRight(n - 1);
        System.out.println("Move " + n + " left to middle");
        rightToMiddle(n - 1);
    }

    /**
     * 将n层汉诺塔从左移到右，改进方法
     *
     * @param n n层
     */
    public void hanoi1(int n) {
        process(n, "left", "right", "middle");
    }

    /**
     * 将第n层从from移到到to
     *
     * @param n    n层
     * @param from from位置
     * @param to   to位置
     * @param help 用来过度的位置
     */
    private void process(int n, String from, String to, String help) {
        if (n == 1) {
            System.out.println("move " + n + " " + from + " to " + to);
        } else {
            process(n - 1, from, help, to);
            System.out.println("move " + n + " " + from + " to " + to);
            process(n - 1, help, to, from);
        }
    }
}
