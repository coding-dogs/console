package com.easyorder.common.beans;

public class UploadBean {
	private String type;							// 标识上传的是哪类文件
	private String productId;					// 上传商品相关文件时,需要传入商品ID
	private String supplierId;				// 上传需要供应商身份的文件时，需要传入供应商ID
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getSupplierId() {
		return supplierId;
	}
	
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
}
