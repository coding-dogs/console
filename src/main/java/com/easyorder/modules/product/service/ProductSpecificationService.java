/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.easyorder.modules.product.entity.ProductSpecification;
import com.easyorder.modules.product.dao.ProductSpecificationDao;

/**
 * 商品规格Service
 * @author qiudequan
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class ProductSpecificationService extends CrudService<ProductSpecificationDao, ProductSpecification> {

	public ProductSpecification get(String id) {
		return super.get(id);
	}
	
	public List<ProductSpecification> findList(ProductSpecification productSpecification) {
		return super.findList(productSpecification);
	}
	
	public Page<ProductSpecification> findPage(Page<ProductSpecification> page, ProductSpecification productSpecification) {
		return super.findPage(page, productSpecification);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductSpecification productSpecification) {
		super.save(productSpecification);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductSpecification productSpecification) {
		super.delete(productSpecification);
	}
	
	
	
	
}