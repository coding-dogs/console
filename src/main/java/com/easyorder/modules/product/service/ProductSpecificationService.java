/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.common.utils.CollectionUtils;
import com.easyorder.modules.product.dao.ProductSpecificationDao;
import com.easyorder.modules.product.entity.ProductSpecification;
import com.easyorder.modules.product.entity.ProductSpecificationCustomerGroupPrice;
import com.easyorder.modules.product.entity.ProductSpecificationCustomerPrice;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;

/**
 * 商品规格Service
 * @author qiudequan
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class ProductSpecificationService extends CrudService<ProductSpecificationDao, ProductSpecification> {

	@Autowired
	ProductSpecificationCustomerPriceService productSpecificationCustomerPriceService;
	
	@Autowired
	ProductSpecificationCustomerGroupPriceService productSpecificationCustomerGroupPriceService;
	
	public ProductSpecification get(String id) {
		return super.get(id);
	}
	
	public List<ProductSpecification> findList(ProductSpecification productSpecification) {
		List<ProductSpecification> psList = super.findList(productSpecification);
		if(CollectionUtils.isNotEmpty(psList)) {
			psList.forEach(psl -> {
				ProductSpecificationCustomerPrice pscp = new ProductSpecificationCustomerPrice();
				pscp.setProductSpecificationId(psl.getId());
				List<ProductSpecificationCustomerPrice> pscpList = productSpecificationCustomerPriceService.findList(pscp);
				psl.setProductSpecificationCustomerPrices(pscpList);
				
				ProductSpecificationCustomerGroupPrice pscgp = new ProductSpecificationCustomerGroupPrice();
				pscgp.setProductSpecificationId(psl.getId());
				List<ProductSpecificationCustomerGroupPrice> pscgpList = productSpecificationCustomerGroupPriceService.findList(pscgp);
				psl.setProductSpecificationCustomerGroupPrices(pscgpList);
			});
		}
		return psList;
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