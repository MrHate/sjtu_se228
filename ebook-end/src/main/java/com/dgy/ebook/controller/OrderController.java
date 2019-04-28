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

	private String joinBookInfo(List<OrderItem> items){
		ArrayList<String> res = new ArrayList();
		for(OrderItem item : items){
			BookInfo book = bookService.getBook(item.getBid());
			JSONObject jobj = new JSONObject();
			jobj.put("id",item.getId());
			jobj.put("bid",item.getBid());
			jobj.put("username",item.getUsername());
			jobj.put("bookname",book.getName());
			jobj.put("isbn",book.getIsbn());
			jobj.put("time",item.getFormatTime());
			jobj.put("quantity",item.getQuantity());
			jobj.put("price",item.getPrice());
			jobj.put("date",item.getDate());
			res.add(jobj.toJSONString());
		}
		return res.toString();
	}


	@GetMapping
	public String getOrdersForUser(@RequestParam String username){
		return joinBookInfo(orderService.getOrderForUser(username));
	}

	@GetMapping(value="/all")
	public String getOrdersAll(){
		return joinBookInfo(orderService.getOrdersAll());
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

