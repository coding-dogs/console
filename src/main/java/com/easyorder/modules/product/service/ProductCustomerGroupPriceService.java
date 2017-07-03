/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.easyorder.modules.product.entity.ProductCustomerGroupPrice;
import com.easyorder.modules.product.dao.ProductCustomerGroupPriceDao;

/**
 * 商品客户组指定价Service
 * @author qiudequan
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class ProductCustomerGroupPriceService extends CrudService<ProductCustomerGroupPriceDao, ProductCustomerGroupPrice> {

	public ProductCustomerGroupPrice get(String id) {
		return super.get(id);
	}
	
	public List<ProductCustomerGroupPrice> findList(ProductCustomerGroupPrice productCustomerGroupPrice) {
		return super.findList(productCustomerGroupPrice);
	}
	
	public Page<ProductCustomerGroupPrice> findPage(Page<ProductCustomerGroupPrice> page, ProductCustomerGroupPrice productCustomerGroupPrice) {
		return super.findPage(page, productCustomerGroupPrice);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductCustomerGroupPrice productCustomerGroupPrice) {
		super.save(productCustomerGroupPrice);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductCustomerGroupPrice productCustomerGroupPrice) {
		super.delete(productCustomerGroupPrice);
	}
	
	
	
	
}