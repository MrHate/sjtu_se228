package com.dgy.ebook.service;

import com.dgy.ebook.entity.BookImage;
import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.repository.BookRepository;
import com.dgy.ebook.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookService{
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public BookInfo getBook(int id){
		return bookRepository.findById(id).get();
    }

	public int getBookNum(){
		try{
			String sql = "select max(id) from book_info";
			int res = jdbcTemplate.queryForObject(sql,int.class)+1;
			return res;
		}catch(Exception e){
			return 0;
		}
	}

	public void updateBook(BookInfo info){
		bookRepository.save(info);
	}

	public void removeBook(int id){
		jdbcTemplate.update("delete from book_info where id=?",
			new Object[]{id});
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
