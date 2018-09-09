package com.ys.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Huang Zengmeng
 * @description 启动配置类
 * @date 2018/9/9
 */
public class EmailForker {

    public static void main(String[] args) {


        List<Integer> list = new ArrayList<>();
        for (int i = 10000; i > 0; i--) {
            list.add(i);
        }
        List<List<Integer>> result = new ArrayList<>();
        fork(list, result);
        System.out.println(result);

    }

    static void fork(List<Integer> list, List<List<Integer>> result) {
        if (list.size() < 10) {
            result.add(list);
        } else {
            Random random = new Random();
            int x = random.nextInt(10);
            fork(list.subList(0, x), result);
            fork(list.subList(x, list.size()), result);

        }
    }


}
