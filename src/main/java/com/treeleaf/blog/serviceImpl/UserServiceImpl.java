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
            if(user == null){
                throw new ResourceNotFoundException("User doesn't exist with this email: "+ email);
            }
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new ResourceNotFoundException("Bad Credentials.");
            }
            return user;
        } catch (ResourceNotFoundException | IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error:" + e.getMessage(), e);
        }
    }

    @Override
    public int registerUser(User user) {
        try {
            User existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser != null) {
                throw new IllegalArgumentException("User with the same email already exists.");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.createUser(user);
        }catch (IllegalArgumentException e){
            throw e;
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
                throw new ResourceNotFoundException("User not found with Id: " + user.getId());
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
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
