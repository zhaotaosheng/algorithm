package com.taozi.binarytree;

/**
 * 请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后展开。此时折痕是凹下去的，即折痕突起的方向指向纸条的背面。
 * 如果从纸条的下边向上方连续对折2次，压出折痕后展开，此时有三条折痕，从上到下依次是下折痕、下折痕和上折痕。
 * 给定一个输入参数N，代表纸条都从下边向上方连续对折N次。 请从上到下打印所有折痕的方向。
 * 例如:N=1时，打印: down N=2时，打印: down down up
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/14 20:06
 */
public class Code07_PaperFolding {

    /* 当N=3时，折痕如下：
        d d u d d u u
              d
          d       u
        d   u   d   u
        规律解释二叉树的中序遍历，根节点和左头节点为d，右头节点为u
     */

    /**
     * 打印折痕方向
     *
     * @param n 折叠次数
     */
    private void print(int n) {
        if (n < 1) return;
        // 根节点为向下折痕
        print(1, n, true);
    }

    /**
     * 中序打印折痕方向
     *
     * @param i    当前节点所在层数
     * @param n    总层数
     * @param down 是否为向下折痕
     */
    private void print(int i, int n, boolean down) {
        if (i > n) return;
        // 左子树头节点都为向下折痕
        print(i + 1, n, true);
        System.out.print(down ? "down " : "up ");
        // 右子树头节点都为向上折痕
        print(i + 1, n, false);
    }
}
