package com.dgy.ebook.serviceImpl;

import com.dgy.ebook.entity.UserInfo;
import com.dgy.ebook.repository.UserRepository;
import com.dgy.ebook.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
	@Autowired 
	private UserRepository userRepository;

	public int getIdForUser(String username){
		int res = -1;

		for(UserInfo n : userRepository.findByUsername(username)){
			res = n.getId();
		}
		return res;
	}

	public String getPasswordForUser(String username){
		String res = null;

		for(UserInfo n : userRepository.findByUsername(username)){
			res = n.getPassword();
		}
		return res;
	}

	public boolean createUser(String username,String password,String email){
		for(UserInfo n : userRepository.findByUsername(username)){
			return false;
		}

		UserInfo n = new UserInfo();
		n.setUsername(username);
		n.setPassword(password);
		n.setEmail(email);
		n.setEnabled(true);

		int id = 1;
		while(userRepository.existsById(id)){
			++id;
		}
		n.setId(id);

		userRepository.save(n);

		return true;
	}

	public void setUserEnabled(String username,boolean enabled){
		for(UserInfo n : userRepository.findByUsername(username)){
			n.setEnabled(enabled);
			userRepository.save(n);
			return;
		}
	}

	public boolean isUserEnabled(String username){
		for(UserInfo n : userRepository.findByUsername(username)){
			return n.isEnabled();
		}
		return false;
	}

}

