package com.dgy.ebook.controller;

import java.util.List;
import java.util.Date;

import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.service.AnalyzeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/analyze")
public class AnalyzeController {

	@Autowired
	private AnalyzeService service;

	@GetMapping("/book-sale")
	public int analyzeSaleForBook(@RequestParam int bookId,@RequestParam Date startDate,@RequestParam Date endDate){
		return service.analyzeSaleForBook(bookId,startDate,endDate);
	}

	@GetMapping("/user-spending")
	public double analyzeSpendingForUser(@RequestParam String username,@RequestParam Date startDate,@RequestParam Date endDate){
		return service.analyzeSpendingForUser(username,startDate,endDate);
	}

	@GetMapping("/user-books")
	public List<BookInfo> analyzeBuysForUser(@RequestParam String username,@RequestParam Date startDate,@RequestParam Date endDate){
		return service.analyzeBuysForUser(username,startDate,endDate);
	}
}
