package com.easyorder.modules.product.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品类目单位关联Entity
 * @author qiudequan
 * @version 2017-06-09
 */
public class ProductCategoryUnit extends DataEntity<ProductCategoryUnit> {
	
	private static final long serialVersionUID = 1L;
	private String unitId;		// 单位ID
	private String productCategoryId;		// 商品类目ID
	
	public ProductCategoryUnit() {
		super();
	}

	public ProductCategoryUnit(String id){
		super(id);
	}

	@ExcelField(title="单位ID", align=2, sort=1)
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	@ExcelField(title="商品类目ID", align=2, sort=2)
	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	
}