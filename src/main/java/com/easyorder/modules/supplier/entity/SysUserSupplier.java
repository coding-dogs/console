package com.easyorder.modules.supplier.entity;

import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 用户-供应商Entity
 * @author qiudequan
 * @version 2017-05-07
 */
public class SysUserSupplier extends DataEntity<SysUserSupplier> {
	
	private static final long serialVersionUID = 1L;
	private String sysUserId;		// 用户ID
	private String supplierId;		// 供应商ID
	
	public SysUserSupplier() {
		super();
	}

	public SysUserSupplier(String id){
		super(id);
	}

	@ExcelField(title="用户ID", align=2, sort=1)
	public String getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}
	
	@ExcelField(title="供应商ID", align=2, sort=2)
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
}