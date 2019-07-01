package com.dgy.ebook.dao;

import java.util.List;

import com.dgy.ebook.entity.OrderBatch;
import com.dgy.ebook.entity.OrderItem;

public interface OrderDao {
	public List<OrderBatch> findByUsername(String username);
	public List<OrderBatch> findAll();
	public void deleteItemById(int id);
	public OrderBatch save(OrderBatch batch);
	public List<OrderItem> findItemByBid(int bookId);
}
