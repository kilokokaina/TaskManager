package com.work.taskmanager.model.proxy;

import com.work.taskmanager.model.Project;
import com.work.taskmanager.model.Task;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@NoArgsConstructor
public class ProjectProxy {

    private Project project;
    private List<Task> projectTaskList;
    private List<Task> userTaskList;

    public List<Task> getTasksByStatus(String taskStatus) {
        return projectTaskList.stream().filter(item -> item.getStatus().equals(taskStatus)).toList();
    }

}
