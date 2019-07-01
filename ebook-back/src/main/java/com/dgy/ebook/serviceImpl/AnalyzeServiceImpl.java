package com.dgy.ebook.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dgy.ebook.dao.BookDao;
import com.dgy.ebook.dao.OrderDao;
import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.entity.OrderBatch;
import com.dgy.ebook.entity.OrderItem;
import com.dgy.ebook.service.AnalyzeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyzeServiceImpl implements AnalyzeService {
	
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private BookDao bookDao;

	public int analyzeSaleForBook(int bookId,Date start,Date end){
		int res = 0; // count for sales of a book
		List<OrderItem> ois = orderDao.findItemByBid(bookId);
		for(OrderItem oi : ois){
			Date date = oi.getOrderBatch().getDate();
			if(date.compareTo(start)>=0 && date.compareTo(end)<=0){
				res += oi.getQuantity();
			}
		}

		return res;
	}

	public double analyzeSpendingForUser(String username,Date start,Date end){
		double res = 0; // sum of spending of an user
		List<OrderBatch> orderBatchs = orderDao.findByUsername(username);
		for(OrderBatch ob : orderBatchs){
			Date date = ob.getDate();
			if(date.compareTo(start)>=0 && date.compareTo(end)<=0){
				for(OrderItem oi : ob.getItems()){
					res += oi.getPrice() * oi.getQuantity();
				}
			}
		}

		return res;
	}

	public List<BookInfo> analyzeBuysForUser(String username,Date start,Date end){
		ArrayList<BookInfo> res = new ArrayList<BookInfo>();
		List<OrderBatch> orderBatchs = orderDao.findByUsername(username);
		for(OrderBatch ob : orderBatchs){
			Date date = ob.getDate();
			if(date.compareTo(start)>=0 && date.compareTo(end)<=0){
				for(OrderItem oi : ob.getItems()){
					res.add(bookDao.findById(oi.getBid()));
				}
			}
		}

		return res;
	}

}
