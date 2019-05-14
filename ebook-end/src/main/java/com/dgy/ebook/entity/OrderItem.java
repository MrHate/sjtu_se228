package com.dgy.ebook.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Entity
@Data
public class OrderItem{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@JSONField
	private int bid;

	@JSONField
	private String username;

	@JSONField
	private int quantity;

	@JSONField
	private double price;

	@JSONField
	private String time;

	private Date date;

	@Override
	public String toString(){
		time = getFormatTime();
		return JSON.toJSONString(this);
	}

	public String getFormatTime(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date.getTime());
	}
}



