package com.dgy.ebook.controller;

import java.util.List;

import com.dgy.ebook.entity.UserInfo;
import com.dgy.ebook.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@GetMapping(value="/profile")
	public UserInfo getUserInfo(String username){
		return userService.getUserInfo(username);
	}

	@PostMapping(value="/profile")
	public String updateUserPassword(@RequestParam String username,@RequestParam String password,@RequestParam String newPassword,@RequestParam String repeatPassword){
		if(!newPassword.equals(repeatPassword)){
			return "repeated password not match";
		}
		if(userService.updateUserPassword(username,password,newPassword)){
			return "update successfully";
		}
		return "failed to update";
	}

	@GetMapping(value="/avatar")
	public String getAvatarForUser(@RequestParam String username){
		return userService.getAvatarForUser(username);
	}

	@PostMapping(value="/avatar")
	public String updateAvatarForUser(@RequestParam String username,@RequestBody UserInfo body){
		userService.updateUserAvatar(username,body.getAvatar());
		return "update avatar";
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

	@GetMapping(value="/isAdmin")
	public boolean isAdmin(){
		return userService.isUserAdmin(getCurrentUsername());
	}

	@GetMapping(value="/set-user-admin")
	public boolean setAdmin(@RequestParam String username,@RequestParam boolean isAdmin){
		userService.setUserAdmin(username,isAdmin);
		return true;
	}
}
