package com.treeleaf.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Blogs blogs;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    private String comment;
}
