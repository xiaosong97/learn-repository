package com.ekkosong.requestspeedtestdemo.services.impl;

import com.ekkosong.requestspeedtestdemo.services.TaskService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@EnableAsync
@Service
public class TaskServiceImpl implements TaskService {
    @Override
    @Async
    public CompletableFuture<String> executeTask(String taskName) {
        // 模拟子任务
        try {
            Thread.sleep(200); // 模拟200ms的任务执行时间
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return CompletableFuture.completedFuture(taskName + " completed");
    }
}
