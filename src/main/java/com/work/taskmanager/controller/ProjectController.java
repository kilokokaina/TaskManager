package com.work.taskmanager.controller;

import com.work.taskmanager.model.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("project")
public class ProjectController {

    @GetMapping("{id}")
    public String project(@PathVariable(value = "id") Project project, Model model) {
        model.addAttribute("project", project);
        return "project";
    }

}
