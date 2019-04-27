package com.dgy.ebook.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Entity
@Data
public class CartItem{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@JSONField
	private int bid;

	@JSONField
	private String username;

	@JSONField
	private int quantity;

	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}

}


