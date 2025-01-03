package com.treeleaf.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    private int id;
    private String fullName;
    private String address;
    private String email;
    private String password;
    private String role;
}
