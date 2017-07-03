/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.common.constant.Constants;
import com.easyorder.common.utils.CollectionUtils;
import com.easyorder.common.utils.StringUtils;
import com.easyorder.modules.product.dao.ProductCategoryDao;
import com.easyorder.modules.product.entity.ProductCategory;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;

/**
 * 商品分类Service
 * @author qiudequan
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class ProductCategoryService extends CrudService<ProductCategoryDao, ProductCategory> {

	public ProductCategory get(String id) {
		return super.get(id);
	}
	
	public List<ProductCategory> findList(ProductCategory productCategory) {
		return super.findList(productCategory);
	}
	
	public Page<ProductCategory> findPage(Page<ProductCategory> page, ProductCategory productCategory) {
		return super.findPage(page, productCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductCategory productCategory) {
		String oldPids = productCategory.getPids();
		// 顶级父类
		if("0".equals(productCategory.getPid())) {
			productCategory.setParent(this.getRootCategory());
		} else { 
			productCategory.setParent(this.get(productCategory.getPid()));
		}
		String sourcePids = productCategory.getParent() != null ? productCategory.getParent().getPids() : Constants.EMPTY_STRING;
		productCategory.setPids((StringUtils.isNotEmpty(sourcePids) ? sourcePids : Constants.EMPTY_STRING) + productCategory.getParent().getId() + Constants.SPLIT_SEPERATOR);
		super.save(productCategory);
		
		// 更新子节点pids
		ProductCategory pcParam = new ProductCategory();
		pcParam.setSupplierId(productCategory.getSupplierId());
		pcParam.setPids("%," + productCategory.getId() + ",%");
		List<ProductCategory> list = this.dao.findByParentIdsLike(pcParam);
		if(CollectionUtils.isNotEmpty(list)) {
			for (ProductCategory pc : list) {
				pc.setPids(pc.getPids().replace(oldPids, productCategory.getPids()));
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductCategory productCategory) {
		super.delete(productCategory);
	}
	
	public ProductCategory getRootCategory() {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setName("顶级分类");
		productCategory.setId("0");
		return productCategory;
	}
	
	public Integer getMaxSort(ProductCategory productCategory) {
		return this.dao.getMaxSort(productCategory);
	}
	
	
}