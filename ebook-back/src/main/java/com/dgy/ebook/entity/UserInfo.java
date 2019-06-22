package com.dgy.ebook.entity;

import javax.persistence.Id;

import lombok.Data;

@Data
public class UserInfo{
	@Id
	private int id;

	private String username;
	private String password;
	private String email;
	private boolean enabled;
	private String avatar;
}
