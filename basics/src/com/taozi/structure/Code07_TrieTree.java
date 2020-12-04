package com.taozi.structure;

/**
 * 前缀树（prefix/trie tree）
 * 1）单个字符串中，字符从前到后的加到一棵多叉树上
 * 2）字符放在路上，节点上有专属的数据项（常见的是pass和end值）
 * 3）所有样本都这样添加，如果没有路就新建，如有路就复用
 * 4）沿途节点的pass值增加1，每个字符串结束时来到的节点end值增加1
 * <p>
 * ["abc","abd","bcd","acb"]，O是节点
 * --------O
 * ------a/-\b
 * ------O---O
 * ----b/-\c--\c
 * ----0---O---O
 * --c/-\d--\d--\d
 * -O----O---O---O
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/4 21:01
 */
public class Code07_TrieTree {

    // 前缀树节点
    private class Node {
        // 经过此节点的个数
        public int pass;
        // 以此节点为结尾的个数
        public int end;
        // 下个节点，假设为通往 a ~ z 的26个方向
        public Node[] nextNodes;

        public Node() {
            this.pass = 0;
            this.end = 0;
            // 假设只有 a ~ z 26个字母，如果数据种类多用Map来维护
            // nextNodes[i] == null 表示通往i方向的路不存在
            this.nextNodes = new Node[26];
        }
    }

    // 前缀树
    private class TrieTree {

        // 前缀树根节点
        private Node root;

        public TrieTree() {
            this(new Node());
        }

        public TrieTree(Node root) {
            this.root = root;
        }

        /**
         * 添加某个字符串，可以重复添加，每次算1个
         *
         * @param str 某个字符串
         */
        public void insert(String str) {
            if (str == null) return;
            // 首先将指针指向根节点
            Node root = this.root;
            // 根节点的通过次数加1
            root.pass++;
            // 将字符串转成字符数组，挂到树上
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                // 找到下一个节点路径
                int next = chars[i] - 'a';
                // 如果下一个节点路径还没有，则新建一个节点，生成该路径
                if (root.nextNodes[next] == null) {
                    root.nextNodes[next] = new Node();
                }
                // 将指针移动到新的节点上，准备找下一个字符的路径
                root = root.nextNodes[next];
                // 将该节点通过次数加1
                root.pass++;
            }
            // 只有在最后一个节点加结尾树
            root.end++;
        }

        /**
         * 删掉某个字符串，可以重复删除，每次算1个
         *
         * @param str 某个字符串
         */
        public void delete(String str) {
            if (search(str) < 1) return;
            Node root = this.root;
            root.pass--;
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                int next = chars[i] - 'a';
                // 如果发现下一个节点的pass减完后为1，说明后续的直接砍掉就行
                if (--root.nextNodes[next].pass == 0) {
                    // 手动设为null，防止内存泄露
                    root.nextNodes[next] = null;
                    return;
                }
                root = root.nextNodes[next];
            }
            root.end--;
        }

        /**
         * 查询某个字符串在结构中还有几个
         *
         * @param str 某个字符串
         * @return 在树中有几个
         */
        private int search(String str) {
            if (str == null) return 0;
            Node root = this.root;
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                int next = chars[i] - 'a';
                // 如果字符数组没遍历完就发现没路了说明没有这个字符串
                if (root.nextNodes[next] == null) {
                    return 0;
                }
                root = root.nextNodes[next];
            }
            return root.end;
        }

        /**
         * 查询有多少个字符串，是以str做前缀的
         *
         * @param str 某个字符串前缀
         * @return 在树中有几个
         */
        private int prefixNumber(String str) {
            if (str == null) return 0;
            Node root = this.root;
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                int next = chars[i] - 'a';
                // 如果字符数组没遍历完就发现没路了说明没有这个字符串前缀
                if (root.nextNodes[next] == null) {
                    return 0;
                }
                root = root.nextNodes[next];
            }
            return root.pass;
        }
    }
}
