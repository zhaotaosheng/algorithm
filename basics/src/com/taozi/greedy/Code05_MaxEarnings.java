package com.taozi.greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 输入: 正数数组costs、正数数组profits、正数K、正数M
 * costs[i]表示i号项目的花费
 * profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
 * K表示你只能串行的最多做k个项目
 * M表示你初始的资金
 * 说明: 每做完一个项目，马上获得的收益，可以支持你去做下一个项目。不能并行的做项目。
 * 输出：你最后获得的最大钱数。
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/23 17:55
 */
public class Code05_MaxEarnings {
    // 每项项目的花费与利润
    private static class Project {
        public int cost;
        public int profit;

        public Project(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }
    }

    /**
     * 维护最小花费堆，最大收益堆，在资金允许的情况下，选择最大收益项目
     *
     * @param costs   项目花费
     * @param profits 项目收益
     * @param k       最多项目个数
     * @param m       初始资金
     * @return 最大钱数
     */
    public int getMaxEarnings(int[] costs, int[] profits, int k, int m) {
        Project[] infos = new Project[costs.length];
        for (int i = 0; i < costs.length; i++) {
            infos[i] = new Project(costs[i], profits[i]);
        }
        PriorityQueue<Project> costMinQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
        PriorityQueue<Project> profitMaxQueue = new PriorityQueue<>((a, b) -> b.profit - a.profit);
        costMinQueue.addAll(Arrays.asList(infos));
        for (int i = 0; i < k; i++) {
            // 如果最先花费堆顶的项目花费小于等于资金，表示我可以做它，将它压入最大收益堆
            while (costMinQueue.peek() != null && costMinQueue.peek().cost <= m) {
                profitMaxQueue.add(costMinQueue.poll());
            }
            // 最大收益堆顶就是代表我当前可做的收益最大的项目
            Project info = profitMaxQueue.poll();
            // 如果收益堆没有项目，说明我的资金无法在在做任何项目
            if (info == null) return m;
            m += info.profit;
        }
        return m;
    }
}
