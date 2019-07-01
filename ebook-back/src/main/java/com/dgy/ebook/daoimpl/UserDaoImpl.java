package com.dgy.ebook.daoimpl;

import java.util.List;

import com.dgy.ebook.dao.UserDao;
import com.dgy.ebook.entity.UserInfo;
import com.dgy.ebook.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDao {
	@Autowired
	private UserRepository userRepository;

	public List<UserInfo> findAll(){
		return userRepository.findAll();
	}

	public List<UserInfo> findByUsername(String username){
		return userRepository.findByUsername(username);
	}

	public boolean existsById(int id){
		return userRepository.existsById(id);
	}

	public UserInfo save(UserInfo user){
		return userRepository.save(user);
	}
}
