package com.dgy.ebook.service;

import java.util.List;

import com.dgy.ebook.entity.OrderBatch;

public interface OrderService{
	public String getOrderForUser(String username);
	public String getOrdersAll();
	public boolean deleteItem(int id);
}
