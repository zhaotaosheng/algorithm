package com.taozi.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个字符串str，给定一个字符串类型的数组arr。
 * arr里的每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来。
 * 返回需要至少多少张贴纸可以完成这个任务。
 * 例子：str= "babac"，arr = {"ba","c","abcd"}
 * 至少需要两张贴纸"ba"和"abcd"，因为使用这两张贴纸，把每一个字符单独剪开，含有2个a、2个b、1个c。是可以拼出str的。所以返回2。
 *
 * @author ZhaoTaoSheng
 * @since 2021/1/11 13:17
 */
public class Code05_StickersToSpellWord {

    /**
     * 需要至少多少张贴纸可以完成这个任务
     *
     * @param arr 所有的贴纸
     * @param str 目标字符串
     * @return 最小贴纸数
     */
    public int stickersToSpellWord(String[] arr, String str) {
        int ans = process(arr, str);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * 使用最少的贴纸完成字符串
     *
     * @param stickers 所有的贴纸
     * @param target   剩余目标字符串
     * @return 最小贴纸数
     */
    private int process(String[] stickers, String target) {
        // 如果剩余字符串长度为0，代表完成，不在需要贴纸
        if (target.length() == 0) return 0;
        // 如果现有的贴纸解决不掉字符串返回系统最大值
        int ans = Integer.MAX_VALUE;
        // 使用每一张贴纸来尝试
        for (String first : stickers) {
            // 在目标字符串中减去贴纸含有的字符
            String remain = minus(target, first);
            // 如果减去后与原字符串长度不等代表该贴纸可用
            if (target.length() != remain.length()) {
                // 上一轮的结果与当前轮结果的最小值
                ans = Math.min(ans, process(stickers, remain));
            }
        }
        // 如果为系统最大值说明没有解，返回给上游
        // 如果有具体值，则还需要加上当前贴纸
        return ans == Integer.MAX_VALUE ? ans : ans + 1;
    }

    /**
     * 在目标字符串中减去贴纸含有的字符
     *
     * @param target  目标字符串
     * @param sticker 贴纸
     * @return 剩余字符串
     */
    private String minus(String target, String sticker) {
        char[] targetChars = target.toCharArray();
        char[] stickerChars = sticker.toCharArray();
        // 词频统计，当统计数小于1时，代表目标中该字符都减没了
        int[] count = new int[26];
        for (char targetChar : targetChars) {
            count[targetChar - 'a']++;
        }
        for (char stickerChar : stickerChars) {
            count[stickerChar - 'a']--;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                for (int j = 0; j < count[i]; j++) {
                    stringBuilder.append((char) i + 'a');
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 需要至少多少张贴纸可以完成这个任务
     *
     * @param arr 所有的贴纸
     * @param str 目标字符串
     * @return 最小贴纸数
     */
    public int stickersToSpellWord1(String[] arr, String str) {
        int length = arr.length;
        // 每张贴纸的词频统计
        int[][] counts = new int[length][26];
        for (int i = 0; i < length; i++) {
            char[] chars = arr[i].toCharArray();
            for (char aChar : chars) {
                counts[i][aChar - 'a']++;
            }
        }
        int ans = process1(counts, str);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * 使用最少的贴纸完成字符串
     *
     * @param stickers 每张贴纸的词频
     * @param target   剩余的目标字符串
     * @return 最少贴纸数
     */
    private int process1(int[][] stickers, String target) {
        if (target.length() == 0) return 0;
        char[] targetChars = target.toCharArray();
        int[] targetCounts = new int[26];
        // 目标字符串的词频统计
        for (char targetChar : targetChars) {
            targetCounts[targetChar - 'a']++;
        }
        int ans = Integer.MAX_VALUE;
        // sticker 每一张贴纸的词频
        for (int[] sticker : stickers) {
            // 当前贴纸如果不含有目标的第一个字符则跳过，贪心
            if (sticker[targetChars[0] - 'a'] > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (targetCounts[j] > 0) {
                        int nums = targetCounts[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            stringBuilder.append((char) j + 'a');
                        }
                    }
                }
                String remain = stringBuilder.toString();
                ans = Math.min(ans, process1(stickers, remain));
            }
        }
        return ans == Integer.MAX_VALUE ? ans : ans + 1;
    }


    /**
     * 需要至少多少张贴纸可以完成这个任务
     *
     * @param arr 所有的贴纸
     * @param str 目标字符串
     * @return 最小贴纸数
     */
    public int stickersToSpellWord2(String[] arr, String str) {
        int length = arr.length;
        // 每张贴纸的词频统计
        int[][] counts = new int[length][26];
        for (int i = 0; i < length; i++) {
            char[] chars = arr[i].toCharArray();
            for (char aChar : chars) {
                counts[i][aChar - 'a']++;
            }
        }
        Map<String, Integer> dp = new HashMap<>();
        dp.put("", 0);
        int ans = process2(counts, str, dp);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * 使用最少的贴纸完成字符串
     *
     * @param stickers 每张贴纸的词频
     * @param target   剩余的目标字符串
     * @param dp       缓存表(字符串 -> 需要贴纸数)
     * @return 最少贴纸数
     */
    private int process2(int[][] stickers, String target, Map<String, Integer> dp) {
        // 如果缓存中有直接返回
        if (dp.get(target) != null) {
            return dp.get(target);
        }
        char[] targetChars = target.toCharArray();
        int[] targetCounts = new int[26];
        // 目标字符串的词频统计
        for (char targetChar : targetChars) {
            targetCounts[targetChar - 'a']++;
        }
        int ans = Integer.MAX_VALUE;
        // sticker 每一张贴纸的词频
        for (int[] sticker : stickers) {
            // 当前贴纸如果不含有目标的第一个字符则跳过，贪心
            if (sticker[targetChars[0] - 'a'] > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (targetCounts[j] > 0) {
                        int nums = targetCounts[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            stringBuilder.append((char) j + 'a');
                        }
                    }
                }
                String remain = stringBuilder.toString();
                ans = Math.min(ans, process1(stickers, remain));
            }
        }
        ans = ans == Integer.MAX_VALUE ? ans : ans + 1;
        dp.put(target, ans);
        return ans;
    }
}
