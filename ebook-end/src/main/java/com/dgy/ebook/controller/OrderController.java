package com.dgy.ebook.controller;

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

	@GetMapping
	public String getOrdersForUser(@RequestParam String username){
		return orderService.getOrderForUser(username).toString();
	}

	@GetMapping(value="/all")
	public String getOrdersAll(){
		return orderService.getOrdersAll().toString();
	}

	//@PostMapping
	//public String updateOrderItem(@RequestParam String username,@RequestParam int bid,@RequestParam int quantity){
	//    orderService.updateItem(username,bid,quantity);
	//    return "post";
	//}

	@DeleteMapping
	public String removeOrderItem(@RequestParam String username,@RequestParam int bid){
		if(!orderService.deleteItem(username,bid)){
			log.warn(">delete failed");
		}

		return "delete";
	}

}

