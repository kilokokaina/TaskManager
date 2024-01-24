package com.work.taskmanager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orgId;

    private String name;

    private String orgUUID = UUID.randomUUID().toString();

}
