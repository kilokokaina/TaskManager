package com.work.taskmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@Data
@Entity
public class Department {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long depId;

    private String fullName;
    private String shortName;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Organization organization;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "dep_user", joinColumns = @JoinColumn(name = "dep_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> userSet;

}
