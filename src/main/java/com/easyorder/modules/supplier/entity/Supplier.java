package com.easyorder.modules.supplier.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 供应商Entity
 * @author qiudequan
 * @version 2017-05-07
 */
public class Supplier extends DataEntity<Supplier> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 供货商名称
	private String bossName;	// 老板姓名
	private String supplierNo;		// 供货商编号
	private String logoPictureUrl;		// 供货商logo图片地址
	private String managerId;		// 供货商管理员ID
	private String mobile;    	// 供货商联系电话
	private String storeAddress;	// 供应商地址
	private String description;		// 简述
	
	public Supplier() {
		super();
	}

	public Supplier(String id){
		super(id);
	}

	@ExcelField(title="供货商名称", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="供货商编号", align=2, sort=1)
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
	
	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	
	@ExcelField(title="简述", align=2, sort=4)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}

	public String getBossName() {
		return bossName;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}