package com.treeleaf.blog.serviceImpl;

import com.treeleaf.blog.entity.User;
import com.treeleaf.blog.exceptions.ResourceNotFoundException;
import com.treeleaf.blog.repository.UserRepository;
import com.treeleaf.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User loginUser(String email, String password) {
        try {
            User user = userRepository.findByEmail(email);
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            } else {
                throw new ResourceNotFoundException("Bad Credentials.");
            }
        } catch (ResourceNotFoundException | IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error:" + e.getMessage(), e);
        }
    }

    @Override
    public int registerUser(User user) {
        try {
            return userRepository.createUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    public User getUserById(int id) {
        try {
            return userRepository.getUserById(id);
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    public int updateUser(User user) {
        try {
            User existingUser = userRepository.getUserById((user.getId()));
            if (existingUser == null) {
                throw new ResourceNotFoundException("User not found with ID: " + user.getId());
            }
            return userRepository.updateUser(user);
        }catch (ResourceNotFoundException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    public int deleteUser(int id) {
        try {
            return userRepository.deleteUser(id);
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }
}
