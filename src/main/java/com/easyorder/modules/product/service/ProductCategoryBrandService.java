/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.modules.product.dao.ProductCategoryBrandDao;
import com.easyorder.modules.product.entity.ProductCategoryBrand;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;

/**
 * 商品类目品牌关系维护Service
 * @author qiudequan
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class ProductCategoryBrandService extends CrudService<ProductCategoryBrandDao, ProductCategoryBrand> {

	public ProductCategoryBrand get(String id) {
		return super.get(id);
	}
	
	public List<ProductCategoryBrand> findList(ProductCategoryBrand productCategoryBrand) {
		return super.findList(productCategoryBrand);
	}
	
	public Page<ProductCategoryBrand> findPage(Page<ProductCategoryBrand> page, ProductCategoryBrand productCategoryBrand) {
		return super.findPage(page, productCategoryBrand);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductCategoryBrand productCategoryBrand) {
		super.save(productCategoryBrand);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductCategoryBrand productCategoryBrand) {
		super.delete(productCategoryBrand);
	}
	
	
	
	
}