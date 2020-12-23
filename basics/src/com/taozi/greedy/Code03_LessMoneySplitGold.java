package com.taozi.greedy;

import java.util.PriorityQueue;

/**
 * 一块金条切成两半，是需要花费和长度数值一样的铜板的。
 * 比如长度为20的金条，不管怎么切，都要花费20个铜板。 一群人想整分整块金条，怎么分最省铜板?
 * <p>
 * 例如,给定数组{10,20,30}，代表一共三个人，整块金条长度为60，金条要分成10，20，30三个部分。
 * <p>
 * 如果先把长度60的金条分成10和50，花费60; 再把长度50的金条分成20和30，花费50;一共花费110铜板。
 * 但如果先把长度60的金条分成30和30，花费60;再把长度30金条分成10和20， 花费30;一共花费90铜板。
 * 输入一个数组，返回分割的最小代价。
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/23 14:48
 */
public class Code03_LessMoneySplitGold {

    /**
     * 从最小的两份开始往上合并，将合并的结果也要考虑进去
     *
     * @param lengths 金条长度
     * @return 最小花费
     */
    public int lessMoney(int[] lengths) {
        int res = 0;
        // 小根堆，维护当前最小的长度
        PriorityQueue<Integer> smallHeap = new PriorityQueue<>();
        for (int length : lengths) {
            smallHeap.add(length);
        }
        while (smallHeap.size() > 1) {
            // 将当前金条最小的两个长度合并，并重新加入小根堆中
            Integer x = smallHeap.poll();
            Integer y = smallHeap.poll();
            res += x + y;
            smallHeap.add(x + y);
        }
        return res;
    }
}
