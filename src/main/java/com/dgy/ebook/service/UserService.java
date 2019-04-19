package com.dgy.ebook.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.dgy.ebook.entity.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<UserInfo> getUserList(){
		return jdbcTemplate.query("select * from usr", new RowMapper<UserInfo>() {

            @Override
            public UserInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                UserInfo user = new UserInfo();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });
	}

	public String getPasswordForUser(String usr){
		List<UserInfo> list = getUserList();
		for(UserInfo info : list){
			if(info.getUsername().equals(usr)){
				return info.getPassword();
			}
		}
		return null;
	}

	public boolean createUser(String usr,String pwd){
		List<UserInfo> list = getUserList();
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
		List<UserInfo> list = getUserList();
		for(UserInfo info : list){
			if(info.getUsername().equals(usr)){
				jdbcTemplate.update("delete from usr where username=?",new Object[]{usr});
				return true;
			}
		}

		return false;
	}
}
