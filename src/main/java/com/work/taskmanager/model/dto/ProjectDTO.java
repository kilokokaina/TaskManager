package com.work.taskmanager.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ProjectDTO {

    private String name;
    private Set<String> userSet;

}
