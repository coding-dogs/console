/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.customer.dao;

import java.util.List;

import com.easyorder.modules.customer.entity.Customer;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;

/**
 * 客户DAO接口
 * @author qiudequan
 * @version 2017-05-11
 */
@MyBatisDao
public interface CustomerDao extends CrudDao<Customer> {

	List<Customer> findByCondition(Customer customer);
}