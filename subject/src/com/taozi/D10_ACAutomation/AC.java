package com.taozi.D10_ACAutomation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * AC自动机
 * 解决在一个大字符串中，找到多个候选字符串的问题
 * <p>
 * AC自动机算法步骤
 * 1）把候选串生成前缀树
 * 2）前缀树节点增加fail指针
 * 3）顺序遍历大字符串进行匹配
 * <p>
 * AC自动机算法核心
 * 1）fail指针，用来淘汰最少不可能匹配的前缀
 * 2）遍历，如果匹配不到则从fail开始继续遍历，遍历过的直接跳过
 *
 * @author ZhaoTaoSheng
 * @since 2021/3/4 19:06
 */
public class AC {

    // trie tree 节点
    public static class Node {
        // 记录完整的候选字符串，如果为null表示不是结尾
        public String end;
        // 记录完整的候选字符串是否被匹配到过
        public boolean used;
        // 当前匹配失败的下次尝试位置
        public Node fail;
        // 26条路表示从a-z这26种字母，字母不在node本身上
        public Node[] next = new Node[26];
    }

    public static class ACAutomation {
        // 根节点
        public static Node root = new Node();

        // 添加候选字符串，生成trie tree
        public static void insert(String str) {
            char[] chars = str.toCharArray();
            Node cur = root;
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if (cur.next[index] == null) {
                    cur.next[index] = new Node();
                }
                cur = cur.next[index];
            }
            cur.end = str;
        }

        // 宽度优先遍历的方式为所有节点添加fail指针
        // root节点的fail指针为null，root子节点的fail指针为root
        // 某一节点的fail指针：指向其父fail指针节点下具有相同字母路径的节点，否则指向root
        public static void build() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur, curFail;
            while (!queue.isEmpty()) {
                cur = queue.poll();
                // 当前节点弹出时，填好其所有子节点的fail指针，并将所有子节点加入queue中
                for (int i = 0; i < 26; i++) {
                    // cur表示当前节点，curFail表示当前节点的fail节点
                    if (cur.next[i] != null) {
                        cur.next[i].fail = root;
                        curFail = cur.fail;
                        while (curFail != null) {
                            if (curFail.next[i] != null) {
                                cur.next[i].fail = curFail.next[i];
                                break;
                            }
                            curFail = curFail.fail;
                        }
                        queue.add(cur.next[i]);
                    }
                }
            }
        }

        // 匹配content中包含的候选字符串
        // 按内容顺序匹配每个字母，如果匹配失败则从fail指向的节点继续匹配
        public static List<String> match(String content) {
            List<String> ans = new ArrayList<>();
            char[] chars = content.toCharArray();
            Node cur = root;
            Node watcher;
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                // 如果当前节点匹配失败，则从当前节点的fail节点继续匹配
                while (cur.next[index] == null && cur != root) {
                    cur = cur.fail;
                }
                cur = cur.next[index] != null ? cur.next[index] : root;
                watcher = cur;
                while (watcher != root) {
                    if (watcher.used) {
                        break;
                    }
                    if (watcher.end != null) {
                        ans.add(watcher.end);
                        watcher.used = true;
                    }
                    watcher = watcher.fail;
                }
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        ACAutomation.insert("dhes");
        ACAutomation.insert("he");
        ACAutomation.insert("abcdheks");
        // 设置fail指针
        ACAutomation.build();

        List<String> contains = ACAutomation.match("abcdheksskdjfafhasldkflskdjhwqaeruv");
        for (String word : contains) {
            System.out.println(word);
        }
    }
}
