package com.ekkosong.requestspeedtestdemo.task;

import java.util.concurrent.CountDownLatch;

public class SubService implements Runnable{
    private final String serviceName;
    private final CountDownLatch latch;

    public SubService(String serviceName, CountDownLatch latch) {
        this.serviceName = serviceName;
        this.latch = latch;
    }

    @Override
    public void run() {
        // System.out.println(serviceName + " begin");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // System.out.println(serviceName + " end");
            latch.countDown();
        }
    }
}
