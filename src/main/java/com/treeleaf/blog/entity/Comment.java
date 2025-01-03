package com.treeleaf.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Comment {
    @Id
    private int id;
    private Long userId;
    private Long blogId;
    private String comment;
}
