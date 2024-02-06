package com.work.taskmanager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;

    private String title;
    private String description;
    private String author;
    private String status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "task_comment", joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<Comment> commentList;

    private Date creationDate = new Date();
    private Date targetDate;

    private Long projectId;

    public Task() {}

}
