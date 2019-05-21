package com.dgy.ebook.serviceImpl;

import java.util.Collections;

import com.dgy.ebook.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EbookUserDetailsServiceImpl implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(EbookUserDetailsServiceImpl.class);

	@Autowired
	private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		String password = userService.getPasswordForUser(username);
        if (password == null) {
            throw new UsernameNotFoundException(username);
        }
		logger.info(">loadUserByUsername: "+username+":"+password);
        return new User(username, passwordEncoder().encode(password), Collections.singletonList(new SimpleGrantedAuthority("User")));
    }

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
