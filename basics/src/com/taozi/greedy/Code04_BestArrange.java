package com.taozi.greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。
 * 给你每一个项目开始的时间和结束的时间
 * 你来安排宣讲的日程，要求会议室进行的宣讲的场次最多。
 * 返回最多的宣讲场次
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/23 16:33
 */
public class Code04_BestArrange {
    // 项目
    private static class Project {
        public int start;
        public int end;

        public Project(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * 按照结束时间来安排项目宣讲
     *
     * @param projects 项目
     * @return 最大宣讲次数
     */
    public int getBestArrange(Project[] projects) {
        // 按照结束时间早晚排序
        PriorityQueue<Project> smallHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.end));
        // 其实可以用数组来实现
        // Arrays.sort(projects, Comparator.comparingInt(a -> a.end));
        smallHeap.addAll(Arrays.asList(projects));
        int res = 0;
        while (!smallHeap.isEmpty()) {
            // 我将会安排的项目宣讲
            Project cur = smallHeap.poll();
            // 直接移除掉开始时间在我当前会议结束时间前的项目宣讲
            while (smallHeap.peek() != null && smallHeap.peek().start < cur.end) {
                smallHeap.poll();
            }
            res++;
        }
        return res;
    }
}
