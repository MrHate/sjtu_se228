package com.dgy.ebook.repository;

import java.util.List;

import com.dgy.ebook.entity.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface CartRepository extends JpaRepository<CartItem, Integer> {

	List<CartItem> findByUsername(String username);

	//@Query("select item from CartItem item where item.username = ?1 and item.bid = ?2")
	CartItem findByUsernameAndBid(String username,int bid);

	//@Query("delete from CartItem where username = ?1 and bid = ?2")
	void deleteByUsernameAndBid(String username,int bid);

	void deleteByUsername(String username);
}

