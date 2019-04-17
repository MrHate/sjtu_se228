package com.dgy.ebook;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EbookUserDetailsServiceImpl implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(EbookUserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		String password = DbUtil.getInstance().getPasswordForUser(username);
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
