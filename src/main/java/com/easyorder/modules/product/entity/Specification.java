package com.easyorder.modules.product.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品规格Entity
 * @author qiudequan
 * @version 2017-06-09
 */
public class Specification extends DataEntity<Specification> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 规格名称
	private String no;		// 规格编号
	private String supplierId; // 供应商ID
	private String data;		// 规格数据
	
	public Specification() {
		super();
	}

	public Specification(String id){
		super(id);
	}

	@ExcelField(title="规格名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="规格编号", align=2, sort=2)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@ExcelField(title="规格数据", align=2, sort=3)
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
}