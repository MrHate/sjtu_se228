package com.dgy.ebook;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Arrays;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import javax.annotation.PostConstruct;

@Component
public class DbUtil{
    private Logger logger = LoggerFactory.getLogger(DbUtil.class);
	private static DbUtil instance = new DbUtil();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private DbUtil(){
	}

	public static DbUtil getInstance(){
		return instance;
	}

	@PostConstruct // To prevent jbcTemplate from a null pointer
	public void init(){
		instance = this;
		instance.jdbcTemplate = this.jdbcTemplate;
	}

	public class BookInfo{
		public int id;
		public String name;
		public double price;
		public int quantity;
		public String desp;

		public BookInfo(
				int id_,
				String name_,
				double price_,
				int quantity_,
				String desp_){
			id = id_;
			name = name_;
			price = price_;
			quantity = quantity_;
			desp = desp_;
		}
	}

	public List<BookInfo> getBooks(){
        String sql = "select * from book";
        return (List<BookInfo>) jdbcTemplate.query(sql, new RowMapper<BookInfo>(){

            @Override
            public BookInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				//logger.info("> getBooks() id: " + rs.getInt("id") + " name: " + rs.getString("name") );
                BookInfo info = new BookInfo(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getBigDecimal("price").doubleValue(),
						rs.getInt("quantity"),
						rs.getString("desp"));
                return info;
            }

        });
    }

	public BookInfo getBook(int id){
        List<BookInfo> books = getBooks();
		for(BookInfo book : books){
			if(book.id == id){
				//logger.info("> getBook() id: " + book.id + " name: " + book.name );
				return book;
			}
		}
		return books.get(0);
    }

	public int getBookNum(){
		String sql = "select count(*) from book";
		return jdbcTemplate.queryForObject(sql,int.class);
	}
}

