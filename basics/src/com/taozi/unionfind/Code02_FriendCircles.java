package com.taozi.unionfind;

/**
 * 班上有N名学生。其中有些人是朋友，有些则不是。他们的友谊具有是传递性。如果已知A是B的朋友，B是C的朋友，
 * 那么我们可以认为A也是C的朋友。所谓的朋友圈，是指所有朋友的集合
 * <p>
 * 给定一个N * N的矩阵M，表示班级中学生之间的朋友关系。如果M[i][j] = 1，表示已知第 i 个和 j 个学生互为朋友关系，
 * 否则为不知道。你必须输出所有学生中的已知的朋友圈总数
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/24 19:04
 */
public class Code02_FriendCircles {

    /**
     * 每个人作为一个元素，使用并查集来合并认识的人
     *
     * @param M 关系网图
     * @return 朋友圈个数
     */
    public int findCircleNum(int[][] M) {
        // 成员个数
        int memberNum = M.length;
        UnionFind unionFind = new UnionFind(memberNum);
        // 遍历的是二维矩阵右上角的关系网，因为是对称的，所有遍历一侧即可
        for (int i = 0; i < M.length; i++) {
            for (int j = i + 1; j < M.length; j++) {
                // 如果i和j是朋友，那么将它们朋友圈合并
                if (M[i][j] == 1) {
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.sets;
    }

    // 数组实现的并查集
    private static class UnionFind {
        // 存储第i位成员的上级成员
        public int[] parents;
        // 存储代表节点集合的大小
        public int[] sizes;
        // 用来优化找代表节点时将沿路节点直接挂在代表节点下
        public int[] help;
        // 代表朋友圈的个数
        public int sets;

        /**
         * 通过成员数量初始化并查集
         *
         * @param memberNum 成员数量
         */
        public UnionFind(int memberNum) {
            this.parents = new int[memberNum];
            this.sizes = new int[memberNum];
            this.help = new int[memberNum];
            // 初始化朋友圈个数为成员个数
            this.sets = memberNum;
            for (int i = 0; i < parents.length; i++) {
                // 初始化各个成员的代表节点是自己
                parents[i] = i;
                // 初始化各个代表节点的集合也只有自己
                sizes[i] = 1;
            }
        }

        /**
         * 将i和j所在的集合合并
         *
         * @param i 成员i
         * @param j 成员j
         */
        public void union(int i, int j) {
            int iRoot = findRoot(i);
            int jRoot = findRoot(j);
            // 如果i和j的代表成员不是一个说明它们不在一个朋友圈内
            if (iRoot != jRoot) {
                // 将小朋友圈挂到大朋友圈下
                int bigSet = sizes[iRoot] > sizes[jRoot] ? iRoot : jRoot;
                int smallSet = bigSet == iRoot ? jRoot : iRoot;
                // 更新大朋友圈的成员数量
                sizes[bigSet] = sizes[bigSet] + sizes[smallSet];
                sizes[smallSet] = 0;
                // 更新小朋友圈代表成员的上一级成员
                parents[smallSet] = bigSet;
                // 合并一次朋友圈数量减少一个
                sets--;
            }
        }

        /**
         * 根据成员i找到它的代表成员
         *
         * @param i 成员i
         * @return 代表成员
         */
        public int findRoot(int i) {
            int path = 0;
            while (i != parents[i]) {
                i = parents[i];
                help[path++] = i;
            }
            // 将沿途的成员直接挂在代表成员下
            while (path != 0) {
                parents[help[--path]] = i;
            }
            return i;
        }
    }
}
