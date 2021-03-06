package com.dgy.ebook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Entity
@Data
public class BookInfo{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JSONField
	private int id;

	@JSONField
	private String name;
	@JSONField
	private String isbn;
	@JSONField
	private String author;
	@JSONField
	private double price;
	@JSONField
	private int quantity;
	@JSONField
	@Column(length = 3000)
	private String desp;

	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}

}
