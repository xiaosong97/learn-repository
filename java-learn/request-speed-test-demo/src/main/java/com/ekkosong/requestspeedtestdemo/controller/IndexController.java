package com.ekkosong.requestspeedtestdemo.controller;

import com.ekkosong.requestspeedtestdemo.services.TaskService;
import com.ekkosong.requestspeedtestdemo.services.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class IndexController {
    @Autowired
    private TestService testService;
    @Autowired
    private TaskService taskService;
    @GetMapping("/test1")
    public String doSomething1() {
        testService.doMainTaskWithSingleThread();
        log.info("test1 do something.");
        return "Success";
    }

    @GetMapping("/test2")
    public String doSomething2() {
        testService.doMainTaskWithMultiThread();
        log.info("test2 do something.");
        return "Success";
    }

    @GetMapping("/test3")
    public String doSomething3() {
        testService.doMainTaskWithFixedThreadPool();
        log.info("test3 do something.");
        return "Success";
    }

    @GetMapping("/test4")
    public String doSomething4() {
        testService.doMainTaskWithCachedThreadPool();
        log.info("test4 do something.");
        return "Success";
    }

    @GetMapping("/test5")
    public String doSomething5() {
        testService.doMainTaskWithCustomThreadPool();
        log.info("test5 do something.");
        return "Success";
    }

    @GetMapping("/test6")
    public String doSomething6() {
        CompletableFuture<String> task1 = taskService.executeTask("Task1");
        CompletableFuture<String> task2 = taskService.executeTask("Task2");
        CompletableFuture<String> task3 = taskService.executeTask("Task3");
        CompletableFuture<String> task4 = taskService.executeTask("Task4");
        CompletableFuture<String> task5 = taskService.executeTask("Task5");

        CompletableFuture.allOf(task1, task2, task3, task4, task5).join();

        return "All tasks completed";
    }
}
