package com.treeleaf.blog.repository;

import com.treeleaf.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogsRepository extends JpaRepository<User, Long> {
}
