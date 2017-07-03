package com.easyorder.modules.product.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品规格组Entity
 * @author qiudequan
 * @version 2017-06-09
 */
public class SpecificationGroup extends DataEntity<SpecificationGroup> {
	
	private static final long serialVersionUID = 1L;
	private String groupName;		// 规格组名称
	private String groupNo;		// 规格组编号
	private String productCategoryId;		// 商品分类ID
	
	public SpecificationGroup() {
		super();
	}

	public SpecificationGroup(String id){
		super(id);
	}

	@ExcelField(title="规格组名称", align=2, sort=1)
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@ExcelField(title="规格组编号", align=2, sort=2)
	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
	
	@ExcelField(title="商品分类ID", align=2, sort=3)
	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	
}