package com.dgy.ebook.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dgy.ebook.entity.BookImage;
import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(path="/books")
public class BookController{
	@Autowired
	private BookService bookService;

	@GetMapping
	public @ResponseBody String getBook(@RequestParam int id,@RequestParam boolean withImg){
		try{
			JSONObject obj = new JSONObject();
			obj.put("id",id);

			if(id == -1){
				obj.put("book_num", bookService.getBookNum()+1);
			}else{
				BookInfo book = bookService.getBook(id);
				obj.put("id",book.getId());
				obj.put("name",book.getName());
				obj.put("price",book.getPrice());
				obj.put("description",book.getDesp());
				obj.put("quantity",book.getQuantity());
				obj.put("isbn",book.getIsbn());
				obj.put("author",book.getAuthor());
				if(withImg){
					obj.put("img",bookService.getImg(book.getId()));
				}
			}

			return obj.toString();
		}catch(Exception e){
			return "failed to get";
		}
	}

	@PostMapping
	public @ResponseBody String updateBook(HttpServletRequest request,HttpServletResponse response){
		int id = Integer.valueOf(request.getParameter("id"));
		if(id == -1){
			id = bookService.getBookNum()+1;
		}
		
		String name = request.getParameter("name");
		String isbn = request.getParameter("isbn");
		String author = request.getParameter("author");
		double price = Double.valueOf(request.getParameter("price"));
		int quantity = Integer.valueOf(request.getParameter("quantity"));
		String desp = request.getParameter("desp");
		boolean withImg = Boolean.parseBoolean(request.getParameter("withImg"));

		BookInfo info = new BookInfo();
		info.setId(id);
		info.setName(name);
		info.setIsbn(isbn);
		info.setAuthor(author);
		info.setPrice(price);
		info.setQuantity(quantity);
		info.setDesp(desp);

		bookService.updateBook(info);
		if(withImg){
			BookImage image = new BookImage();
			image.setId(id);
			image.setImg(request.getParameter("img").getBytes());
			bookService.updateImage(image);
		}
		return "post";
	}

	@DeleteMapping
	public @ResponseBody String removeBook(@RequestParam int id){
		//int id = Integer.valueOf(request.getParameter("id"));
		bookService.removeBook(id);
		return "delete";
	}
}
