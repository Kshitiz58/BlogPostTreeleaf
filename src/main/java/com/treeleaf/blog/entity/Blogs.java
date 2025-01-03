package com.treeleaf.blog.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Entity
public class Blogs {
    @Id
    private int id;
    private String blogTitle;
    private String blogDescription;
//    private MultipartFile thumbnailImage;
    private String thumbnailImageUrl;
}
