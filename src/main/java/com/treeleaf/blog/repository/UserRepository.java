package com.treeleaf.blog.repository;

import com.treeleaf.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(User.class),
                    email
            );
        } catch (RuntimeException e) {
            return null;
        }
    }


    public User getUserById(int id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (rs, rowNum) -> {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setAddress(rs.getString("address"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
        );
    }

    public int createUser(User user) {
        String sql = "INSERT INTO user (id, full_name, address, email, password, role) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                user.getId(),
                user.getFullName(),
                user.getAddress(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    public int updateUser(User user) {
        String sql = "UPDATE user SET full_name = ?, address = ?, email = ?, password = ?, role = ? WHERE id = ?";
        return jdbcTemplate.update(
                sql,
                user.getFullName(),
                user.getAddress(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getId()
        );
    }

    public int deleteUser(int id) {
        String sql = "DELETE FROM user WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
