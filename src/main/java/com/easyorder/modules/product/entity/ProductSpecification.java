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
	private String productId;		// 商品ID
	private String supplierId;	// 供应商ID
	private String specificationItemPath;		// 规格项组合ID
	private String specificationNo;					// 规格项编号
	
	public ProductSpecification() {
		super();
	}

	public ProductSpecification(String id){
		super(id);
	}
	
	@ExcelField(title="商品ID", align=2, sort=3)
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSpecificationItemPath() {
		return specificationItemPath;
	}

	public void setSpecificationItemPath(String specificationItemPath) {
		this.specificationItemPath = specificationItemPath;
	}

	public String getSpecificationNo() {
		return specificationNo;
	}

	public void setSpecificationNo(String specificationNo) {
		this.specificationNo = specificationNo;
	}
	
}