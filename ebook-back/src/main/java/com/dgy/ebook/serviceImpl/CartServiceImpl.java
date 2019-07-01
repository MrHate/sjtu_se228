package com.dgy.ebook.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.dgy.ebook.dao.BookDao;
import com.dgy.ebook.dao.CartDao;
import com.dgy.ebook.dao.OrderDao;
import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.entity.CartItem;
import com.dgy.ebook.entity.OrderBatch;
import com.dgy.ebook.entity.OrderItem;
import com.dgy.ebook.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CartServiceImpl implements CartService{
	@Autowired 
	private CartDao cartDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private BookDao bookDao;

	public String getCartForUser(String username){
		List<CartItem> items = cartDao.findByUsername(username);
		ArrayList<String> res = new ArrayList();
		for(CartItem item : items){
			BookInfo book = bookDao.findById(item.getBid());
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
		CartItem ci = cartDao.findByUsernameAndBid(username,bid);
		if(ci == null){
			ci = new CartItem();
			ci.setUsername(username);
			ci.setBid(bid);
		}
		ci.setQuantity(quantity);
		cartDao.save(ci);
	}

	public void deleteItem(String username,int bid){
		cartDao.delete(cartDao.findByUsernameAndBid(username,bid));
	}

	public void deleteByUsername(String username){
		cartDao.deleteInBatch(cartDao.findByUsername(username));
	}

	public boolean addToCart(String username,int bid){
		CartItem ci = cartDao.findByUsernameAndBid(username,bid);
		if(ci == null){
			ci = new CartItem();
			ci.setUsername(username);
			ci.setBid(bid);
			ci.setQuantity(1);
		}else{
			ci.setQuantity(ci.getQuantity()+1);
		}
		cartDao.save(ci);

		return true;
	}

	public String clearCart(String username){
		String res = "";

		OrderBatch ob = new OrderBatch();
		ob.setUsername(username);
		ob.setDate(new Date());
		ArrayList<OrderItem> ois = new ArrayList<OrderItem>();

		for(CartItem ci : cartDao.findByUsername(username)){
			BookInfo book = bookDao.findById(ci.getBid());

			int bquantity = book.getQuantity();
			int cquantity = ci.getQuantity();
			int oquantity = cquantity;
			bquantity -= cquantity;

			if(bquantity < 0){
				oquantity += bquantity;
				bquantity = 0;
				res += "[Warning] \"" + book.getName() + "\" only remains: " + String.valueOf(oquantity) + "\n";
			}
			else{
				res += "[Success] \"" + book.getName() + "\" bought: " + String.valueOf(oquantity) + "\n";
			}
			book.setQuantity(bquantity);
			bookDao.save(book);
			
			OrderItem oi = new OrderItem();
			oi.setBid(ci.getBid());
			oi.setQuantity(oquantity);
			oi.setPrice(oquantity * book.getPrice());
			oi.setOrderBatch(ob);
			ois.add(oi);
			
		}

		if(ois.isEmpty()){
			return "empty cart";
		}

		log.info("> "+username+" clear cart with "+String.valueOf(ois.size())+" items");

		ob.setItems(ois);
		orderDao.save(ob);
		cartDao.deleteInBatch(cartDao.findByUsername(username));
		
		return res;
	}
}


