package com.taozi.dynamicprogramming;

import java.util.Arrays;

/**
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线
 * 玩家A和玩家B依次拿走每张纸牌
 * 规定玩家A先拿，玩家B后拿
 * 但是每个玩家每次只能拿走最左或最右的纸牌
 * 玩家A和玩家B都绝顶聪明
 * 请返回最后获胜者的分数
 *
 * @author taosheng.zhao
 * @since 2021/1/6 12:51
 */
public class Code02_CardsInLine {

    /**
     * @param arr 纸牌
     * @return 获胜者分数
     */
    public int win(int[] arr) {
        int firstScore = first(arr, 0, arr.length - 1);
        int laterScore = later(arr, 0, arr.length - 1);
        return Math.max(firstScore, laterScore);
    }

    /**
     * 先手姿态拿牌
     *
     * @param arr   纸牌
     * @param left  纸牌左边界
     * @param right 纸牌右边界
     * @return 先手一定会按照最大分数的方式取
     */
    private int first(int[] arr, int left, int right) {
        // 如果就剩一张牌，那先手可以获得这个分数
        if (left == right) {
            return arr[left];
        }
        // 如果拿走最左边的牌，得到最左边的分数 + 以后手姿态从[left+1，right]获取的分数
        int takeLeft = arr[left] + later(arr, left + 1, right);
        // 如果拿走最右边的牌，得到最右边的分数 + 以后手姿态从[left，right-1]获取的分数
        int takeRight = arr[right] + later(arr, left, right - 1);
        // 先手按照最大分数拿牌
        return Math.max(takeLeft, takeRight);
    }

    /**
     * 后手姿态拿牌
     *
     * @param arr   纸牌
     * @param left  纸牌左边界
     * @param right 纸牌右边界
     * @return 先手一定会让后手拿到最小的分数
     */
    private int later(int[] arr, int left, int right) {
        // 如果就剩一张牌，先手拿走后，后手得不到分
        if (left == right) {
            return 0;
        }
        // 如果先手拿走最左边的牌，那么后手姿态从[left+1，right]范围上变成了先手
        int takeLeft = first(arr, left + 1, right);
        // 如果先手拿走最右边的牌，那么后手姿态从[left，right-1]范围上变成了先手
        int takeRight = first(arr, left, right - 1);
        // 先手一定会让后手拿最小的分数
        return Math.min(takeLeft, takeRight);
    }

    public int win1(int[] arr) {
        int[][] firstDp = new int[arr.length][arr.length];
        int[][] laterDp = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length - 1; i++) {
            Arrays.fill(firstDp[i], -1);
            Arrays.fill(laterDp[i], -1);
        }
        int firstScore = first1(arr, 0, arr.length - 1, firstDp, laterDp);
        int laterScore = later1(arr, 0, arr.length - 1, firstDp, laterDp);
        return Math.max(firstScore, laterScore);
    }

    private int first1(int[] arr, int left, int right, int[][] firstDp, int[][] laterDp) {
        if (firstDp[left][right] != -1) {
            return firstDp[left][right];
        }
        int ans;
        if (left == right) {
            ans = arr[left];
        } else {
            int takeLeft = arr[left] + later1(arr, left + 1, right, firstDp, laterDp);
            int takeRight = arr[right] + later1(arr, left, right - 1, firstDp, laterDp);
            ans = Math.max(takeLeft, takeRight);
        }
        // 将[left，right]范围内，先手的得分缓存
        firstDp[left][right] = ans;
        return ans;
    }

    private int later1(int[] arr, int left, int right, int[][] firstDp, int[][] laterDp) {
        if (laterDp[left][right] != -1) {
            return laterDp[left][right];
        }
        int ans;
        if (left == right) {
            ans = 0;
        } else {
            int takeLeft = first1(arr, left + 1, right, firstDp, laterDp);
            int takeRight = first1(arr, left, right - 1, firstDp, laterDp);
            ans = Math.min(takeLeft, takeRight);
        }
        // 将[left，right]范围内，后手的得分缓存
        laterDp[left][right] = ans;
        return ans;
    }

    public int win2(int[] arr) {
        int[][] firstDp = new int[arr.length][arr.length];
        int[][] laterDp = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length - 1; i++) {
            Arrays.fill(firstDp[i], -1);
            Arrays.fill(laterDp[i], -1);
        }
        int firstScore = first1(arr, 0, arr.length - 1, firstDp, laterDp);
        int laterScore = later1(arr, 0, arr.length - 1, firstDp, laterDp);
        return Math.max(firstScore, laterScore);
    }

    public static void main(String[] args) {
//        int[] arr = {5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7};
        int[] arr = {5, 7, 4, 0, 3, 4, 6, 1};
        System.out.println(new Code02_CardsInLine().win(arr));
        System.out.println(new Code02_CardsInLine().win1(arr));
    }
}
