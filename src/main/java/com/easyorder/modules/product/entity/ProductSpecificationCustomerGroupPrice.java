package com.easyorder.modules.product.entity;

import java.math.BigDecimal;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 商品规格客户组价
 * @author qiudequan
 * @create 2018年1月29日 下午1:55:05
 */
public class ProductSpecificationCustomerGroupPrice extends DataEntity<ProductSpecificationCustomerGroupPrice> {

	private static final long serialVersionUID = 8631639776700071019L;
	
	private String customerGroupId;						// 客户组ID
	private String productSpecificationId;		// 商品规格ID
	private BigDecimal orderPrice;						// 客户组指定价
	public String getCustomerGroupId() {
		return customerGroupId;
	}
	public void setCustomerGroupId(String customerGroupId) {
		this.customerGroupId = customerGroupId;
	}
	public String getProductSpecificationId() {
		return productSpecificationId;
	}
	public void setProductSpecificationId(String productSpecificationId) {
		this.productSpecificationId = productSpecificationId;
	}
	public BigDecimal getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}
	
}
