package com.treeleaf.blog.serviceImpl;

import com.treeleaf.blog.entity.Comment;
import com.treeleaf.blog.entity.Blogs;
import com.treeleaf.blog.entity.User;
import com.treeleaf.blog.repository.CommentRepository;
import com.treeleaf.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentsRepository;

    @Override
    public List<Comment> findCommentsByBlog(Blogs blog) {
        try {
            return commentsRepository.findCommentsByBlog(blog);
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    public Comment createComment(Comment comment, Blogs blog, User user) {
        try {
            comment.setBlogs(blog);
            comment.setUser(user);
            commentsRepository.createComment(comment);
            return comment;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    public int deleteComment(int id) {
        try {
            return commentsRepository.deleteComment(id);
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }
}
