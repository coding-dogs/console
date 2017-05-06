/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.dao;

import com.easyorder.modules.entity.Supplier;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;

/**
 * 供货商DAO接口
 * @author qiudequan
 * @version 2017-04-22
 */
@MyBatisDao
public interface SupplierDao extends CrudDao<Supplier> {

	
}