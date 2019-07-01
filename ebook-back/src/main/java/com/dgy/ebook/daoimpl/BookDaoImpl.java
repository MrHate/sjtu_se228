package com.dgy.ebook.daoimpl;

import java.util.List;

import com.dgy.ebook.dao.BookDao;
import com.dgy.ebook.entity.BookImage;
import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.repository.BookRepository;
import com.dgy.ebook.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookDaoImpl implements BookDao {
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private ImageRepository imageRepository;

	public List<BookInfo> findAll(){
		return bookRepository.findAll();
	}

	public BookInfo findById(int id){
		return bookRepository.findById(id).get();
	}

	public BookInfo save(BookInfo book){
		return bookRepository.save(book);
	}

	public void deleteById(int id){
		bookRepository.deleteById(id);
		imageRepository.deleteById(id);
	}

	public String findImageById(int id){
		return imageRepository.findById(id).get().getImg();
	}

	public BookImage saveImage(BookImage img){
		return imageRepository.save(img);
	}
}
