package com.dgy.ebook.daoimpl;

import java.util.List;

import com.dgy.ebook.dao.CartDao;
import com.dgy.ebook.entity.CartItem;
import com.dgy.ebook.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartDaoImpl implements CartDao {
	@Autowired
	private CartRepository cartRepository;

	public List<CartItem> findByUsername(String username){
		return cartRepository.findByUsername(username);
	}

	public CartItem findByUsernameAndBid(String username,int bid){
		return cartRepository.findByUsernameAndBid(username,bid);
	}

	public CartItem save(CartItem item){
		return cartRepository.save(item);
	}

	public void delete(CartItem item){
		cartRepository.delete(item);
	}

	public void deleteInBatch(List<CartItem> items){
		cartRepository.deleteInBatch(items);
	}
}
