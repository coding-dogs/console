/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.customer.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.easyorder.modules.customer.entity.Customer;

/**
 * 客户DAO接口
 * @author qiudequan
 * @version 2017-05-11
 */
@MyBatisDao
public interface CustomerDao extends CrudDao<Customer> {

	
}