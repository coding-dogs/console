package com.easyorder.modules.customer.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 客户组Entity
 * @author qiudequan
 * @version 2017-05-11
 */
public class CustomerGroup extends DataEntity<CustomerGroup> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 客户组名称
	
	private String supplierId; // 供应商ID
	
	public CustomerGroup() {
		super();
	}

	public CustomerGroup(String id){
		super(id);
	}

	@ExcelField(title="客户组名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
}