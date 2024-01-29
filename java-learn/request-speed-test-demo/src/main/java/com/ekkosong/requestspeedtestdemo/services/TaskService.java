package com.ekkosong.requestspeedtestdemo.services;

import java.util.concurrent.CompletableFuture;

public interface TaskService {
    CompletableFuture<String> executeTask(String taskName);
}
