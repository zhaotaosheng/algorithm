package com.taozi.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ZhaoTaoSheng
 * @since 2020/12/24 21:25
 */
public class Code04_NumberOfIslandsII {

    public List<Integer> numIslands21(int m, int n, int[][] positions) {
        UnionFind unionFind = new UnionFind(m, n);
        List<Integer> res = new ArrayList<>();
        for (int[] position : positions) {
            res.add(unionFind.add(position[0], position[1]));
        }
        return res;
    }

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
            parents = new int[size];
            sizes = new int[size];
            help = new int[size];
        }

        public Integer add(int r, int c) {
            int index = r * col + c;
            if (sizes[index] == 0) {
                parents[index] = index;
                sizes[index] = 1;
                sets++;
                union(r - 1, c, r, c);
                union(r + 1, c, r, c);
                union(r, c - 1, r, c);
                union(r, c + 1, r, c);
            }
            return sets;
        }

        private void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r1 == row || r2 < 0 || r2 == row
                    || c1 < 0 || c1 == col || c2 < 0 || c2 == col) return;
            int i = r1 * col + c1;
            int j = r2 * col + c2;
            if (sizes[i] == 0 || sizes[j] == 0) return;
            int root1 = findRoot(i);
            int root2 = findRoot(j);
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

        private int findRoot(int index) {
            int path = 0;
            while (index != parents[index]) {
                index = parents[index];
                help[path++] = index;
            }
//            while (path != 0) {
//                parents[help[--path]] = index;
//            }
            for (path--; path >= 0; path--) {
                parents[help[path]] = index;
            }
            return index;
        }
    }

    public List<Integer> numIslands22(int m, int n, int[][] positions) {
        int[] arr = new int[m * n];
        Arrays.fill(arr, 0);
        List<Integer> res = new ArrayList<>();
        int num = 0;
        for (int[] position : positions) {
            num++;
            int x = position[0];
            int y = position[1];
            if (hasLandRound(arr, x, y, m, n)) {
                num--;
            }
            arr[x * n + y] = 1;
            res.add(num);
        }
        return res;
    }

    public boolean hasLandRound1(int[] arr, int x, int y, int m, int n) {
        boolean hasLandRound = false;
        int index;
        if ((index = x * n + y + 1) < m * n && arr[index] == 1) hasLandRound = true;
        else if (0 < (index = x * n + y - 1) && index < m * n && arr[index] == 1) hasLandRound = true;
        else if ((index = (x + 1) * n + y) < m * n && arr[index] == 1) hasLandRound = true;
        else if (0 < (index = (x - 1) * n + y) && index < m * n && arr[index] == 1) hasLandRound = true;
        return hasLandRound;
    }

    public boolean hasLandRound(int[] arr, int x, int y, int m, int n) {
        if (arr[x * n + y] == 1) return false;
        boolean hasLandRound = false;
        if (x * n + y + 1 < m * n && arr[x * n + y + 1] == 1) hasLandRound = true;
        else if (0 < x * n + y - 1 && x * n + y - 1 < m * n && arr[x * n + y - 1] == 1) hasLandRound = true;
        else if ((x + 1) * n + y < m * n && arr[(x + 1) * n + y] == 1) hasLandRound = true;
        else if (0 < (x - 1) * n + y && (x - 1) * n + y < m * n && arr[(x - 1) * n + y] == 1) hasLandRound = true;
        return hasLandRound;
    }

    public static void main(String[] args) {
        Code04_NumberOfIslandsII code04_numberOfIslandsII = new Code04_NumberOfIslandsII();
        int m = 3;
        int n = 3;
        for (int j = 0; j < 1; j++) {
            int[][] positions = new int[(int) (Math.random() * m * n)][2];
            for (int i = 0; i < positions.length; i++) {
                positions[i] = new int[]{(int) (Math.random() * m), (int) (Math.random() * n)};
            }
            List<Integer> integers1 = code04_numberOfIslandsII.numIslands21(m, n, positions);
            List<Integer> integers2 = code04_numberOfIslandsII.numIslands22(m, n, positions);
            for (int i = 0; i < integers1.size(); i++) {
                if (!integers1.get(i).equals(integers2.get(i))) {
                    for (int[] position : positions) {
                        System.out.println(position[0] + " , " + position[1]);
                    }
                    System.out.println(integers1.get(i));
                    System.out.println(integers2.get(i));
                    System.out.println("错误！！！！！！！！！！！！！");
                    return;
                }
            }
        }
        System.out.println("完成！！！！！！！！！！");
    }

    public static List<Integer> numIslands23(int m, int n, int[][] positions) {
        UnionFind1 uf = new UnionFind1(m, n);
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            ans.add(uf.connect(position[0], position[1]));
        }
        return ans;
    }

    public static class UnionFind1 {
        private int[] parent;
        private int[] size;
        private int[] help;
        private final int row;
        private final int col;
        private int sets;

        public UnionFind1(int m, int n) {
            row = m;
            col = n;
            sets = 0;
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
        }

        private int index(int r, int c) {
            return r * col + c;
        }

        private int find(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        private void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r1 == row || r2 < 0 || r2 == row || c1 < 0 || c1 == col || c2 < 0 || c2 == col) {
                return;
            }
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            if (size[i1] == 0 || size[i2] == 0) {
                return;
            }
            int f1 = find(i1);
            int f2 = find(i2);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }
        }

        public int connect(int r, int c) {
            int index = index(r, c);
            if (size[index] == 0) {
                parent[index] = index;
                size[index] = 1;
                sets++;
                union(r - 1, c, r, c);
                union(r + 1, c, r, c);
                union(r, c - 1, r, c);
                union(r, c + 1, r, c);
            }
            return sets;
        }

    }
}
