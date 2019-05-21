package com.dgy.ebook.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;

import lombok.Data;

@Entity
@Data
public class BookImage{
	@Id
	private int id;

	@Column(length=100000)
	private String img;
}

