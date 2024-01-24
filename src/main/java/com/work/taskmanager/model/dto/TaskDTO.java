package com.work.taskmanager.model.dto;

import lombok.Data;

@Data
public class TaskDTO {

    private String title;
    private String description;
    private String[] targetUser;

}
