/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.easyorder.modules.product.entity.Product;

/**
 * 商品DAO接口
 * @author qiudequan
 * @version 2017-06-09
 */
@MyBatisDao
public interface ProductDao extends CrudDao<Product> {

	
}