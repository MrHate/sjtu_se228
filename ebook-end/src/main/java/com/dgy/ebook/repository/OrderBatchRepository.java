package com.dgy.ebook.repository;

import java.util.List;

import com.dgy.ebook.entity.OrderBatch;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBatchRepository extends JpaRepository<OrderBatch, Integer> {

	List<OrderBatch> findByUsername(String username);
}


