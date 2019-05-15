package com.dgy.ebook.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.entity.OrderItem;
import com.dgy.ebook.repository.BookRepository;
import com.dgy.ebook.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class OrderService{
	@Autowired 
	private OrderRepository orderRepository;
	@Autowired
	private BookRepository bookRepository;

	private String joinBookInfo(List<OrderItem> items){
		ArrayList<String> res = new ArrayList();
		for(OrderItem item : items){
			BookInfo book = bookRepository.findById(item.getBid()).get();
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

	public String getOrderForUser(String username){
		return joinBookInfo(orderRepository.findByUsername(username));
	}

	public String getOrdersAll(){
		return joinBookInfo(orderRepository.findAll());
	}
	public void updateItem(String username,int bid,int quantity,double price){
		OrderItem ci = new OrderItem();
		ci.setUsername(username);
		ci.setBid(bid);
		ci.setQuantity(quantity);
		ci.setPrice(price);
		ci.setDate(new Date());

		orderRepository.save(ci);
	}

	public boolean deleteItem(String username,int bid){
		for(OrderItem item : orderRepository.findByUsername(username)){
			//log.info(">deleteItem find: "+item.getUsername()+"/"+item.getBid());
			if(item.getBid() == bid){
				orderRepository.deleteById(item.getId());
				return true;
			}
		}

		return false;
	}

	public void deleteByUsername(String username){
		for(OrderItem item : orderRepository.findByUsername(username)){
			orderRepository.deleteById(item.getId());
		}
	}

}
