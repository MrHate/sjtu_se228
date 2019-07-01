package com.dgy.ebook.repository;

import java.util.List;

import com.dgy.ebook.entity.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartItem, Integer> {

	List<CartItem> findByUsername(String username);

	CartItem findByUsernameAndBid(String username,int bid);

	//@Query("delete from CartItem item where item.username = ?1 and item.bid = ?2")
	//void deleteByUsernameAndBid(String username,int bid);

	//void deleteByUsername(String username);
}

