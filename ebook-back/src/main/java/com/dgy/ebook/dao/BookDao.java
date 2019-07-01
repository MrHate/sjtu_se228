package com.dgy.ebook.dao;

import java.util.List;

import com.dgy.ebook.entity.BookImage;
import com.dgy.ebook.entity.BookInfo;

public interface BookDao {
	public List<BookInfo> findAll();
	public BookInfo findById(int id);
	public BookInfo save(BookInfo book);
	public void deleteById(int id);

	public String findImageById(int id);
	public BookImage saveImage(BookImage img);
}
