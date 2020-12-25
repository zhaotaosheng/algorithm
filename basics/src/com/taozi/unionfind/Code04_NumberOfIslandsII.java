package com.taozi.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 初始网格都为0，动态给出陆地位置1，判断实时岛屿数量
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/24 21:25
 */
public class Code04_NumberOfIslandsII {

    /**
     * 使用并查集的方式合并陆地
     *
     * @param m         网格
     * @param n         网格
     * @param positions 实时给出的陆地位置
     * @return 实时的岛屿数量
     */
    public List<Integer> numIslands21(int m, int n, int[][] positions) {
        UnionFind unionFind = new UnionFind(m, n);
        List<Integer> res = new ArrayList<>();
        for (int[] position : positions) {
            res.add(unionFind.add(position[0], position[1]));
        }
        return res;
    }

    // 基于数组实现的并查集
    private static class UnionFind {
        public int[] parents;
        public int[] sizes;
        public int[] help;
        public int row;
        public int col;
        public int sets;

        public UnionFind(int m, int n) {
            row = m;
            col = n;
            sets = 0;
            int size = row * col;
            // 初始化都是水，所以都为0
            parents = new int[size];
            sizes = new int[size];
            help = new int[size];
        }

        /**
         * 每次空降一个陆地试，将该位置与其上下左右的陆地合并
         *
         * @param r 空降纵坐标
         * @param c 空降横坐标
         * @return 当前岛屿数量
         */
        public Integer add(int r, int c) {
            int index = r * col + c;
            // 如果当前位置的数量不为0，说明之前空降过该点，不处理
            if (sizes[index] == 0) {
                // 初始化该位置
                parents[index] = index;
                sizes[index] = 1;
                sets++;
                // 尝试与其上下左右的陆地合并
                union(r - 1, c, r, c);
                union(r + 1, c, r, c);
                union(r, c - 1, r, c);
                union(r, c + 1, r, c);
            }
            // 返回岛屿数量
            return sets;
        }

        /**
         * 陆地合并
         *
         * @param r1 第一块陆地的纵坐标
         * @param c1 第一块陆地的横坐标
         * @param r2 第二块陆地的纵坐标
         * @param c2 第一块陆地的横坐标
         */
        private void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r1 == row || r2 < 0 || r2 == row
                    || c1 < 0 || c1 == col || c2 < 0 || c2 == col) return;
            int i = r1 * col + c1;
            int j = r2 * col + c2;
            // 如果这两块陆地中有一个没空降过陆地，那么直接返回
            if (sizes[i] == 0 || sizes[j] == 0) return;
            int root1 = findRoot(i);
            int root2 = findRoot(j);
            // 如果两块陆地不属于一个岛屿那么将其合并，并将岛屿数量减1
            if (root1 != root2) {
                if (sizes[root1] > sizes[root2]) {
                    sizes[root1] += sizes[root2];
                    parents[root2] = root1;
                } else {
                    sizes[root2] += sizes[root1];
                    parents[root1] = root2;
                }
                sets--;
            }
        }

        /**
         * 找到当前陆地所在岛屿的代表节点
         *
         * @param index 当前陆地坐标
         * @return 代表节点坐标
         */
        private int findRoot(int index) {
            int path = 0;
            while (index != parents[index]) {
                index = parents[index];
                help[path++] = index;
            }
            while (path != 0) {
                parents[help[--path]] = index;
            }
            return index;
        }
    }
}
