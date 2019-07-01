package com.dgy.ebook.configuration;

import com.dgy.ebook.dao.UserDao;
import com.dgy.ebook.entity.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminConfig {
	@Autowired
	private UserDao userDao;

	@Bean
	void createAdminAccount(){
		if(userDao.findByUsername("admin").isEmpty()){
			UserInfo admin = new UserInfo();
			admin.setId(1);
			admin.setUsername("admin");
			admin.setPassword("123");
			admin.setEnabled(true);
			admin.setEmail("dgyhateyou@sjtu.edu.cn");
			userDao.save(admin);
		}

	}
}
