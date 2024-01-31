package com.work.taskmanager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
public class Project {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long projectId;

    private String title;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_user", joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> userList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_admin", joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> adminList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_curator", joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> curatorList;

    public Project() {}

}
