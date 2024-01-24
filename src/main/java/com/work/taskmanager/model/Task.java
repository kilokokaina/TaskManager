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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "task_user", joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> targetUser;

    private Date creationDate = new Date();
    private Date targetDate;

    public Task() {}

}
