package com.dgy.ebook.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Entity
@Data
public class CartItem{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private int bid;
	private String username;
	private int quantity;

}


