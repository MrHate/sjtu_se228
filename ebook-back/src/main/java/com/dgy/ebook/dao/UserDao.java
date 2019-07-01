package com.dgy.ebook.dao;

import java.util.List;

import com.dgy.ebook.entity.UserInfo;

public interface UserDao {
	public List<UserInfo> findAll();
	public List<UserInfo> findByUsername(String username);
	public boolean existsById(int id);
	public UserInfo save(UserInfo user);
}
