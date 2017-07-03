package com.easyorder.modules.product.entity;

import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品客户组指定价Entity
 * @author qiudequan
 * @version 2017-06-09
 */
public class ProductCustomerGroupPrice extends DataEntity<ProductCustomerGroupPrice> {
	
	private static final long serialVersionUID = 1L;
	private String customerGroupId;		// 客户组ID
	private String productId;		// 商品ID
	private Double price;		// 客户组指定价格
	
	private String customerGroupName;
	private String customerName;
	private String mtCityCd;
	private String customerNo;
	
	public ProductCustomerGroupPrice() {
		super();
	}

	public ProductCustomerGroupPrice(String id){
		super(id);
	}

	@ExcelField(title="客户组ID", align=2, sort=1)
	public String getCustomerGroupId() {
		return customerGroupId;
	}

	public void setCustomerGroupId(String customerGroupId) {
		this.customerGroupId = customerGroupId;
	}
	
	@ExcelField(title="商品ID", align=2, sort=2)
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@NotNull(message="客户组指定价格不能为空")
	@ExcelField(title="客户组指定价格", align=2, sort=3)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCustomerGroupName() {
		return customerGroupName;
	}

	public void setCustomerGroupName(String customerGroupName) {
		this.customerGroupName = customerGroupName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMtCityCd() {
		return mtCityCd;
	}

	public void setMtCityCd(String mtCityCd) {
		this.mtCityCd = mtCityCd;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
}