package com.dgy.ebook;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.dgy.ebook.entity.UserInfo;
import com.dgy.ebook.entity.BookInfo;

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

	public List<BookInfo> getBooks(){
        String sql = "select * from book";
        return (List<BookInfo>) jdbcTemplate.query(sql, new RowMapper<BookInfo>(){

            @Override
            public BookInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				//logger.info("> getBooks() id: " + rs.getInt("id") + " name: " + rs.getString("name") );
                BookInfo info = new BookInfo();
				info.setId(rs.getInt("id"));
				info.setName(rs.getString("name"));
				info.setIsbn(rs.getString("isbn"));
				info.setAuthor(rs.getString("author"));
				info.setPrice(rs.getBigDecimal("price").doubleValue());
				info.setQuantity(rs.getInt("quantity"));
				info.setDesp(rs.getString("desp"));
                return info;
            }

        });
    }

	public BookInfo getBook(int id){
        String sql = "select * from book where id=?";

		BookInfo res = (BookInfo) jdbcTemplate.queryForObject(sql,new Object[]{id},new RowMapper<BookInfo>(){

            @Override
            public BookInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				//logger.info("> getBooks() id: " + rs.getInt("id") + " name: " + rs.getString("name") );
                BookInfo info = new BookInfo();
				info.setId(rs.getInt("id"));
				info.setName(rs.getString("name"));
				info.setIsbn(rs.getString("isbn"));
				info.setAuthor(rs.getString("author"));
				info.setPrice(rs.getBigDecimal("price").doubleValue());
				info.setQuantity(rs.getInt("quantity"));
				info.setDesp(rs.getString("desp"));
                return info;
			}
		});	

		return res;
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
		jdbcTemplate.update("update book set name=?,price=?,quantity=?,desp=?,isbn=?,author=? where id=?", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, info.getName());
                ps.setBigDecimal(2, new BigDecimal(info.getPrice()));
				ps.setInt(3,info.getQuantity());
				ps.setString(4,info.getDesp());
				ps.setString(5,info.getIsbn());
				ps.setString(6,info.getAuthor());
				ps.setInt(7,info.getId());
            }
        });
	}

	public void insertBook(BookInfo info){
		jdbcTemplate.update("insert into book values(?,?,?,?,?,?,?)",
				new Object[]{
					info.getId(),
					info.getName(),
					info.getIsbn(),
					info.getAuthor(),
					new BigDecimal(info.getPrice()),
					info.getQuantity(),
					info.getDesp()});
	}

	public void removeBook(int id){
		jdbcTemplate.update("delete from book where id=?",
			new Object[]{id});
	}

	public int getUniqueID(){
		List<BookInfo> books = getBooks();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for(BookInfo info : books){
			arr.add(info.getId());
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
                UserInfo info = new UserInfo();
				info.setUsername(rs.getString("username"));
				info.setPassword(rs.getString("password"));
                return info;
            }

        });
    }

	public String getPasswordForUser(String usr){
		List<UserInfo> list = getUsers();
		for(UserInfo info : list){
			if(info.getUsername().equals(usr)){
				return info.getPassword();
			}
		}
		return null;
	}

	public boolean createUser(String usr,String pwd){
		List<UserInfo> list = getUsers();
		for(UserInfo info : list){
			if(info.getUsername().equals(usr)){
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
			if(info.getUsername().equals(usr)){
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
}

