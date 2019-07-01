package com.dgy.ebook.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.dgy.ebook.dao.BookDao;
import com.dgy.ebook.entity.BookImage;
import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{
	@Autowired
	private BookDao bookDao;
	//@Autowired
	//private ImageRepository imageRepository;

	public List<BookInfo> getBookList(){
		ArrayList<BookInfo> res = new ArrayList();
		for(BookInfo info : bookDao.findAll()){
			res.add(info);
		}
		return res;
	}
	
	public String getBookWithImageAsJSON(int id,boolean withImg){
		BookInfo book = bookDao.findById(id);
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
		return bookDao.save(info);
	}

	public void removeBook(int id){
		bookDao.deleteById(id);
	}

	public String getImg(int id){
		String bytes = bookDao.findImageById(id);
		return bytes;
	}

	public boolean updateImage(BookImage img){
		bookDao.saveImage(img);
		return true;
	}

}

