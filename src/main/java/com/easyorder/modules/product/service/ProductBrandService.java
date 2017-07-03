/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.easyorder.modules.product.entity.ProductBrand;
import com.easyorder.modules.product.dao.ProductBrandDao;

/**
 * 商品品牌Service
 * @author qiudequan
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class ProductBrandService extends CrudService<ProductBrandDao, ProductBrand> {

	public ProductBrand get(String id) {
		return super.get(id);
	}
	
	public List<ProductBrand> findList(ProductBrand productBrand) {
		return super.findList(productBrand);
	}
	
	public Page<ProductBrand> findPage(Page<ProductBrand> page, ProductBrand productBrand) {
		return super.findPage(page, productBrand);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductBrand productBrand) {
		super.save(productBrand);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductBrand productBrand) {
		super.delete(productBrand);
	}
	
	
	
	
}