package com.easyorder.modules.order.entity;

import java.math.BigDecimal;

import com.jeeplus.common.persistence.DataEntity;

public class OrderItem extends DataEntity<OrderItem> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String orderId;
	private String productId;
	private String specificationId;
	private Long totalCount;
	private BigDecimal price;
	private BigDecimal totalPrice;
	private String productTitle;
	private String productPicUrl;
	private String productName;
	private String barCode;
	private String unit;
	
	private String productNo;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSpecificationId() {
		return specificationId;
	}
	public void setSpecificationId(String specificationId) {
		this.specificationId = specificationId;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	public String getProductPicUrl() {
		return productPicUrl;
	}
	public void setProductPicUrl(String productPicUrl) {
		this.productPicUrl = productPicUrl;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
