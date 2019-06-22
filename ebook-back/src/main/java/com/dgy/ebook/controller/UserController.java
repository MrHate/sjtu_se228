package com.dgy.ebook.controller;

import com.dgy.ebook.entity.UserInfo;
import com.dgy.ebook.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value="/users")
public class UserController{
	@Autowired
	private UserService userService;

	@GetMapping(value="/all")
	public List<UserInfo> getAllUsers(){
		return userService.getAllUsers();
	}

	@PostMapping(value="/register",produces="application/json")
	public boolean register(@RequestParam String username,@RequestParam String password,@RequestParam String email){
		return userService.createUser(username,password,email);
	}

	@GetMapping(value="/current",produces="application/json")
	public String getCurrentUsername(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();
		return userDetails.getUsername();
	}

	@GetMapping(value="/enabled")
	public boolean isEnabled(){
		return userService.isUserEnabled(getCurrentUsername());
	}

	@GetMapping(value="/set-user-enabled")
	public boolean setEnabled(@RequestParam String username,@RequestParam boolean enabled){
		userService.setUserEnabled(username,enabled);
		return true;
	}
}
