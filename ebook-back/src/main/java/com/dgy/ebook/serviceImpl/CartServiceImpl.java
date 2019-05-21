package com.dgy.ebook.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.entity.CartItem;
import com.dgy.ebook.entity.OrderBatch;
import com.dgy.ebook.entity.OrderItem;
import com.dgy.ebook.repository.BookRepository;
import com.dgy.ebook.repository.CartRepository;
import com.dgy.ebook.repository.OrderBatchRepository;
import com.dgy.ebook.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CartServiceImpl implements CartService{
	@Autowired 
	private CartRepository cartRepository;
	@Autowired
	private OrderBatchRepository orderRepository;
	@Autowired
	private BookRepository bookRepository;

	public String getCartForUser(String username){
		List<CartItem> items = cartRepository.findByUsername(username);
		ArrayList<String> res = new ArrayList();
		for(CartItem item : items){
			BookInfo book = bookRepository.findById(item.getBid()).get();
			JSONObject jobj = new JSONObject();
			jobj.put("id",item.getId());
			jobj.put("bid",item.getBid());
			jobj.put("username",item.getUsername());
			jobj.put("bookname",book.getName());
			jobj.put("isbn",book.getIsbn());
			jobj.put("quantity",item.getQuantity());
			jobj.put("price",book.getPrice());
			res.add(jobj.toJSONString());
		}
		return res.toString();
	}

	public void updateItem(String username,int bid,int quantity){
		CartItem ci = cartRepository.findByUsernameAndBid(username,bid);
		if(ci == null){
			ci = new CartItem();
			ci.setUsername(username);
			ci.setBid(bid);
		}
		ci.setQuantity(quantity);
		cartRepository.save(ci);
	}

	public void deleteItem(String username,int bid){
		cartRepository.delete(cartRepository.findByUsernameAndBid(username,bid));
	}

	public void deleteByUsername(String username){
		cartRepository.deleteInBatch(cartRepository.findByUsername(username));
	}

	public boolean addToCart(String username,int bid){
		CartItem ci = cartRepository.findByUsernameAndBid(username,bid);
		if(ci == null){
			ci = new CartItem();
			ci.setUsername(username);
			ci.setBid(bid);
			ci.setQuantity(1);
		}else{
			ci.setQuantity(ci.getQuantity()+1);
		}
		cartRepository.save(ci);

		return true;
	}

	public boolean clearCart(String username){
		OrderBatch ob = new OrderBatch();
		ob.setUsername(username);
		ob.setDate(new Date());
		ArrayList<OrderItem> ois = new ArrayList<OrderItem>();

		for(CartItem ci : cartRepository.findByUsername(username)){
			BookInfo book = bookRepository.findById(ci.getBid()).get();

			int bquantity = book.getQuantity();
			int cquantity = ci.getQuantity();
			int oquantity = cquantity;
			bquantity -= cquantity;

			if(bquantity < 0){
				oquantity += bquantity;
				bquantity = 0;
			}
			book.setQuantity(bquantity);
			bookRepository.save(book);
			
			OrderItem oi = new OrderItem();
			oi.setBid(ci.getBid());
			oi.setQuantity(oquantity);
			oi.setPrice(oquantity * book.getPrice());
			oi.setOrderBatch(ob);
			ois.add(oi);
			
		}

		if(ois.isEmpty()){
			return false;
		}

		log.info("> "+username+" clear cart with "+String.valueOf(ois.size())+" items");

		ob.setItems(ois);
		orderRepository.save(ob);
		cartRepository.deleteInBatch(cartRepository.findByUsername(username));
		
		return true;
	}
}


