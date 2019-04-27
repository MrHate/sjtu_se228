package com.dgy.ebook.controller;

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
		if(!cartService.deleteItem(username,bid)){
			log.warn(">delete failed");
		}

		return "delete";
	}

	@GetMapping(value="clear")
	public String clearCart(@RequestParam String username){
		cartService.deleteByUsername(username);
		return "clear";
	}
}
