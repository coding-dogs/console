/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.easyorder.modules.product.entity.ProductCustomerGroupPrice;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;

/**
 * 商品客户组指定价DAO接口
 * @author qiudequan
 * @version 2017-06-09
 */
@MyBatisDao
public interface ProductCustomerGroupPriceDao extends CrudDao<ProductCustomerGroupPrice> {
	
	void deleteByCondition(ProductCustomerGroupPrice customerGroupPrice);
	
	List<String> getCustomerGroupIds(@Param(value = "productId") String productId);
}