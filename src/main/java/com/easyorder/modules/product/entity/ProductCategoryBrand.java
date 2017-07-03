package com.easyorder.modules.product.entity;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 商品类目品牌关系类
 * @author qiudequan
 * @version 2017-07-02 23:49:31
 */
public class ProductCategoryBrand extends DataEntity<ProductCategoryBrand> {
	
	private static final long serialVersionUID = 1L;
	private String productCategoryId;		// 商品类目ID
	private String productBrandId;		// 商品品牌ID
	private String supplierId;		// 供货商ID
	
	public ProductCategoryBrand() {
		super();
	}

	public ProductCategoryBrand(String id){
		super(id);
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String getProductBrandId() {
		return productBrandId;
	}

	public void setProductBrandId(String productBrandId) {
		this.productBrandId = productBrandId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

}