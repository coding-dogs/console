package com.easyorder.modules.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.modules.product.dao.ProductSpecificationCustomerPriceDao;
import com.easyorder.modules.product.entity.ProductSpecificationCustomerPrice;
import com.jeeplus.common.service.CrudService;

/**
 * 商品规格客户价Service
 * @author qiudequan
 * @create 2018年1月29日 下午2:07:17
 */
@Service
@Transactional(readOnly = true)
public class ProductSpecificationCustomerPriceService extends CrudService<ProductSpecificationCustomerPriceDao, ProductSpecificationCustomerPrice> {
	
}
