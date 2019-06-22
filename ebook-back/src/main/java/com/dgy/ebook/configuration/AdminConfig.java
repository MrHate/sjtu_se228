package com.dgy.ebook.configuration;

import com.dgy.ebook.entity.UserInfo;
import com.dgy.ebook.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminConfig {
	@Autowired
	private UserRepository userRepository;

	@Bean
	void createAdminAccount(){
		if(userRepository.findByUsername("admin").isEmpty()){
			UserInfo admin = new UserInfo();
			admin.setId(0);
			admin.setUsername("admin");
			admin.setPassword("123");
			admin.setEnabled(true);
			admin.setEmail("dgyhateyou@sjtu.edu.cn");
			userRepository.save(admin);
		}

	}
}
