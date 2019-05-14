package com.dgy.ebook.controller;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
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

	private String joinBookInfo(List<CartItem> items){
		ArrayList<String> res = new ArrayList();
		for(CartItem item : items){
			BookInfo book = bookService.getBook(item.getBid());
			JSONObject jobj = new JSONObject();
			jobj.put("id",item.getId());
			jobj.put("bid",item.getBid());
			jobj.put("username",item.getUsername());
			jobj.put("bookname",book.getName());
			jobj.put("isbn",book.getIsbn());
			jobj.put("quantity",item.getQuantity());
			jobj.put("price",book.getPrice());
			res.add(jobj.toJSONString());
		}
		return res.toString();
	}

	@GetMapping
	public String getCart(@RequestParam String username){
		return joinBookInfo(cartService.getCartForUser(username));
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
		cartService.deleteItem(username,bid);
		return "delete";
	}

	@GetMapping(value="clear")
	public String clearCart(@RequestParam String username){
		for(CartItem ci : cartService.getCartForUser(username)){
			BookInfo book = bookService.getBook(ci.getBid());

			int bquantity = book.getQuantity();
			int cquantity = ci.getQuantity();
			int oquantity = cquantity;
			bquantity -= cquantity;

			if(bquantity < 0){
				oquantity += bquantity;
				bquantity = 0;
			}
			book.setQuantity(bquantity);
			bookService.updateBook(book);
			orderService.updateItem(username,ci.getBid(),oquantity,ci.getQuantity() * book.getPrice());
		}
		cartService.deleteByUsername(username);
		return "clear";
	}
}
