package com.work.taskmanager.api;

import com.work.taskmanager.model.Comment;
import com.work.taskmanager.model.Task;
import com.work.taskmanager.model.User;
import com.work.taskmanager.model.dto.TaskDTO;
import com.work.taskmanager.repository.CommentRepository;
import com.work.taskmanager.service.impl.TaskServiceImpl;
import com.work.taskmanager.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/task")
public class TaskAPI {

    private final TaskServiceImpl taskService;
    private final UserServiceImpl userService;
    private final CommentRepository commentRepository;

    @Autowired
    public TaskAPI(TaskServiceImpl taskService, UserServiceImpl userService,
                   CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> findTask(@PathVariable(value = "id") Task task) {
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody TaskDTO taskDTO, Authentication authentication) {
        Task task = taskService.create(taskDTO, userService.findByUsername(authentication.getName()));

        for (long userId : taskDTO.getTargetUser()) {
            User user = userService.findById(userId);
            userService.addTask(user, task);
        }

        return ResponseEntity.ok(task);
    }

    @GetMapping("find_by_project/{id}")
    public ResponseEntity<List<Task>> findByProject(@PathVariable(value = "id") Long projectId,
                                                    @RequestParam(name = "status") String status) {
        List<Task> taskList = taskService.findByProject(projectId);

        if (!status.isEmpty()) {
            taskList = taskList.stream().filter(task ->
                    task.getStatus().equals(status)
            ).collect(Collectors.toList());
        }

        taskList.sort(Comparator.comparing(Task::getCreationDate).reversed());

        return ResponseEntity.ok(taskList);
    }

    @GetMapping("find_by_user")
    public ResponseEntity<List<Task>> findByUser(@RequestParam(name = "is_author") boolean isAuthor,
                                                 Authentication authentication) {
        List<Task> resultList = new ArrayList<>();

        if (authentication != null) {
            String username = authentication.getName();
            if (isAuthor) resultList.addAll(taskService.findAllByAuthor(username));
            else resultList.addAll(userService.findByUsername(username).getTaskList());
        }

        return ResponseEntity.ok(resultList);
    }

    @PostMapping("comment/{id}")
    public ResponseEntity<Comment> addComment(@PathVariable(value = "id") Task task,
                                              @RequestBody Comment comment) {
        commentRepository.save(comment);

        List<Comment> commentList = task.getCommentList();
        commentList.add(comment);
        task.setCommentList(commentList);

        taskService.save(task);

        return ResponseEntity.ok(comment);
    }

    @GetMapping("status/{id}")
    public ResponseEntity<HttpStatus> changeStatus(@PathVariable(value = "id") Task task,
                                                   @RequestParam(name = "status") String status) {
        task.setStatus(status); taskService.save(task);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("user_list/{id}")
    public ResponseEntity<List<User>> getUsers(@PathVariable(value = "id") Long taskId) {
        return ResponseEntity.ok(userService.findByTaskId(taskId));
    }

}
