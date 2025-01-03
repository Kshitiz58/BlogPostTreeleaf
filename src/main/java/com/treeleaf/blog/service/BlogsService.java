package com.treeleaf.blog.service;

import com.treeleaf.blog.entity.Blogs;
import com.treeleaf.blog.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogsService {
    Blogs findBlogById(int id);
    List<Blogs> findBlogsByUser(User user);
    Blogs createBlog(Blogs blog, MultipartFile thumbnailImage, User user);
    Blogs updateBlog(Blogs blog, MultipartFile thumbnailImage);
    List<Blogs> findBlogsByTitle(String title);
    int deleteBlogs(int id);
}
