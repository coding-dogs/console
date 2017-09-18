package com.easyorder.modules.order.dao;

import java.util.List;

import com.easyorder.modules.order.entity.Order;
import com.easyorder.modules.order.entity.OrderStatistics;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface OrderDao extends CrudDao<Order> {
	List<OrderStatistics> findOrderStatistics(Order order);
	
	List<OrderStatistics> findOrderStatisticsDetail(OrderStatistics orderStatistics);
	
}
