package com.freshvotes.services;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserDetailsServiceImplTest {

	@Test
	void test() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = "Opera244";
		String encode = encoder.encode(password);
		
		System.out.println(encode);
		
		assertThat(password, not(encode));
	}

}
