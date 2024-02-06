package com.work.taskmanager.repository;

import com.work.taskmanager.model.Task;
import com.work.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByAuthor(String author);
    Task findByTitle(String title);
    List<Task> findAllByProjectId(Long projectId);

}
