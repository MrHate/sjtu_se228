package com.dgy.ebook.controller;

import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.entity.CartItem;
import com.dgy.ebook.service.BookService;
import com.dgy.ebook.service.CartService;
import com.dgy.ebook.service.OrderService;

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
	@Autowired
	private OrderService orderService;
	@Autowired
	private BookService bookService;

	@GetMapping
	public String getCart(@RequestParam String username){
		return cartService.getCartForUser(username).toString();
	}

	@GetMapping(value="/add")
	public String addToCart(@RequestParam String username,@RequestParam int bid){
		for(CartItem ci : cartService.getCartForUser(username)){
			if(ci.getBid()==bid){
				cartService.updateItem(username,bid,ci.getQuantity()+1);
				return "new cart entry";
			}
		}
		cartService.updateItem(username,bid,1);
		return "add to cart";
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
		for(CartItem ci : cartService.getCartForUser(username)){
			BookInfo book = bookService.getBook(ci.getBid());
			orderService.updateItem(username,ci.getBid(),ci.getQuantity(),ci.getQuantity() * book.getPrice());
		}
		cartService.deleteByUsername(username);
		return "clear";
	}
}
