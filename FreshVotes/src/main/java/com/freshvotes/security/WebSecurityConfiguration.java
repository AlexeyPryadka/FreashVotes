package com.freshvotes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
		
/*		auth.inMemoryAuthentication().passwordEncoder(getPasswordEncoder()).withUser("user")
		.password(getPasswordEncoder().encode("1234")).roles("USER");*/		
		
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//.csrf().disable().
		http.authorizeRequests().antMatchers("/", "/index","/register").permitAll().anyRequest().hasRole("USER")
				.and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/dashboard").and().logout().logoutUrl("/logout").permitAll();
	}
}
