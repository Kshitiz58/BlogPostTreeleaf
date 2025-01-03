package com.treeleaf.blog.config;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class CustomUserDetails implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private String userType;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String email, String password, String userType) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.authorities = determineAuthorities(userType);
    }

    public Collection<? extends GrantedAuthority> determineAuthorities(String userType) {
        switch (userType.toUpperCase()) {
            case "ADMIN":
                return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
            case "USER":
                return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities != null ? this.authorities : List.of();
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
