package com.dgy.ebook.controller;

import com.dgy.ebook.service.UserService;

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

}
