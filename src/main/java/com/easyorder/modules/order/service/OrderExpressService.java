/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.modules.order.dao.OrderExpressDao;
import com.easyorder.modules.order.entity.OrderExpress;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;

/**
 * 订单发货物流信息Service
 * @author qiudequan
 * @version 2017-05-11
 */
@Service
@Transactional(readOnly = true)
public class OrderExpressService extends CrudService<OrderExpressDao, OrderExpress> {

	public OrderExpress get(String id) {
		return super.get(id);
	}
	
	public List<OrderExpress> findList(OrderExpress orderExpress) {
		return super.findList(orderExpress);
	}
	
	public Page<OrderExpress> findPage(Page<OrderExpress> page, OrderExpress orderExpress) {
		return super.findPage(page, orderExpress);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderExpress orderExpress) {
		super.save(orderExpress);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderExpress orderExpress) {
		super.delete(orderExpress);
	}
	
	
	
	
}