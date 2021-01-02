package com.taozi.recursive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author taosheng.zhao
 * @since 2021/1/2 16:43
 */
public class Code02_PrintAllSubsequences {

    /**
     * 打印一个字符串的全部子序列
     *
     * @param target 目标字符串
     * @return 结果集
     */
    public List<String> printAllSubsequences(String target) {
        List<String> ans = new ArrayList<>();
        // 如果想要不重复的结果得用Set
        // Set<String> ans = new HashSet<>();
        process(target.toCharArray(), 0, "", ans);
        return ans;
    }

    /**
     * 所有字符都会两种情况，包含与不包含，按个字符处理，生成中间结果
     * 当所有字符都处理过了，就加入结果集中
     *
     * @param target 目标字符串转换出来的字符数组
     * @param index  当前处理字符的位置
     * @param temp   临时生成的结果
     * @param ans    最总生成的结果
     */
    public void process(char[] target, int index, String temp, Collection<String> ans) {
        // base case，当都处理完，将最总的结果加入到结果集中
        if (target.length == index) {
            ans.add(temp);
            return;
        }
        // 不选当前字符生成的结果
        process(target, index + 1, temp, ans);
        // 选当前字符生成的结果
        process(target, index + 1, temp + String.valueOf(target[index]), ans);
    }
}
