package com.taozi.recursive;

import java.util.ArrayList;
import java.util.List;

/**
 * @author taosheng.zhao
 * @since 2021/1/2 17:32
 */
public class Code03_PrintAllPermutations {

    /**
     * 打印一个字符串的全部排列，使用一个List用来保存剩余未考察的字符
     *
     * @param target 目标字符串
     * @return 结果集
     */
    public List<String> printAllPermutations(String target) {
        List<String> ans = new ArrayList<>();
        if (target == null || target.length() == 0) return ans;
        char[] chars = target.toCharArray();
        List<Character> remain = new ArrayList<>();
        for (char c : chars) {
            remain.add(c);
        }
        process(remain, "", ans);
        return ans;
    }

    /**
     * 每次选出一个字符添加到临时结果后，从原字符集合中删除此字符，
     * 等到后续的结果返回后再将该字符加回原字符集合中
     *
     * @param remain 剩余的字符
     * @param temp   生成的临时结果
     * @param ans    最终的结果集
     */
    private void process(List<Character> remain, String temp, List<String> ans) {
        if (remain.isEmpty()) {
            ans.add(temp);
            return;
        }
        for (int i = 0; i < remain.size(); i++) {
            Character cur = remain.get(i);
            remain.remove(cur);
            process(remain, temp + cur, ans);
            remain.add(cur);
        }
    }


    /**
     * 打印一个字符串的全部排列，在字符串内部交换字符来生成不同的字符串
     *
     * @param target 目标字符串
     * @return 结果集
     */
    public List<String> printAllPermutations1(String target) {
        List<String> ans = new ArrayList<>();
        if (target == null || target.length() == 0) return ans;
        char[] chars = target.toCharArray();
        process(chars, 0, ans);
        return ans;
    }

    /**
     * 当前位置的字符依次与后续挨个位置的字符交换，生成新的字符串
     *
     * @param target 需要处理的字符串
     * @param index  当前位置
     * @param ans    最终的结果集
     */
    public void process(char[] target, int index, List<String> ans) {
        if (target.length == index) {
            ans.add(String.valueOf(target));
            return;
        }
        // visited的作用就是去重，如果要交换的字符和之前一样，那么就没必要交换
        boolean[] visited = new boolean[256];
        for (int i = index; i < target.length; i++) {
            if (visited[target[i]]) continue;
            visited[target[i]] = true;
            swap(target, index, i);
            process(target, index + 1, ans);
            swap(target, index, i);
        }
    }

    private void swap(char[] target, int index, int i) {
        char temp = target[index];
        target[index] = target[i];
        target[i] = temp;
    }
}
