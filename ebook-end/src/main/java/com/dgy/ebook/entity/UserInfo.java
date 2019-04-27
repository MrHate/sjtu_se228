package com.dgy.ebook.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Entity
@Data
public class UserInfo{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String username;

	private String password;

	private String email;

	private boolean enabled;
}
