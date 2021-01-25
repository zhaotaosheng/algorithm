package com.taozi.window;

import java.util.LinkedList;

/**
 * 假设一个固定大小为 w 的窗口，依次划过arr，
 * 返回每一次滑出状况的最大值
 * 例如，arr = [4,3,5,4,3,3,6,7], W = 3
 * 返回：[5,5,5,4,6,7]
 *
 * @author ZhaoTaoSheng
 * @since 2021/1/25 15:37
 */
public class Code01_SlidingWindowMaxArray {

    public static int[] max(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) return null;
        int[] ans = new int[arr.length - w + 1];
        int pos = 0;
        // 窗口最大值更新结构
        LinkedList<Integer> window = new LinkedList<>();
        // 窗口右边界R一直向右滑动直到滑出数组
        for (int R = 0; R < arr.length; R++) {
            // 弹出队列尾部所有小于当前位置值的数，因为窗口已经包含比他们大的数值arr[R]了
            while (!window.isEmpty() && arr[window.peekLast()] <= arr[R]) {
                window.pollLast();
            }
            // 将该位置加入到窗口更新结构尾部
            window.addLast(R);
            // 如果当前窗口更新结构的头部过期了，那么就要给他剔除
            // 何时过期？窗口右边界 - 窗口大小 其实就是窗口左边界
            if (window.peekFirst() == R - w) {
                window.pollFirst();
            }
            // 前几位构不成窗口所有直接跳过，从w-1位置开始的数够一个窗口
            if (R >= w - 1) {
                ans[pos++] = arr[window.peekFirst()];
            }
        }
        return ans;
    }

    // 暴力的对数器方法
    public static int[] right(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int N = arr.length;
        int[] res = new int[N - w + 1];
        int index = 0;
        int L = 0;
        int R = w - 1;
        while (R < N) {
            int max = arr[L];
            for (int i = L + 1; i <= R; i++) {
                max = Math.max(max, arr[i]);

            }
            res[index++] = max;
            L++;
            R++;
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = max(arr, w);
            int[] ans2 = right(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
