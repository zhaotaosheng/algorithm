package com.taozi.binarytree;

import java.util.List;

/**
 * 派对的最大快乐值
 * @author taosheng.zhao
 * @since 2020/12/18 12:57
 */
public class Code16_MaxHappy {

    private static class Employee {
        public int happy; // 这名员工可以带来的快乐值
        List<Employee> subordinates; // 这名员工有哪些直接下级
    }

}
