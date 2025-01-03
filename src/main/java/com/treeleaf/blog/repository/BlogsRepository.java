package com.treeleaf.blog.repository;

import com.treeleaf.blog.entity.Blogs;
import com.treeleaf.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlogsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Blogs findBlogById(int id) {
        String sql = "SELECT * FROM blogs WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Blogs.class),
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Blogs> findBlogsByUser(User user) {
        String sql = "SELECT * FROM blogs WHERE user_id = ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{user.getId()},
                new BeanPropertyRowMapper<>(Blogs.class)
        );
    }

    public int createBlog(Blogs blog) {
        String sql = "INSERT INTO blogs (id, user_id, blog_title, blog_description, thumbnail_image_url, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                blog.getId(),
                blog.getUser().getId(),
                blog.getBlogTitle(),
                blog.getBlogDescription(),
                blog.getThumbnailImageUrl(),
                blog.getCreatedAt()
        );
    }

    public int updateBlog(Blogs blog) {
        String sql = "UPDATE blogs SET blog_title = ?, blog_description = ?, thumbnail_image_url = ?, created_at = ? WHERE id = ?";
        return jdbcTemplate.update(
                sql,
                blog.getBlogTitle(),
                blog.getBlogDescription(),
                blog.getThumbnailImageUrl(),
                blog.getCreatedAt(),
                blog.getId()
        );
    }

    public List<Blogs> findBlogsByTitle(String title) {
        String sql = "SELECT * FROM blogs WHERE blog_title LIKE ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{"%" + title + "%"},
                new BeanPropertyRowMapper<>(Blogs.class)
        );
    }

    public int deleteBlog(int id) {
        String sql = "DELETE FROM blogs WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
