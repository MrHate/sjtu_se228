package com.dgy.ebook.service;

import java.util.List;
import java.util.Date;

import com.dgy.ebook.entity.BookInfo;

public interface AnalyzeService {

	public int analyzeSaleForBook(int bookId,Date start,Date end);
	public double analyzeSpendingForUser(String username,Date start,Date end);
	public List<BookInfo> analyzeBuysForUser(String username,Date start,Date end);
}

