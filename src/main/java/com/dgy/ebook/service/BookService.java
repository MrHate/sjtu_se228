package com.dgy.ebook.service;

import com.dgy.ebook.entity.BookImage;
import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.repository.BookRepository;
import com.dgy.ebook.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService{
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private ImageRepository imageRepository;

	public BookInfo getBook(int id){
		return bookRepository.findById(id).get();
    }

	public int getBookNum(){
		int res = 0;
		for(BookInfo info : bookRepository.findAll()){
			res = info.getId();
		}
		return res;
	}

	public void updateBook(BookInfo info){
		bookRepository.save(info);
	}

	public void removeBook(int id){
		bookRepository.deleteById(id);
		imageRepository.deleteById(id);
	}

	public String getImg(int id){
		byte[] bytes = imageRepository.findById(id).get().getImg();
		return new String(bytes);
	}

	public boolean updateImage(BookImage img){
		imageRepository.save(img);
		return true;
	}

}
