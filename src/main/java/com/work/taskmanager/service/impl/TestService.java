package com.work.taskmanager.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class TestService {

    @Async
    public void test() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            log.info("Iteration: " + i);
            Thread.sleep(1000);
        }

        log.info("Thread done");
    }

    @Async
    public CompletableFuture<String> test2() throws InterruptedException {
        Thread.sleep(3000);
        return CompletableFuture.completedFuture("Hello");
    }

}
