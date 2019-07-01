package com.dgy.ebook.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.dgy.ebook.dao.BookDao;
import com.dgy.ebook.dao.OrderDao;
import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.entity.OrderBatch;
import com.dgy.ebook.entity.OrderItem;
import com.dgy.ebook.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
	@Autowired 
	private OrderDao orderDao;
	@Autowired
	private BookDao bookDao;

	private String joinBookInfo(List<OrderBatch> batchs){
		ArrayList<String> res = new ArrayList();
		for(OrderBatch batch : batchs){
			for(OrderItem item : batch.getItems()){
				BookInfo book = bookDao.findById(item.getBid());
				JSONObject jobj = new JSONObject();
				jobj.put("orderid",batch.getId());
				jobj.put("id",item.getId());
				jobj.put("bid",item.getBid());
				jobj.put("username",batch.getUsername());
				jobj.put("bookname",book.getName());
				jobj.put("isbn",book.getIsbn());
				jobj.put("time",batch.getFormatTime());
				jobj.put("quantity",item.getQuantity());
				jobj.put("price",item.getPrice());
				jobj.put("date",batch.getDate());
				res.add(jobj.toJSONString());
			}
		}
		return res.toString();
	}

	public String getOrderForUser(String username){
		return joinBookInfo(orderDao.findByUsername(username));
	}

	public String getOrdersAll(){
		return joinBookInfo(orderDao.findAll());
	}

	public boolean deleteItem(int id){
		log.info(">deleteItem: "+id);
		orderDao.deleteItemById(id);
		return true;
	}

}

