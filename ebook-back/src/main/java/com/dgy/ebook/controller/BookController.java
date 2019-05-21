package com.dgy.ebook.controller;

import com.dgy.ebook.entity.BookImage;
import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/books")
public class BookController{
	@Autowired
	private BookService service;

	@GetMapping(value="/all")
	public String getBookList(){
		return service.getBookList().toString();
	}

	@GetMapping
	public String getBook(@RequestParam int id,@RequestParam boolean withImg){
		return service.getBookWithImageAsJSON(id,withImg);
	}

	@GetMapping(value="/image")
	public String getBookImage(@RequestParam int id){
		return service.getImg(id);
	}

	@PostMapping(value="/image")
	public String updateBookImage(@RequestBody BookImage img){
		service.updateImage(img);
		return "post to image";
	}

	@PostMapping
	public int updateBook(@RequestBody BookInfo info){
		int res = service.updateBook(info).getId();
		return res;
	}

	@DeleteMapping
	public String removeBook(@RequestParam int id){
		//int id = Integer.valueOf(request.getParameter("id"));
		service.removeBook(id);
		return "delete";
	}
}
