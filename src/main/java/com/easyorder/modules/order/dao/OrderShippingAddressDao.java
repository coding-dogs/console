package com.easyorder.modules.order.dao;

import com.easyorder.modules.order.entity.OrderShippingAddress;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface OrderShippingAddressDao extends CrudDao<OrderShippingAddress> {

}
