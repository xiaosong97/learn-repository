package com.ekkosong.multithread;

import java.util.concurrent.*;

public class MainServiceImpl implements MainService {
    private static final int TASK_NUM = 5;
    /**
     * 期待最大耗时100ms，需要调用5个不相关的下游服务SubService
     */
    @Override
    public void doMainTaskWithSingleThread() {
        // System.out.println("【doMainTaskWithSingleThread】Main Task begin...");
        CountDownLatch latch = new CountDownLatch(TASK_NUM);
        SubService s1 = new SubService("子任务1", latch);
        SubService s2 = new SubService("子任务2", latch);
        SubService s3 = new SubService("子任务3", latch);
        SubService s4 = new SubService("子任务4", latch);
        SubService s5 = new SubService("子任务5", latch);
        s1.run();
        s2.run();
        s3.run();
        s4.run();
        s5.run();
        // System.out.println("【doMainTaskWithSingleThread】Finish Main Task!");
    }

    @Override
    public void doMainTaskWithMultiThread() {
        // System.out.println("【doMainTaskWithMultiThread】Main Task begin...");
        CountDownLatch latch = new CountDownLatch(TASK_NUM);
        for (int i = 0; i < TASK_NUM; i++) {
            new Thread(new SubService("子任务-" + i, latch)).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // System.out.println("【doMainTaskWithMultiThread】Finish Main Task!");
        }
    }

    @Override
    public void doMainTaskWithFixedThreadPool() {
        // System.out.println("【doMainTaskWithFixedThreadPool】Main Task begin...");
        ExecutorService threadPool = Executors.newFixedThreadPool(12);
        int taskNum = TASK_NUM;
        CountDownLatch latch = new CountDownLatch(taskNum);
        for (int i = 0; i < taskNum; i++) {
            threadPool.submit(new SubService("子任务-" + i, latch));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // System.out.println("【doMainTaskWithFixedThreadPool】Finish Main Task!");
            // threadPool.shutdown();
        }

    }

    @Override
    public void doMainTaskWithCachedThreadPool() {
        // System.out.println("【doMainTaskWithCachedThreadPool】Main Task begin...");
        ExecutorService threadPool = Executors.newCachedThreadPool();
        int taskNum = TASK_NUM;
        CountDownLatch latch = new CountDownLatch(taskNum);
        for (int i = 0; i < taskNum; i++) {
            threadPool.submit(new SubService("子任务-" + i, latch));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // System.out.println("【doMainTaskWithCachedThreadPool】Finish Main Task!");
            // threadPool.shutdown();
        }
    }

    @Override
    public void doMainTaskWithCustomThreadPool() {
        ExecutorService threadPool = new ThreadPoolExecutor(5,13,
                1L,TimeUnit.SECONDS, new LinkedBlockingQueue<>(8),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        int taskNum = TASK_NUM;
        CountDownLatch latch = new CountDownLatch(taskNum);
        for (int i = 0; i < taskNum; i++) {
            threadPool.submit(new SubService("子任务-" + i, latch));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // System.out.println("【doMainTaskWithCachedThreadPool】Finish Main Task!");
            // threadPool.shutdown();
        }
    }
}
