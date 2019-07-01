package com.dgy.ebook.entity;

import javax.persistence.Id;

import lombok.Data;

@Data
public class BookImage{
	@Id
	private int id;

	private String img;
}

