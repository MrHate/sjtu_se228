package com.dgy.ebook.repository;

import java.util.List;

import com.dgy.ebook.entity.UserInfo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserInfo, Integer> {

	List<UserInfo> findByUsername(String username);
}
