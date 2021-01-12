package com.taozi.dynamicprogramming;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 给定一个数组arr，arr[i]代表第i号咖啡机泡一杯咖啡的时间
 * 给定一个正数N，表示N个人等着咖啡机泡咖啡，每台咖啡机只能轮流泡咖啡
 * 只有一台咖啡机，一次只能洗一个杯子，时间耗费a，洗完才能洗下一杯
 * 每个咖啡杯也可以自己挥发干净，时间耗费b，咖啡杯可以并行挥发
 * 假设所有人拿到咖啡之后立刻喝干净，
 * 返回从开始等到所有咖啡机变干净的最短时间
 * 三个参数：int[] arr、int N，int a、int b
 *
 * @author taosheng.zhao
 * @since 2021/1/12 14:16
 */
public class Code09_Coffee {

    /**
     * 从开始等到所有杯子变干净的最短时间
     *
     * @param arr 咖啡机泡一杯咖啡的时间
     * @param N   人数
     * @param a   洗杯子的时间
     * @param b   挥发的时间
     * @return 全部洗干净的最好时间
     */
    public static int coffee(int[] arr, int N, int a, int b) {
        // 按照完成时间生成小根堆
        PriorityQueue<Machine> heap = new PriorityQueue<>(
                Comparator.comparingInt(machine -> machine.endTime + machine.needTime));
        for (int time : arr) {
            heap.add(new Machine(0, time));
        }
        // 每个人喝完咖啡的时间点，最优方案
        int[] endTime = new int[N];
        for (int i = 0; i < N; i++) {
            Machine machine = heap.poll();
            machine.endTime += machine.needTime;
            endTime[i] = machine.endTime;
            heap.add(machine);
        }
        return process(0, 0, endTime, a, b);
    }

    /**
     * 在[cur,cap.length)范围返回最好的时间
     *
     * @param cur  当前做觉得的杯子
     * @param free 机器可以使用的时间点
     * @param cap  等待洗的杯子
     * @param a    机器洗杯子的时间
     * @param b    杯子自己挥发的时间
     * @return 全部洗干净的最好时间
     */
    private static int process(int cur, int free, int[] cap, int a, int b) {
        if (cur == cap.length) return 0;
        // 当前杯子决定去机器上洗
        int selfClean1 = Math.max(free + a, cap[cur] + a);
        int restClean1 = process(cur + 1, selfClean1, cap, a, b);
        int p1 = Math.max(selfClean1, restClean1);
        // 当前杯子决定去自然风干
        int selfClean2 = cap[cur] + b;
        int restClean2 = process(cur + 1, free, cap, a, b);
        int p2 = Math.max(selfClean2, restClean2);
        return Math.min(p1, p2);
    }

    // 每台机器的信息
    private static class Machine {
        // 上一位人的结束时间
        public int endTime;
        // 机器工作需要的时间
        public int needTime;

        public Machine(int endTime, int needTime) {
            this.endTime = endTime;
            this.needTime = needTime;
        }
    }

    public static int coffee1(int[] arr, int N, int a, int b) {
        PriorityQueue<Machine> heap = new PriorityQueue<>(
                Comparator.comparingInt(machine -> machine.endTime + machine.needTime));
        for (int time : arr) {
            heap.add(new Machine(0, time));
        }
        int[] endTime = new int[N];
        for (int i = 0; i < N; i++) {
            Machine machine = heap.poll();
            machine.endTime += machine.needTime;
            endTime[i] = machine.endTime;
            heap.add(machine);
        }
        // free的变化范围看不出来，假设全部都去洗取最大值
        int maxFreeTime = 0;
        for (int i : endTime) {
            maxFreeTime = Math.max(maxFreeTime + a, i + a);
        }
        int[][] dp = new int[N + 1][maxFreeTime + 1];
        for (int cur = N - 1; cur >= 0; cur--) {
            for (int free = 0; free < maxFreeTime + 1; free++) {
                int selfClean1 = Math.max(free + a, endTime[cur] + a);
                if (selfClean1 > maxFreeTime) break;
                int restClean1 = dp[cur + 1][selfClean1];
                int p1 = Math.max(selfClean1, restClean1);
                int selfClean2 = endTime[cur] + b;
                int restClean2 = dp[cur + 1][free];
                int p2 = Math.max(selfClean2, restClean2);
                dp[cur][free] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }
}
