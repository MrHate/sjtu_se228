package com.dgy.ebook.repository;

import com.dgy.ebook.entity.BookImage;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<BookImage, Integer> {

}

