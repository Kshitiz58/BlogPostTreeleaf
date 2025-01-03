package com.treeleaf.blog.service;

import com.treeleaf.blog.entity.User;

public interface UserService {
    User loginUser(String email, String password);
    int registerUser(User user);
    User getUserById(int id);
    int updateUser(User user);
    int deleteUser(int id);
}
