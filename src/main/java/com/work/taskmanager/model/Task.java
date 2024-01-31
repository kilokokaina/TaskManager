package com.work.taskmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.Set;

@Data
@Entity
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;

    private String title;
    private String description;

    private String author;

    @ElementCollection(targetClass = TaskStatus.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "task_status", joinColumns = @JoinColumn(name = "task_id"))
    @Enumerated(EnumType.STRING)
    private Set<TaskStatus> status;

    private Date creationDate = new Date();
    private Date targetDate;

    private Long projectId;

    public Task() {}

}
