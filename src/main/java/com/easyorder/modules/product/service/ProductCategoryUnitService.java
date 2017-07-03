/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.easyorder.modules.product.entity.ProductCategoryUnit;
import com.easyorder.modules.product.dao.ProductCategoryUnitDao;

/**
 * 商品类目单位关联Service
 * @author qiudequan
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class ProductCategoryUnitService extends CrudService<ProductCategoryUnitDao, ProductCategoryUnit> {

	public ProductCategoryUnit get(String id) {
		return super.get(id);
	}
	
	public List<ProductCategoryUnit> findList(ProductCategoryUnit productCategoryUnit) {
		return super.findList(productCategoryUnit);
	}
	
	public Page<ProductCategoryUnit> findPage(Page<ProductCategoryUnit> page, ProductCategoryUnit productCategoryUnit) {
		return super.findPage(page, productCategoryUnit);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductCategoryUnit productCategoryUnit) {
		super.save(productCategoryUnit);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductCategoryUnit productCategoryUnit) {
		super.delete(productCategoryUnit);
	}
	
	
	
	
}