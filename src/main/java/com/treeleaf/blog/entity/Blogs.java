package com.treeleaf.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
public class Blogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    private String blogTitle;
    private String blogDescription;
    private String thumbnailImageUrl;
    private Timestamp createdAt;
}
