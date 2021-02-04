package com.taozi.D01_WindowUpdateStructure;

import java.util.LinkedList;

/**
 * 加油站的良好出发点问题
 * gas  [2,1,5,3,2,1,5,4,1] 表示加油站有多少油
 * cost [3,1,4,2,1,5,2,3,1] 表示开到下一个加油站消耗多少油
 * 从某一位置出发绕一圈回到该位置既为良好出发点
 * 求第一个良好出发点的位置
 *
 * @author ZhaoTaoSheng
 * @since 2021/1/25 18:18
 */
public class Code03_GasStation {

    public static int fineLocation(int[] gas, int[] cost) {
        if (gas == null || gas.length == 0 || cost == null || cost.length == 0
                || gas.length != cost.length) return 0;
        int num = gas.length;
        // 每个加油站的净剩，比如netProfit[0]表示0号加油站出发开到1号加油站时剩下的油
        int[] netProfit = new int[num << 1];
        for (int i = 0; i < num; i++) {
            netProfit[i] = gas[i] - cost[i];
            netProfit[i + num] = gas[i] - cost[i];
        }
        // 净剩的前缀和，netProfit[i]表示从0号加油站开到i+1号加油站时剩下的油
        for (int i = 1; i < netProfit.length; i++) {
            netProfit[i] += netProfit[i - 1];
        }
        // 窗口最小值更新结构
        LinkedList<Integer> wMin = new LinkedList<>();
        // 将0号加油站出发的方案准备好
        for (int L = 0; L < num; L++) {
            while (!wMin.isEmpty() && netProfit[wMin.peekLast()] >= netProfit[L]) {
                wMin.pollLast();
            }
            wMin.addLast(L);
        }
        // offset表示L - 1的前缀和，L - offset表示实际L号邮箱出发开到下一号邮箱时剩下的油耗
        for (int offset = 0, L = 0, R = num; L < num; offset = netProfit[L], L++, R++) {
            // L号加油站出发，是否为良好出发点？
            // 由于netProfit为油量前缀和数组，那么[L,R)的每个数 - (L-1)前缀和既为到达每个加油站的剩油量
            if (netProfit[wMin.peekFirst()] - offset >= 0) {
                return L;
            }
            // 构建(L,R]的窗口，先把右边界扩出去
            while (!wMin.isEmpty() && netProfit[wMin.peekLast()] >= netProfit[R]) {
                wMin.pollLast();
            }
            wMin.addLast(R);
            // 再考虑左边界是否要缩回来
            if (wMin.peekFirst() == L) {
                wMin.pollFirst();
            }
        }
        return -1;
    }
}
