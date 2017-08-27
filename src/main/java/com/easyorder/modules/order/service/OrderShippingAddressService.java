/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.modules.order.dao.OrderShippingAddressDao;
import com.easyorder.modules.order.entity.OrderShippingAddress;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;

/**
 * 订单收货地址Service
 * @author qiudequan
 * @version 2017-05-11
 */
@Service
@Transactional(readOnly = true)
public class OrderShippingAddressService extends CrudService<OrderShippingAddressDao, OrderShippingAddress> {

	public OrderShippingAddress get(String id) {
		return super.get(id);
	}
	
	public List<OrderShippingAddress> findList(OrderShippingAddress orderShippingAddress) {
		return super.findList(orderShippingAddress);
	}
	
	public Page<OrderShippingAddress> findPage(Page<OrderShippingAddress> page, OrderShippingAddress orderShippingAddress) {
		return super.findPage(page, orderShippingAddress);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderShippingAddress orderShippingAddress) {
		super.save(orderShippingAddress);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderShippingAddress orderShippingAddress) {
		super.delete(orderShippingAddress);
	}
	
	
	
	
}