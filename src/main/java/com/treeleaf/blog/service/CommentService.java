package com.treeleaf.blog.service;

import com.treeleaf.blog.entity.Blogs;
import com.treeleaf.blog.entity.Comment;
import com.treeleaf.blog.entity.User;

import java.util.List;

public interface CommentService {
    List<Comment> findCommentsByBlog(Blogs blog);

    Comment createComment(Comment comment, Blogs blog, User user);

    int deleteComment(int id);
}
