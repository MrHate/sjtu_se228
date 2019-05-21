package com.dgy.ebook.service;

public interface UserService{
	public int getIdForUser(String username);
	public String getPasswordForUser(String username);
	public boolean createUser(String username,String password,String email);
	public void setUserEnabled(String username,boolean enabled);
	public boolean isUserEnabled(String username);
}
