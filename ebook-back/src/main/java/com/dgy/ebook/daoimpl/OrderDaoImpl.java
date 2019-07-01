package com.dgy.ebook.daoimpl;

import java.util.List;

import com.dgy.ebook.dao.OrderDao;
import com.dgy.ebook.entity.OrderBatch;
import com.dgy.ebook.entity.OrderItem;
import com.dgy.ebook.repository.OrderBatchRepository;
import com.dgy.ebook.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDaoImpl implements OrderDao {
	@Autowired
	private OrderBatchRepository orderBatchRepository;
	@Autowired
	private OrderRepository orderItemRepository;

	public List<OrderBatch> findByUsername(String username){
		return orderBatchRepository.findByUsername(username);
	}

	public List<OrderBatch> findAll(){
		return orderBatchRepository.findAll();
	}

	public void deleteItemById(int id){
		//orderItemRepository.deleteById(id);
		OrderItem oi = orderItemRepository.findById(id).get();
		OrderBatch ob = oi.getOrderBatch();
		List<OrderItem> items = ob.getItems();
		items.remove(oi);
		ob.setItems(items);
		orderBatchRepository.save(ob);
		orderItemRepository.deleteById(id);
	}

	public OrderBatch save(OrderBatch batch){
		return orderBatchRepository.save(batch);
	}

	public List<OrderItem> findItemByBid(int bookId){
		return orderItemRepository.findByBid(bookId);
	}
}
