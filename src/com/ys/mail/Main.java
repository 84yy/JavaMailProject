package com.ys.mail;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Huang Zengmeng
 * @description 启动配置类
 * @date 2018/9/9
 */
public class Main {


    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        ExecutorService pool = Executors.newFixedThreadPool(16);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 100; i > 0; i--) {
            list.add("gp_test_01@163.com");
        }
        List<List<String>> result = new ArrayList<>();
        fork(list, result);
        result.forEach(e -> {
            SendEmailsTask task = new SendEmailsTask(list);
            pool.submit(task);
        });
        while (!pool.awaitTermination(2, TimeUnit.SECONDS)) {

        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000);
    }


    static void fork(List<String> list, List<List<String>> result) {
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
