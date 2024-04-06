package com.work.taskmanager.controller;

import com.work.taskmanager.service.impl.ProjectServiceImpl;
import com.work.taskmanager.service.impl.TaskServiceImpl;
import com.work.taskmanager.service.impl.TestService;
import com.work.taskmanager.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Controller
public class MainController {

    private final UserServiceImpl userService;
    private final ProjectServiceImpl projectService;
    private final TaskServiceImpl taskService;
    private final TaskExecutor executor;

    @Autowired
    public TestService testService;

    @Autowired
    public MainController(ProjectServiceImpl projectService, UserServiceImpl userService,
                          TaskServiceImpl taskService, @Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
        this.executor = taskExecutor;
    }

    @GetMapping
    public String hello(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("projects", projectService.findByUserId(
                userService.findByUsername(authentication.getName()).getUserId()
            ));
        }
        return "v3/index";
    }

    @GetMapping("test")
    public @ResponseBody String test() throws InterruptedException {
        testService.test();
        testService.test();
        testService.test();
        testService.test();

        return "test";
    }

    @GetMapping("test2")
    public @ResponseBody String test2() {
        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        log.info(String.valueOf(Math.pow(j, 2)));
                        Thread.sleep(1500);
                    }

                    log.info("Thread done");
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception.getMessage());
                }
            });
        }

        return "test-2";
    }

    @GetMapping("test3")
    public @ResponseBody String test3() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();

        CompletableFuture<String> result1 = testService.test2();
        CompletableFuture<String> result3 = testService.test2();
        CompletableFuture<String> result4 = testService.test2();
        CompletableFuture<String> result2 = testService.test2();

        CompletableFuture.allOf(result1, result2, result3, result4).join();

        log.info(String.valueOf(System.currentTimeMillis() - start));

        log.info(result1.get());
        log.info(result2.get());
        log.info(result3.get());
        log.info(result4.get());

        return "test3";
    }

}
