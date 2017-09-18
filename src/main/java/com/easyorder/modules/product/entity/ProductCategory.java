package com.easyorder.modules.product.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品分类Entity
 * @author qiudequan
 * @version 2017-06-09
 */
public class ProductCategory extends DataEntity<ProductCategory> {
	
	private static final long serialVersionUID = 1L;
	private String supplierId;		// 供货商ID
	private String name;		// 商品分类名称
	private Integer categoryNo;		// 分类编号
	private String pid;		// 商品父分类ID
	private String pids;	// 所有父类ID
	private String pictureUrl;	// 类目预览图片
	private Integer sort; // 排序值
	private ProductCategory parent;
	
	public ProductCategory() {
		super();
	}

	public ProductCategory(String id){
		super(id);
	}

	@ExcelField(title="供货商ID", align=2, sort=1)
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	@ExcelField(title="商品分类名称", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="分类编号", align=2, sort=3)
	public Integer getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(Integer categoryNo) {
		this.categoryNo = categoryNo;
	}
	
	@ExcelField(title="商品父分类ID", align=2, sort=4)
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public ProductCategory getParent() {
		return parent;
	}

	public void setParent(ProductCategory parent) {
		this.parent = parent;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

}