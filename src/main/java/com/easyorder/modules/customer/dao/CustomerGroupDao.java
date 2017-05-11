/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.customer.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.easyorder.modules.customer.entity.CustomerGroup;

/**
 * 客户组DAO接口
 * @author qiudequan
 * @version 2017-05-11
 */
@MyBatisDao
public interface CustomerGroupDao extends CrudDao<CustomerGroup> {

	
}