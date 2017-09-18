package com.easyorder.modules.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.common.utils.CollectionUtils;
import com.easyorder.modules.order.dao.OrderDao;
import com.easyorder.modules.order.entity.Order;
import com.easyorder.modules.order.entity.OrderExpress;
import com.easyorder.modules.order.entity.OrderItem;
import com.easyorder.modules.order.entity.OrderShippingAddress;
import com.easyorder.modules.order.entity.OrderStatistics;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;

@Service
@Transactional(readOnly = true)
public class OrderService extends CrudService<OrderDao, Order> {
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private OrderShippingAddressService orderShippingAddressService;
	@Autowired
	private OrderExpressService orderExpressService;

	public Order get(String id) {
		Order order = super.get(id);
		OrderItem oi = new OrderItem();
		oi.setOrderId(id);
		List<OrderItem> orderItems = orderItemService.findList(oi);
		order.setOrderItems(orderItems);

		OrderShippingAddress orderShippingAddress = new OrderShippingAddress();
		orderShippingAddress.setOrderId(id);
		List<OrderShippingAddress> shippingAddresses = orderShippingAddressService.findList(orderShippingAddress);
		if(CollectionUtils.isNotEmpty(shippingAddresses)) {
			order.setOrderShippingAddress(shippingAddresses.get(0));
		}

		OrderExpress orderExpress = new OrderExpress();
		orderExpress.setOrderId(id);
		List<OrderExpress> orderExpresses = orderExpressService.findList(orderExpress);
		if(CollectionUtils.isNotEmpty(orderExpresses)) {
			order.setOrderExpress(orderExpresses.get(0));
		}
		return order;
	}

	public List<Order> findList(Order order) {
		return super.findList(order);
	}

	public Page<Order> findPage(Page<Order> page, Order order) {
		return super.findPage(page, order);
	}

	@Transactional(readOnly = false)
	public void save(Order order) {
		super.save(order);
	}

	@Transactional(readOnly = false)
	public void delete(Order order) {
		super.delete(order);
	}

	public List<OrderStatistics> getOrderStatistics(Order order) {
		return dao.findOrderStatistics(order);
	}

	
	public List<OrderStatistics> getOrderStatisticsDetail(OrderStatistics orderStatistics) {
		return dao.findOrderStatisticsDetail(orderStatistics);
	}
	
	public Page<OrderStatistics> getOrderStatisticsDetail(Page<OrderStatistics> page, OrderStatistics orderStatistics) {
		orderStatistics.setPage(page);
		List<OrderStatistics> resultList = dao.findOrderStatisticsDetail(orderStatistics);
		page.setList(resultList);
		return page;
	}

}
