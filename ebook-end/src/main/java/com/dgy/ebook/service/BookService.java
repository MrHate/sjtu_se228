package com.dgy.ebook.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
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

	public List<BookInfo> getBookList(){
		ArrayList<BookInfo> res = new ArrayList();
		for(BookInfo info : bookRepository.findAll()){
			res.add(info);
		}
		return res;
	}
	
	public BookInfo getBook(int id){
		return bookRepository.findById(id).get();
	}

	public String getBookWithImageAsJSON(int id,boolean withImg){
		BookInfo book = bookRepository.findById(id).get();
		if(book == null)return "not found";

		JSONObject obj = new JSONObject();
		obj.put("id",book.getId());
		obj.put("name",book.getName());
		obj.put("price",book.getPrice());
		obj.put("description",book.getDesp());
		obj.put("quantity",book.getQuantity());
		obj.put("isbn",book.getIsbn());
		obj.put("author",book.getAuthor());
		if(withImg){
			obj.put("img",getImg(book.getId()));
		}

		return obj.toJSONString();
    }

	public BookInfo updateBook(BookInfo info){
		return bookRepository.save(info);
	}

	public void removeBook(int id){
		bookRepository.deleteById(id);
		imageRepository.deleteById(id);
	}

	public String getImg(int id){
		String bytes = imageRepository.findById(id).get().getImg();
		return bytes;
	}

	public boolean updateImage(BookImage img){
		imageRepository.save(img);
		return true;
	}

}
