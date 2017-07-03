package com.easyorder.modules.product.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品属性Entity
 * @author qiudequan
 * @version 2017-06-09
 */
public class ProductProperty extends DataEntity<ProductProperty> {
	
	private static final long serialVersionUID = 1L;
	private String productId;		// 商品ID
	private String name;		// 属性名
	private String value;		// 属性值
	
	public ProductProperty() {
		super();
	}

	public ProductProperty(String id){
		super(id);
	}

	@ExcelField(title="商品ID", align=2, sort=1)
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@ExcelField(title="属性名", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="属性值", align=2, sort=3)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}