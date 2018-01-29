package com.easyorder.modules.product.entity;

import java.math.BigDecimal;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 商品多规格客户价
 * @author qiudequan
 * @create 2018年1月29日 下午1:58:54
 */
public class ProductSpecificationCustomerPrice extends DataEntity<ProductSpecificationCustomerPrice> {

	private static final long serialVersionUID = 2790610937447053654L;
	
	private String customerId;						// 客户ID
	private String productSpecificationId;// 商品规格ID
	private BigDecimal orderPrice;				// 商品规格客户指定价
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
