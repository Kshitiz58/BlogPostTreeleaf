package com.treeleaf.blog.repository;

import com.treeleaf.blog.entity.Comment;
import com.treeleaf.blog.entity.Blogs;
import com.treeleaf.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Comment> findCommentsByBlog(Blogs blog) {
        String sql = "SELECT * FROM comment WHERE blog_id = ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{blog.getId()},
                new BeanPropertyRowMapper<>(Comment.class)
        );
    }

    public int createComment(Comment comment) {
        String sql = "INSERT INTO comment (blog_id, user_id, comment) VALUES (?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                comment.getBlogs().getId(),
                comment.getUser().getId(),
                comment.getComment()
        );
    }

    public int deleteComment(int id) {
        String sql = "DELETE FROM comment WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
