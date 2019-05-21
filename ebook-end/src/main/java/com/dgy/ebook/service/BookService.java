package com.dgy.ebook.service;

import java.util.List;

import com.dgy.ebook.entity.BookImage;
import com.dgy.ebook.entity.BookInfo;

public interface BookService{
	public List<BookInfo> getBookList();
	public String getBookWithImageAsJSON(int id,boolean withImg);
	public BookInfo updateBook(BookInfo info);
	public void removeBook(int id);
	public String getImg(int id);
	public boolean updateImage(BookImage img);
}
