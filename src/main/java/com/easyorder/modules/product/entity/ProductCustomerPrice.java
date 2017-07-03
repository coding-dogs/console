package com.easyorder.modules.product.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品客户指定价Entity
 * @author qiudequan
 * @version 2017-06-09
 */
public class ProductCustomerPrice extends DataEntity<ProductCustomerPrice> {
	
	private static final long serialVersionUID = 1L;
	private String customerId;		// 客户ID
	private String productId;		// 商品ID
	private Double price;		// 给客户的价格
	
	private String customerGroupName;
	private String customerGroupId;
	private String customerName;
	private String mtCityCd;
	private String customerNo;
	
	public ProductCustomerPrice() {
		super();
	}

	public ProductCustomerPrice(String id){
		super(id);
	}

	@ExcelField(title="客户ID", align=2, sort=1)
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@ExcelField(title="商品ID", align=2, sort=2)
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@ExcelField(title="给客户的价格", align=2, sort=3)
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

	public String getCustomerGroupId() {
		return customerGroupId;
	}

	public void setCustomerGroupId(String customerGroupId) {
		this.customerGroupId = customerGroupId;
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