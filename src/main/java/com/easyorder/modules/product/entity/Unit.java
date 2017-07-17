package com.easyorder.modules.product.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品类目单位Entity
 * @author qiudequan
 * @version 2017-06-09
 */
public class Unit extends DataEntity<Unit> {
	
	private static final long serialVersionUID = 1L;
	private String unit;		// 单位
	private String supplierId; 	// 供货商ID
	
	public Unit() {
		super();
	}

	public Unit(String id){
		super(id);
	}

	@ExcelField(title="单位", align=2, sort=1)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
}