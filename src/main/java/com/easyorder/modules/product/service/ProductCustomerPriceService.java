/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.easyorder.modules.product.entity.ProductCustomerPrice;
import com.easyorder.modules.product.dao.ProductCustomerPriceDao;

/**
 * 商品客户指定价Service
 * @author qiudequan
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class ProductCustomerPriceService extends CrudService<ProductCustomerPriceDao, ProductCustomerPrice> {

	public ProductCustomerPrice get(String id) {
		return super.get(id);
	}
	
	public List<ProductCustomerPrice> findList(ProductCustomerPrice productCustomerPrice) {
		return super.findList(productCustomerPrice);
	}
	
	public Page<ProductCustomerPrice> findPage(Page<ProductCustomerPrice> page, ProductCustomerPrice productCustomerPrice) {
		return super.findPage(page, productCustomerPrice);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductCustomerPrice productCustomerPrice) {
		super.save(productCustomerPrice);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductCustomerPrice productCustomerPrice) {
		super.delete(productCustomerPrice);
	}
	
	@Transactional(readOnly = false)
	public void deleteByCondition(ProductCustomerPrice customerPrice) {
		dao.deleteByCondition(customerPrice);
	}
	
	public List<String> getCustomerIds(String productId) {
		return dao.getCustomerIds(productId);
	}
}