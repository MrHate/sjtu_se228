package com.dgy.ebook.service;

import java.util.List;

import com.dgy.ebook.entity.UserInfo;

public interface UserService{
	public List<UserInfo> getAllUsers();
	public int getIdForUser(String username);
	public String getPasswordForUser(String username);
	public boolean createUser(String username,String password,String email);
	public void setUserEnabled(String username,boolean enabled);
	public boolean isUserEnabled(String username);
}
