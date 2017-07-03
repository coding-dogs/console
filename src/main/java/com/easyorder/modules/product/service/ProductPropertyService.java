/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.easyorder.modules.product.entity.ProductProperty;
import com.easyorder.modules.product.dao.ProductPropertyDao;

/**
 * 商品属性Service
 * @author qiudequan
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class ProductPropertyService extends CrudService<ProductPropertyDao, ProductProperty> {

	public ProductProperty get(String id) {
		return super.get(id);
	}
	
	public List<ProductProperty> findList(ProductProperty productProperty) {
		return super.findList(productProperty);
	}
	
	public Page<ProductProperty> findPage(Page<ProductProperty> page, ProductProperty productProperty) {
		return super.findPage(page, productProperty);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductProperty productProperty) {
		super.save(productProperty);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductProperty productProperty) {
		super.delete(productProperty);
	}
	
	
	
	
}