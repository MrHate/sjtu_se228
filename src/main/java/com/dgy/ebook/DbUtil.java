package com.dgy.ebook;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Base64;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
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

	public static class UserInfo{
		public String username;
		public String password;

		public UserInfo(
				String username_,
				String password_){
			username = username_;
			password = password_;
		}
	}
	public static class BookInfo{
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
        String sql = "select * from book where id=?";

		return (BookInfo) jdbcTemplate.queryForObject(sql,new Object[]{id},new RowMapper<BookInfo>(){

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

        //List<BookInfo> books = getBooks();
		//for(BookInfo book : books){
		//    if(book.id == id){
		//        //logger.info("> getBook() id: " + book.id + " name: " + book.name );
		//        return book;
		//    }
		//}
		//return new BookInfo(-1,"_not_found_",0,0,"_not_found_");
    }

	public int getBookNum(){
		try{
			String sql = "select max(id) from book";
			int res = jdbcTemplate.queryForObject(sql,int.class)+1;
			return res;
		}catch(Exception e){
			return 0;
		}
	}

	public void updateBook(BookInfo info){
		jdbcTemplate.update("update book set name=?,price=?,quantity=?,desp=? where id=?", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, info.name);
                ps.setBigDecimal(2, new BigDecimal(info.price));
				ps.setInt(3,info.quantity);
				ps.setString(4,info.desp);
				ps.setInt(5,info.id);
            }
        });
	}

	public void insertBook(BookInfo info){
		jdbcTemplate.update("insert into book values(?,?,?,?,?)",
				new Object[]{
					info.id,
					info.name,
					new BigDecimal(info.price),
					info.quantity,
					info.desp});
	}

	public void removeBook(int id){
		jdbcTemplate.update("delete from book where id=?",
			new Object[]{id});
	}

	public int getUniqueID(){
		List<BookInfo> books = getBooks();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for(BookInfo info : books){
			arr.add(info.id);
		}
		int id = 0;
		while(arr.contains(id)){
			++id;
		}
		return id;
	}

	private List<UserInfo> getUsers(){
        String sql = "select * from usr";
        return (List<UserInfo>) jdbcTemplate.query(sql, new RowMapper<UserInfo>(){

            @Override
            public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				//logger.info("> getBooks() id: " + rs.getInt("id") + " name: " + rs.getString("name") );
                UserInfo info = new UserInfo(
						rs.getString("username"),
						rs.getString("password"));
                return info;
            }

        });
    }

	public String getPasswordForUser(String usr){
		List<UserInfo> list = getUsers();
		for(UserInfo info : list){
			if(info.username.equals(usr)){
				return info.password;
			}
		}
		return null;
	}

	public boolean createUser(String usr,String pwd){
		List<UserInfo> list = getUsers();
		for(UserInfo info : list){
			if(info.username.equals(usr)){
				return false;
			}
		}

		jdbcTemplate.update("insert into usr values(?,?)",
				new Object[]{usr,pwd});

		return true;
	}

	public boolean removeUser(String usr){
		List<UserInfo> list = getUsers();
		for(UserInfo info : list){
			if(info.username.equals(usr)){
				jdbcTemplate.update("delete from usr where username=?",new Object[]{usr});
				return true;
			}
		}

		return false;
	}

	public String getImg(int id){
        String sql = "select img from image where id=?";

		byte[] bytes = jdbcTemplate.queryForObject(sql,new Object[]{id},byte[].class);
		return new String(bytes);
	}

	public boolean storeImg(int id,byte[] base64){
		jdbcTemplate.update("insert into image values(?,?)",
				new Object[]{id,base64});
		return true;
	}

	public boolean updateImg(int id,byte[] base64){
		jdbcTemplate.update("update image set img=? where id=?", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setBytes(1, base64);
                ps.setInt(2, id);
            }
        });

		return true;
	}

	//public boolean removeImg(int id){
	//    return true;
	//}
}

