package com.dgy.ebook.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class BookImage{
	@Id
	private int id;

	private byte[] img;
}

