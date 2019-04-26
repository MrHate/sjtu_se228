package com.dgy.ebook.controller;

import com.dgy.ebook.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/cart")
public class CartController{
	@Autowired
	private CartService cartService;

	@GetMapping
	public String getCart(@RequestParam String username){
		return cartService.getCartForUser(username).toString();
	}

	@PostMapping
	public String updateCartItem(@RequestParam String username,@RequestParam int bid,@RequestParam int quantity){
		cartService.updateItem(username,bid,quantity);
		return "post";
	}

	@DeleteMapping
	public String removeCartItem(@RequestParam String username,@RequestParam int bid){
		cartService.deleteItem(username,bid);
		return "delete";
	}
}
