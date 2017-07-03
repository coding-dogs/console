package com.easyorder.modules.product.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品规格Entity
 * @author qiudequan
 * @version 2017-06-09
 */
public class ProductSpecification extends DataEntity<ProductSpecification> {
	
	private static final long serialVersionUID = 1L;
	private String specificationId;		// 规格id
	private Double price;		// 规格价格
	private String productId;		// 商品ID
	
	public ProductSpecification() {
		super();
	}

	public ProductSpecification(String id){
		super(id);
	}

	@ExcelField(title="规格id", align=2, sort=1)
	public String getSpecificationId() {
		return specificationId;
	}

	public void setSpecificationId(String specificationId) {
		this.specificationId = specificationId;
	}
	
	@ExcelField(title="规格价格", align=2, sort=2)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@ExcelField(title="商品ID", align=2, sort=3)
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}