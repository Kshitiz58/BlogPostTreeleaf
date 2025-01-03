package com.treeleaf.blog.repository;

import java.util.Optional;

import com.treeleaf.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewUserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
}
