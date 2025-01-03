package com.treeleaf.blog.controller;

import com.treeleaf.blog.entity.Blogs;
import com.treeleaf.blog.entity.Comment;
import com.treeleaf.blog.entity.User;
import com.treeleaf.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentsService;

    @GetMapping("/")
    public ResponseEntity<?> getCommentsByBlog(@RequestParam int blogId) {
        try {
            Blogs blog = new Blogs();
            blog.setId(blogId);
            List<Comment> comments = commentsService.findCommentsByBlog(blog);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    @PostMapping("/create/")
    public ResponseEntity<?> createComment(
            @RequestParam int blogId,
            @RequestParam("comment") String commentText,
            @AuthenticationPrincipal User user) {

        try {
            Blogs blog = new Blogs();
            blog.setId(blogId);

            Comment comment = new Comment();
            comment.setComment(commentText);

            Comment createdComment = commentsService.createComment(comment, blog, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteComment(@RequestParam int id) {
        try {
            int result = commentsService.deleteComment(id);
            if (result == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }
}
