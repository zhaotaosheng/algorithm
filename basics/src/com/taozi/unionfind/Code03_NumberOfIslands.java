package com.taozi.unionfind;

/**
 * 给定一个m x n的2d网格图，图中有“1”(陆地)和“0”(水)，返回岛屿的数量。
 * 岛屿被水包围，通过水平或垂直连接相邻的土地而形成。
 * 你可以假设网格的所有四个边缘都被水包围。
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/24 19:51
 */
public class Code03_NumberOfIslands {

    /**
     * 暴力递归获取岛屿数量
     *
     * @param grid 网格
     * @return 岛屿数量
     */
    public int numIslands(char[][] grid) {
        int num = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // 如果某个网格是陆地，那么将它变为一个新字符
                // 并传播给它周围相邻的陆地也变成一个新字符
                if (grid[i][j] == '1') {
                    diffuse(grid, i, j);
                    num++;
                }
            }
        }
        return num;
    }

    /**
     * 当一个网格为陆地时，传播至周围相邻的陆地
     *
     * @param grid 网格
     * @param i    位置
     * @param j    位置
     */
    private void diffuse(char[][] grid, int i, int j) {
        // 如果超出网格位置或者不是陆地则直接返回
        if (i < 0 || j < 0 || i == grid.length || j == grid[0].length) return;
        if (grid[i][j] != '1') return;
        // 将当前陆地更换一个字符，防止后续重复遍历
        grid[i][j] = '2';
        // 传播至上边网格
        diffuse(grid, i - 1, j);
        // 传播至下边网格
        diffuse(grid, i + 1, j);
        // 传播至左边网格
        diffuse(grid, i, j - 1);
        // 传播至右边网格
        diffuse(grid, i, j + 1);
    }

    /**
     * 将每一块陆地看成是并查集的元素，然后一路向右下合并
     *
     * @param grid 网格
     * @return 岛屿数量
     */
    public int numIslands1(char[][] grid) {
        UnionFind unionFind = new UnionFind(grid);
        for (int i = 0; i < unionFind.row; i++) {
            for (int j = 0; j < unionFind.col; j++) {
                // 每块陆地尝试与自己右下两块陆地合并
                if (grid[i][j] == '1') {
                    unionFind.union(grid, i, j, i, j + 1);
                    unionFind.union(grid, i, j, i + 1, j);
                }
            }
        }
        return unionFind.sets;
    }

    // 数组实现的并查集
    private static class UnionFind {
        // 存储第i位陆地的父
        public int[] parents;
        // 存储第i位岛屿的大小
        public int[] sizes;
        // 用来优化找根时将沿途陆地直接挂在根下
        public int[] help;
        public int row;
        public int col;
        // 岛屿数量
        public int sets;

        public UnionFind(char[][] grid) {
            row = grid.length;
            col = grid[0].length;
            sets = 0;
            parents = new int[row * col];
            sizes = new int[row * col];
            help = new int[row * col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (grid[i][j] == '1') {
                        // 根据二位数组位置转换一维数组位置
                        int index = i * col + j;
                        // 初始化陆地的父是自己
                        parents[index] = index;
                        // 初始化每块陆地都是岛屿，且大小为1
                        sizes[index] = 1;
                        // 所有的岛屿
                        sets++;
                    }
                }
            }
        }

        /**
         * 将当前陆地与右、下两块陆地合并
         *
         * @param grid  网格
         * @param i     当前陆地坐标
         * @param j     当前陆地坐标
         * @param nextI 下一个陆地坐标
         * @param nextJ 下一个陆地坐标
         */
        public void union(char[][] grid, int i, int j, int nextI, int nextJ) {
            // i，j一定不越界，nextI，nextJ需要边界检查
            if (nextI < 0 || nextJ < 0 || nextI == grid.length || nextJ == grid[0].length) return;
            if (grid[nextI][nextJ] != '1') return;
            // 找到当前陆地的根
            int root = findRoot(i * col + j);
            // 找到要合并陆地的根
            int nextRoot = findRoot(nextI * col + nextJ);
            if (root != nextRoot) {
                int big = sizes[root] > sizes[nextRoot] ? root : nextRoot;
                int small = big == root ? nextRoot : root;
                sizes[big] += sizes[small];
                sizes[small] = 0;
                parents[small] = big;
                sets--;
            }
        }

        /**
         * 根据陆地位置找到根
         *
         * @param index 位置
         * @return 根
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
