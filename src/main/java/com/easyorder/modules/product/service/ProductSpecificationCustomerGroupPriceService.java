package com.easyorder.modules.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.modules.product.dao.ProductSpecificationCustomerGroupPriceDao;
import com.easyorder.modules.product.entity.ProductSpecificationCustomerGroupPrice;
import com.jeeplus.common.service.CrudService;

/**
 * 商品规格客户组价Service
 * @author qiudequan
 * @create 2018年1月29日 下午2:06:59
 */
@Service
@Transactional(readOnly = true)
public class ProductSpecificationCustomerGroupPriceService extends CrudService<ProductSpecificationCustomerGroupPriceDao, ProductSpecificationCustomerGroupPrice> {

}
