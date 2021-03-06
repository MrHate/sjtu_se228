package com.dgy.ebook.controller;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.entity.OrderItem;
import com.dgy.ebook.service.BookService;
import com.dgy.ebook.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(path="/orders")
public class OrderController{
	@Autowired
	private OrderService orderService;
	@Autowired
	private BookService bookService;

	@GetMapping
	public String getOrdersForUser(@RequestParam String username){
		return orderService.getOrderForUser(username);
	}

	@GetMapping(value="/all")
	public String getOrdersAll(){
		return orderService.getOrdersAll();
	}

	//@PostMapping
	//public String updateOrderItem(@RequestParam String username,@RequestParam int bid,@RequestParam int quantity){
	//    orderService.updateItem(username,bid,quantity);
	//    return "post";
	//}

	@DeleteMapping
	public String removeOrderItem(@RequestParam int id){
		if(!orderService.deleteItem(id)){
			log.warn(">delete failed");
		}

		return "delete";
	}

}

