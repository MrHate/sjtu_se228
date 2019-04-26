package com.dgy.ebook.service;

import java.util.List;

import com.dgy.ebook.entity.CartItem;
import com.dgy.ebook.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService{
	@Autowired 
	private CartRepository repository;

	public List<CartItem> getCartForUser(String username){
		return repository.findByUsername(username);
	}

	public void updateItem(String username,int bid,int quantity){
		CartItem ci = new CartItem();
		for(CartItem item : repository.findByUsername(username)){
			if(item.getBid() == bid){
				ci = item;
				break;
			}
		}
		ci.setUsername(username);
		ci.setBid(bid);
		ci.setQuantity(quantity);

		repository.save(ci);
	}

	public boolean deleteItem(String username,int bid){
		for(CartItem item : repository.findByUsername(username)){
			if(item.getBid() == bid){
				repository.deleteById(item.getId());
				return true;
			}
		}

		return false;
	}

	public void deleteByUsername(String username){
		for(CartItem item : repository.findByUsername(username)){
			repository.deleteById(item.getId());
		}
	}

}

