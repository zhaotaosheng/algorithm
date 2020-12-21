package com.taozi.binarytree;

import java.util.List;

/**
 * 派对的最大快乐值
 * 公司的每个员工都符合 Employee 类的描述。整个公司的人员结构可以看作是一棵标准的、 没有环的多叉树。
 * 树的头节点是公司唯一的老板。除老板之外的每个员工都有唯一的直接上级。
 * 叶节点是没有任何下属的基层员工(subordinates列表为空)，除基层员工外，每个员工都有一个或多个直接下级。
 * 这个公司现在要办party，你可以决定哪些员工来，哪些员工不来，规则：
 * 1.如果某个员工来了，那么这个员工的所有直接下级都不能来
 * 2.派对的整体快乐值是所有到场员工快乐值的累加
 * 3.你的目标是让派对的整体快乐值尽量大
 * 给定一棵多叉树的头节点boss，请返回派对的最大快乐值。
 *
 * @author taosheng.zhao
 * @since 2020/12/18 12:57
 */
public class Code16_MaxHappy {
    // 员工信息
    private static class Employee {
        public int happy; // 这名员工可以带来的快乐值
        List<Employee> subordinates; // 这名员工有哪些直接下级
    }

    /**
     * 获取排队最大happy值
     *
     * @param boss 老板
     * @return 最大happy值
     */
    public int getMaxHappy(Employee boss) {
        return Math.max(process(boss).comeHappy, process(boss).notComeHappy);
    }

    // 来情况的最大值和不来情况的最大值
    private static class Info {
        public int comeHappy;
        public int notComeHappy;

        public Info(int comeHappy, int notComeHappy) {
            this.comeHappy = comeHappy;
            this.notComeHappy = notComeHappy;
        }
    }

    /**
     * 获取当前员工来或者不来情况的最大值
     * 1.如果当前员工来，则他所有的下属都不能来
     * 2.如果当前员工不来，则他下属无所谓，取下属来或不来的最大值
     *
     * @param employee 员工
     * @return 当前员工来或不来的最大值；
     */
    public Info process(Employee employee) {
        if (employee == null) return new Info(0, 0);
        int comeHappy = 0;
        int notComeHappy = employee.happy;
        for (Employee subordinate : employee.subordinates) {
            Info info = process(subordinate);
            comeHappy += info.notComeHappy;
            notComeHappy += Math.max(info.comeHappy, info.notComeHappy);
        }
        return new Info(comeHappy, notComeHappy);
    }
}
