package com.dgy.ebook.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {

	@RequestMapping("/loginsuccess")
	public String loginSuccess(){
		return "success";
	}
	@RequestMapping("/unauthorized")
	public String unauthorized(){
		return "unauthorized";
	}

}
