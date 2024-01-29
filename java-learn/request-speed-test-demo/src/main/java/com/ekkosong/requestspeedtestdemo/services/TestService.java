package com.ekkosong.requestspeedtestdemo.services;

public interface TestService {
    // 使用单线程直接调用下游服务
    public void doMainTaskWithSingleThread();
    // 实现Runnable接口，手动创建5个线程来调用五个下游服务，主线程join等待五个线程执行完成
    void doMainTaskWithMultiThread();
    // 使用固定大小的线程池执行下游服务
    void doMainTaskWithFixedThreadPool();

    // 使用可缓存的线程池执行下游服务
    void doMainTaskWithCachedThreadPool();

    // 使用自定义线程池执行下游服务
    void doMainTaskWithCustomThreadPool();
}
