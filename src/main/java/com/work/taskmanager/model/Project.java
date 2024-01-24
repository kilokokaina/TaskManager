package com.work.taskmanager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Project {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long projectId;

    private String name;
    private String projectDescribe;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_user", joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> userSet;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_task", joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private Set<Task> taskSet;

    public Project() {}

}
