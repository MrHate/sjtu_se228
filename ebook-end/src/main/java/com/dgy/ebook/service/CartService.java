package com.dgy.ebook.service;

import java.util.List;

import com.dgy.ebook.entity.CartItem;
import com.dgy.ebook.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CartService{
	@Autowired 
	private CartRepository repository;

	public List<CartItem> getCartForUser(String username){
		return repository.findByUsername(username);
	}

	public void updateItem(String username,int bid,int quantity){
		CartItem ci = repository.findByUsernameAndBid(username,bid);
		if(ci == null){
			ci = new CartItem();
			ci.setUsername(username);
			ci.setBid(bid);
		}
		ci.setQuantity(quantity);
		repository.save(ci);
	}

	public void deleteItem(String username,int bid){
		repository.deleteByUsernameAndBid(username,bid);
	}

	public void deleteByUsername(String username){
		repository.deleteByUsername(username);
	}
}

