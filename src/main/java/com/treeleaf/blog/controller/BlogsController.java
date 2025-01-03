package com.treeleaf.blog.controller;

import com.treeleaf.blog.entity.Blogs;
import com.treeleaf.blog.entity.User;
import com.treeleaf.blog.exceptions.ResourceNotFoundException;
import com.treeleaf.blog.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/blogs")
public class BlogsController {
    @Autowired
    private BlogsService blogsService;

    @GetMapping("/")
    public ResponseEntity<?> getBlogById(@RequestParam int id) {
        try {
            Blogs blog = blogsService.findBlogById(id);
            return ResponseEntity.ok(blog);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/user/")
    public ResponseEntity<?> getBlogsByUser(@RequestParam int userId) {
        try {
            User user = new User();
            user.setId(userId);
            List<Blogs> blogs = blogsService.findBlogsByUser(user);
            return ResponseEntity.ok(blogs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createBlog(
            @RequestParam("blogTitle") String blogTitle,
            @RequestParam("blogDescription") String blogDescription,
            @RequestParam("thumbnailImage") MultipartFile thumbnailImage,
            @AuthenticationPrincipal User user) {

        try {
            Blogs blog = new Blogs();
            blog.setBlogTitle(blogTitle);
            blog.setBlogDescription(blogDescription);

            Blogs createdBlog = blogsService.createBlog(blog, thumbnailImage, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBlog);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> updateBlog(
            @PathVariable int id,
            @RequestParam(value = "blogTitle", required = false) String blogTitle,
            @RequestParam(value = "blogDescription", required = false) String blogDescription,
            @RequestParam(value = "thumbnailImage", required = false) MultipartFile thumbnailImage) {

        try {
            Blogs blog = blogsService.findBlogById(id);

            if (blogTitle != null) blog.setBlogTitle(blogTitle);
            if (blogDescription != null) blog.setBlogDescription(blogDescription);

            Blogs updatedBlog = blogsService.updateBlog(blog, thumbnailImage);
            return ResponseEntity.ok(updatedBlog);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBlogsByTitle(@RequestParam String title) {
        try {
            List<Blogs> blogs = blogsService.findBlogsByTitle(title);
            return ResponseEntity.ok(blogs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteBlog(@RequestParam int id) {
        try {
            blogsService.deleteBlogs(id);
            return ResponseEntity.ok("Blogs deleted successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

}
