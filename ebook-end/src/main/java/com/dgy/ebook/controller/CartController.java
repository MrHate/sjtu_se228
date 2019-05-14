package com.dgy.ebook.controller;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.entity.CartItem;
import com.dgy.ebook.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(path="/cart")
public class CartController{
	@Autowired
	private CartService service;

	@GetMapping
	public String getCart(@RequestParam String username){
		return service.getCartForUser(username);
	}

	@GetMapping(value="/add")
	public String addToCart(@RequestParam String username,@RequestParam int bid){
		service.addToCart(username,bid);
		return "add to cart";
	}

	@PostMapping
	public String updateCartItem(@RequestParam String username,@RequestParam int bid,@RequestParam int quantity){
		service.updateItem(username,bid,quantity);
		return "post";
	}

	@DeleteMapping
	public String removeCartItem(@RequestParam String username,@RequestParam int bid){
		service.deleteItem(username,bid);
		return "delete";
	}

	@GetMapping(value="clear")
	public String clearCart(@RequestParam String username){
		service.clearCart(username);
		return "clear";
	}
}
