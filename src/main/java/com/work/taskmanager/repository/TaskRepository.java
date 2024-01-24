package com.work.taskmanager.repository;

import com.work.taskmanager.model.Task;
import com.work.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByAuthor(User author);
    Task findByTitle(String title);

}
