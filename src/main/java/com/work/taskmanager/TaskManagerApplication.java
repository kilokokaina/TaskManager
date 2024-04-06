package com.work.taskmanager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@EnableAsync
@SpringBootApplication
public class TaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner cmd() {
        return args -> {
            Runnable task = () -> {
                for (int i = 0; i < 10; i++) {
                    try {
                        log.info(String.valueOf(Math.pow(i, 2)));
                        Thread.sleep(1000);
                    } catch (InterruptedException exception) {
                        throw new RuntimeException(exception.getMessage());
                    }
                }
                log.info("Thread done");
            };

            Thread thread = new Thread(task, "Daemon Thread");
            thread.setDaemon(true);
            thread.start();
        };
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(15);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("Thread-");
        executor.initialize();

        return executor;
    }

}
