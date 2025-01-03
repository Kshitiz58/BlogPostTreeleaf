package com.treeleaf.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fullName;
    private String address;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String role;
}
