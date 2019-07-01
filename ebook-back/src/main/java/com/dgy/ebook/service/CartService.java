package com.dgy.ebook.service;

public interface CartService{
	public String getCartForUser(String username);
	public void updateItem(String username,int bid,int quantity);
	public void deleteItem(String username,int bid);
	public void deleteByUsername(String username);
	public boolean addToCart(String username,int bid);
	public String clearCart(String username);
}

