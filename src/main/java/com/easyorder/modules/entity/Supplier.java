package com.easyorder.modules.entity;

import com.easyorder.common.entity.EasyOrderDataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 供货商Entity
 * @author qiudequan
 * @version 2017-04-22
 */
public class Supplier extends EasyOrderDataEntity<Supplier> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 供货商名称
	private String supplierNo;		// 供货商编号
	private String logoPictureUrl;		// 供货商logo图片地址
	private String description;		// 简述
	
	public Supplier() {
		super();
	}

	public Supplier(String id){
		super(id);
	}

	@ExcelField(title="供货商名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="供货商编号", align=2, sort=2)
	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	
	@ExcelField(title="供货商logo图片地址", align=2, sort=3)
	public String getLogoPictureUrl() {
		return logoPictureUrl;
	}

	public void setLogoPictureUrl(String logoPictureUrl) {
		this.logoPictureUrl = logoPictureUrl;
	}
	
	@ExcelField(title="简述", align=2, sort=4)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}