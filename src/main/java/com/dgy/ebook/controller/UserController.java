package com.dgy.ebook.controller;

import com.dgy.ebook.DbUtil;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/users")
public class UserController{

	@RequestMapping(value="/register/{s1}&{s2}",method=RequestMethod.GET,produces="application/json")
	public boolean register(@PathVariable String s1,@PathVariable String s2){
		return DbUtil.getInstance().createUser(s1,s2);
	}

	@RequestMapping(value="/current",method=RequestMethod.GET,produces="application/json")
	public String getCurrentUsername(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();
		return userDetails.getUsername();
	}

}
