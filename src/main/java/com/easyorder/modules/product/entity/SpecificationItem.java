package com.easyorder.modules.product.entity;

import com.jeeplus.common.persistence.DataEntity;

public class SpecificationItem extends DataEntity<SpecificationItem> {
	private static final long serialVersionUID = 1L; 
	
	private String specificationId;			// 规格ID
	private String supplierId;					// 供应商ID
	private String name;								// 规格项名称
	private String no;									// 规格项编号
	
	
	public String getSpecificationId() {
		return specificationId;
	}
	public void setSpecificationId(String specificationId) {
		this.specificationId = specificationId;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	
}
