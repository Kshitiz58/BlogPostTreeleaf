package com.treeleaf.blog.config;

import com.treeleaf.blog.entity.User;
import com.treeleaf.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(
                (long) user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
        customUserDetails.setAuthorities(customUserDetails.determineAuthorities(user.getRole()));
        return customUserDetails;
    }
}

