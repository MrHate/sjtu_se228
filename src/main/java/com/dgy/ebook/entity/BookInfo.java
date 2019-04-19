package com.dgy.ebook.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class BookInfo{
	@Id
	private int id;
	private String name;
	private String isbn;
	private String author;
	private double price;
	private int quantity;
	private String desp;
}
