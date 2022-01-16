package com.freshvotes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.freshvotes.domain.User;
import com.freshvotes.repositories.UserRepository;
import com.freshvotes.security.Authority;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}


	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Authority authority = new Authority();
		authority.setAuthority("ROLE_USER");
		authority.setId(user.getId());
		authority.setUser(user);
		user.getAuthorities().add(authority);
		System.out.println(user);
		return userRepository.save(user);
	}

}
