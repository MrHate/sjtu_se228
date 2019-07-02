package com.dgy.ebook.service;

import java.util.List;

import com.dgy.ebook.entity.UserInfo;

public interface UserService{
	public List<UserInfo> getAllUsers();
	public UserInfo getUserInfo(String username);
	public int getIdForUser(String username);
	public String getAvatarForUser(String username);
	public String getPasswordForUser(String username);
	public boolean isUserEnabled(String username);
	public boolean isUserAdmin(String username);

	public boolean createUser(String username,String password,String email);
	public void setUserEnabled(String username,boolean enabled);
	public void setUserAdmin(String username,boolean admin);
	public void updateUserAvatar(String username,String avatar);
	public boolean updateUserPassword(String username,String password,String newPassword);
}
