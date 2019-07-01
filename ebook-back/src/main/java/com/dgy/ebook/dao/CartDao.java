package com.dgy.ebook.dao;

import java.util.List;

import com.dgy.ebook.entity.CartItem;

public interface CartDao {
	public List<CartItem> findByUsername(String username);
	public CartItem findByUsernameAndBid(String username,int bid);
	public CartItem save(CartItem item);
	public void delete(CartItem item);
	public void deleteInBatch(List<CartItem> items);
}
