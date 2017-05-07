package com.easyorder.modules.supplier.vo;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 供应商Entity
 * @author qiudequan
 * @version 2017-05-07
 */
public class SupplierVO extends DataEntity<SupplierVO> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 供货商名称
	private String supplierNo;		// 供货商编号
	private String logoPictureUrl;		// 供货商logo图片地址
	private String managerId;		// 供货商管理员ID
	private String description;		// 简述
	
	private String managerName; // 供货商管理员名称
	
	public SupplierVO() {
		super();
	}

	public SupplierVO(String id){
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

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	
	
	
}