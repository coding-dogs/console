package com.easyorder.modules.product.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品品牌Entity
 * @author qiudequan
 * @version 2017-06-09
 */
public class ProductBrand extends DataEntity<ProductBrand> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 品牌名称
	private String logoUrl;		// 商品品牌LOGO地址
	private String simpleName;		// 商品品牌简称
	private String supplierId;		// 供货商ID
	private Long sort;		// 排序号
	private String productCategoryId;		// 商品分类ID
	private String productCategoryName; // 商品分类名称
	
	public ProductBrand() {
		super();
	}

	public ProductBrand(String id){
		super(id);
	}

	@ExcelField(title="品牌名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="商品品牌LOGO地址", align=2, sort=2)
	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	
	@ExcelField(title="商品品牌简称", align=2, sort=3)
	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	
	@ExcelField(title="供货商ID", align=2, sort=4)
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	@ExcelField(title="排序号", align=2, sort=5)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@ExcelField(title="商品分类ID", align=2, sort=6)
	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	
}