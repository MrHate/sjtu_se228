package com.dgy.ebook.serviceImpl;

import java.util.List;

import com.dgy.ebook.dao.UserDao;
import com.dgy.ebook.entity.UserInfo;
import com.dgy.ebook.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
	@Autowired 
	private UserDao userDao;

	public List<UserInfo> getAllUsers(){
		return userDao.findAll();
	}

	public UserInfo getUserInfo(String username){
		for(UserInfo n : userDao.findByUsername(username)){
			return n;
		}
		return null;
	}

	public int getIdForUser(String username){
		int res = -1;

		for(UserInfo n : userDao.findByUsername(username)){
			res = n.getId();
		}
		return res;
	}

	public String getAvatarForUser(String username){
		String res = null;

		for(UserInfo n : userDao.findByUsername(username)){
			res = n.getAvatar();
		}
		return res;
	}

	public String getPasswordForUser(String username){
		String res = null;

		for(UserInfo n : userDao.findByUsername(username)){
			res = n.getPassword();
		}
		return res;
	}

	public boolean createUser(String username,String password,String email){
		for(UserInfo n : userDao.findByUsername(username)){
			return false;
		}

		UserInfo n = new UserInfo();
		n.setUsername(username);
		n.setPassword(password);
		n.setEmail(email);
		n.setEnabled(true);
		n.setAdmin(false);

		int id = 2;
		while(userDao.existsById(id)){
			++id;
		}
		n.setId(id);

		userDao.save(n);

		return true;
	}

	public void setUserAdmin(String username,boolean isAdmin){
		for(UserInfo n : userDao.findByUsername(username)){
			n.setAdmin(isAdmin);
			userDao.save(n);
			return;
		}
	}

	public boolean isUserAdmin(String username){
		for(UserInfo n : userDao.findByUsername(username)){
			return n.isAdmin();
		}
		return false;
	}

	public void setUserEnabled(String username,boolean enabled){
		for(UserInfo n : userDao.findByUsername(username)){
			n.setEnabled(enabled);
			userDao.save(n);
			return;
		}
	}

	public boolean isUserEnabled(String username){
		for(UserInfo n : userDao.findByUsername(username)){
			return n.isEnabled();
		}
		return false;
	}

	public void updateUserAvatar(String username,String avatar){
		for(UserInfo n : userDao.findByUsername(username)){
			n.setAvatar(avatar);
			userDao.save(n);
			return;
		}
	}

	public boolean updateUserPassword(String username,String password,String newPassword){
		for(UserInfo n : userDao.findByUsername(username)){
			if(!n.getPassword().equals(password)){return false;}
			n.setPassword(newPassword);
			userDao.save(n);
			return true;
		}
		return false;
	}
}

