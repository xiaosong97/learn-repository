package com.ekkosong.multithread;

// 编写一个服务：
// 有一个C端场景，单机1000QPS，4个9，期望最大耗时100ms，12核8g的机器，
// 内部需要调用5个无互相依赖的下游，下游耗时都在100ms以内，你会怎么设计实现，需要考虑哪些方面？（不考虑网络状态）
// 1000 QPS
// 1s 并发吞吐量 1s 10000*5
public class Demo {
    public static void main(String[] args) {
        MainService mainService = new MainServiceImpl();
        long start = System.currentTimeMillis();
        // for (int i = 0; i < 1000; i++) {
        //     mainService.doMainTaskWithSingleThread();
        //     System.out.println("doMainTaskWithSingleThread " + i);
        // }
        // System.out.println("【doMainTaskWithSingleThread】 cost time " + (System.currentTimeMillis() - start) + "ms");

        // start = System.currentTimeMillis();
        // for (int i = 0; i < 100; i++) {
        //     mainService.doMainTaskWithMultiThread();
        //     System.out.println("doMainTaskWithMultiThread " + i);
        // }
        // System.out.println("【doMainTaskWithMultiThread】 cost time " + (System.currentTimeMillis() - start) + "ms");

        // start = System.currentTimeMillis();
        // for (int i = 0; i < 100; i++) {
        //     mainService.doMainTaskWithFixedThreadPool();
        //     System.out.println("doMainTaskWithFixedThreadPool " + i);
        // }
        // System.out.println("【doMainTaskWithFixedThreadPool】 cost time " + (System.currentTimeMillis() - start) + "ms");
        //
        // start = System.currentTimeMillis();
        // for (int i = 0; i < 100; i++) {
        //     mainService.doMainTaskWithCachedThreadPool();
        //     System.out.println("doMainTaskWithCachedThreadPool " + i);
        // }
        // System.out.println("【doMainTaskWithCachedThreadPool】 cost time " + (System.currentTimeMillis() - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            mainService.doMainTaskWithCustomThreadPool();
            System.out.println("doMainTaskWithCustomThreadPool " + i);
        }
        System.out.println("【doMainTaskWithCustomThreadPool】 cost time " + (System.currentTimeMillis() - start) + "ms");
    }
}
