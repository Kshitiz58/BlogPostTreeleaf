package com.treeleaf.blog.config;

import com.treeleaf.blog.repository.NewUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NewUserService implements UserDetailsService {

	@Autowired
	private NewUserRepository newUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.treeleaf.blog.entity.User user = newUserRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

		String[] roles = user.getRole() == null ? new String[]{"USER"} : user.getRole().split(",");

		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.roles(roles)
				.build();
	}
}

