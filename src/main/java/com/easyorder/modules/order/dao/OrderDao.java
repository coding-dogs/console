package com.easyorder.modules.order.dao;

import com.easyorder.modules.order.entity.Order;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface OrderDao extends CrudDao<Order> {
	
}
