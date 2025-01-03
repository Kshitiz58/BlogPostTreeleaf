package com.treeleaf.blog.config;

import com.treeleaf.blog.entity.User;
import com.treeleaf.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = usersRepository.findByEmail(email);

        if (user != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase());

            CustomUserDetails customUserDetails = new CustomUserDetails(
//                    user.getId(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole()
            );
            customUserDetails.setAuthorities(Collections.singleton(authority));
            return customUserDetails;
        } else {
            throw new UsernameNotFoundException("Invalid user account, please verify your email before login.");
        }
    }
}
