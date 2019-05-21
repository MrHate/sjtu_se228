package com.dgy.ebook.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.alibaba.fastjson.JSON;

import lombok.Data;

@Entity
@Data
public class OrderBatch{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@OneToMany(mappedBy="orderBatch",cascade=CascadeType.ALL)
	private List<OrderItem> items;

	private String username;
	private Date date;
	private String time;

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




