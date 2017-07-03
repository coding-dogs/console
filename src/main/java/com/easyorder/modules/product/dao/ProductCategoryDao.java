/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.dao;

import java.util.List;

import com.easyorder.modules.product.entity.ProductCategory;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;

/**
 * 商品分类DAO接口
 * @author qiudequan
 * @version 2017-06-09
 */
@MyBatisDao
public interface ProductCategoryDao extends CrudDao<ProductCategory> {

	List<ProductCategory> findByParentIdsLike(ProductCategory productCategory);
	
	Integer getMaxSort(ProductCategory productCategory);
}