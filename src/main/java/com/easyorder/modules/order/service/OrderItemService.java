package com.easyorder.modules.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.modules.order.dao.OrderItemDao;
import com.easyorder.modules.order.entity.OrderItem;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;

@Service
@Transactional(readOnly = true)
public class OrderItemService extends CrudService<OrderItemDao, OrderItem> {
	public OrderItem get(String id) {
		return super.get(id);
	}
	
	public List<OrderItem> findList(OrderItem orderItem) {
		return super.findList(orderItem);
	}
	
	public Page<OrderItem> findPage(Page<OrderItem> page, OrderItem orderItem) {
		return super.findPage(page, orderItem);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderItem orderItem) {
		super.save(orderItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderItem orderItem) {
		super.delete(orderItem);
	}
}
