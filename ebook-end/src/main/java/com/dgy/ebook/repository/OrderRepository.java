package com.dgy.ebook.repository;

import java.util.List;

import com.dgy.ebook.entity.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface OrderRepository extends JpaRepository<OrderItem, Integer> {

}

