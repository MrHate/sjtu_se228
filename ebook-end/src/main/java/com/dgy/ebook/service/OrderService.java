package com.dgy.ebook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.dgy.ebook.entity.OrderItem;
import com.dgy.ebook.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class OrderService{
	@Autowired 
	private OrderRepository repository;

	public List<OrderItem> getOrderForUser(String username){
		return repository.findByUsername(username);
	}

	public List<OrderItem> getOrdersAll(){
		ArrayList<OrderItem> res = new ArrayList();
		for(OrderItem oi : repository.findAll()){
			res.add(oi);
		}
		return res;
	}
	public void updateItem(String username,int bid,int quantity,double price){
		OrderItem ci = new OrderItem();
		ci.setUsername(username);
		ci.setBid(bid);
		ci.setQuantity(quantity);
		ci.setPrice(price);
		ci.setDate(new Date());

		repository.save(ci);
	}

	public boolean deleteItem(String username,int bid){
		for(OrderItem item : repository.findByUsername(username)){
			//log.info(">deleteItem find: "+item.getUsername()+"/"+item.getBid());
			if(item.getBid() == bid){
				repository.deleteById(item.getId());
				return true;
			}
		}

		return false;
	}

	public void deleteByUsername(String username){
		for(OrderItem item : repository.findByUsername(username)){
			repository.deleteById(item.getId());
		}
	}

}


