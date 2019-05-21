package com.dgy.ebook.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.entity.OrderBatch;
import com.dgy.ebook.entity.OrderItem;
import com.dgy.ebook.repository.BookRepository;
import com.dgy.ebook.repository.OrderBatchRepository;
import com.dgy.ebook.repository.OrderRepository;
import com.dgy.ebook.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
	@Autowired 
	private OrderRepository orderItemRepository;
	@Autowired 
	private OrderBatchRepository orderRepository;
	@Autowired
	private BookRepository bookRepository;

	private String joinBookInfo(List<OrderBatch> batchs){
		ArrayList<String> res = new ArrayList();
		for(OrderBatch batch : batchs){
			log.info("batch have "+String.valueOf(batch.getItems().size())+" items");
			for(OrderItem item : batch.getItems()){
				BookInfo book = bookRepository.findById(item.getBid()).get();
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
		return joinBookInfo(orderRepository.findByUsername(username));
	}

	public String getOrdersAll(){
		return joinBookInfo(orderRepository.findAll());
	}

	public boolean deleteItem(String username,int bid){
		for(OrderBatch batch : orderRepository.findByUsername(username)){
			//log.info(">deleteItem find: "+item.getUsername()+"/"+item.getBid());
			for(OrderItem item : batch.getItems()){
				if(item.getBid() == bid){
					orderRepository.deleteById(item.getId());
					return true;
				}
			}
		}

		return false;
	}

}

