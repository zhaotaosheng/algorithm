package com.taozi.window;

import java.util.LinkedList;

/**
 * 给定一个整型数组arr，和一个整数num
 * 某个arr中的子数组sub，如果想达标，必须满足：
 * sub中最大值 – sub中最小值 <= num，
 * 返回arr中达标子数组的数量
 *
 * @author ZhaoTaoSheng
 * @since 2021/1/25 16:25
 */
public class Code02_AllLessNumSubArray {

    public static int count(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num < 0) return 0;
        // 窗口最大值更新结构，窗口最小值更新结构
        LinkedList<Integer> wMax = new LinkedList<>();
        LinkedList<Integer> wMin = new LinkedList<>();
        int ans = 0;
        int R = 0;
        for (int L = 0; L < arr.length; L++) {
            // L作为窗口左边界，一直找到窗口右边界R不满足题意
            while (R < arr.length) {
                // 维护窗口最大值更新结构
                while (!wMax.isEmpty() && arr[wMax.peekLast()] <= arr[R]) {
                    wMax.pollLast();
                }
                wMax.addLast(R);
                // 维护窗口最小值更新结构
                while (!wMin.isEmpty() && arr[wMin.peekLast()] >= arr[R]) {
                    wMin.pollLast();
                }
                wMin.addLast(R);
                // 如果在当前[L,R]的子数组满足题意，那么直接考察下一个右边界
                if (arr[wMax.peekFirst()] - arr[wMin.peekFirst()] <= num) {
                    R++;
                }
                // 否接结束，此时[L,R]位置不符合题意，[L,R)才符合
                else {
                    break;
                }
            }
            // [L,R)上的子数组满足题意，那么[L+1,R)、[L+2,R)...[R-1,R)上都符合，所以一共R-L个
            ans += R - L;
            // 因为窗口左边界要移动了，所以要考察下时候更新结构的头是否超出窗口的范围了
            if (wMax.peekFirst() == L) {
                wMax.pollFirst();
            }
            if (wMin.peekFirst() == L) {
                wMin.pollFirst();
            }
        }
        return ans;
    }

    // 暴力的对数器方法
    public static int right(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int N = arr.length;
        int count = 0;
        for (int L = 0; L < N; L++) {
            for (int R = L; R < N; R++) {
                int max = arr[L];
                int min = arr[L];
                for (int i = L + 1; i <= R; i++) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }
                if (max - min <= sum) {
                    count++;
                }
            }
        }
        return count;
    }

    // for test
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 200;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int sum = (int) (Math.random() * (maxValue + 1));
            int ans1 = right(arr, sum);
            int ans2 = count(arr, sum);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(sum);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");

    }
}
