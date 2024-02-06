package com.work.taskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    private String commentText;
    private String author;

    private Date creationData = new Date();

    public Comment() {}

}
