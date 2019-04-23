package com.dgy.ebook.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Entity
@Data
public class BookInfo{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String name;
	private String isbn;
	private String author;
	private double price;
	private int quantity;
	private String desp;
}
