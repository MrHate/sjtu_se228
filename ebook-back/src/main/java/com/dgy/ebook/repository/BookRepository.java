package com.dgy.ebook.repository;

import com.dgy.ebook.entity.BookInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookInfo, Integer> {

}
