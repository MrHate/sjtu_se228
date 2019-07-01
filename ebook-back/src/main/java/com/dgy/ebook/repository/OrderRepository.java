package com.dgy.ebook.repository;

import java.util.List;

import com.dgy.ebook.entity.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderItem, Integer> {
	public List<OrderItem> findByBid(int bid);
}

