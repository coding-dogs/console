package com.easyorder.modules.customer.entity;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 供应商及客户关系Entity
 * @author qiudequan
 * @version 2017-05-11
 */
public class SupplierCustomer extends DataEntity<SupplierCustomer> {
	
	private static final long serialVersionUID = 1L;
	private String supplierId; // 供应商ID
	private String customerId;			// 客户ID
	private String customerGroupId; // 客户组ID
	private String mtCustomerStatusCd;	// 客户状态代码
	private Long customerScore;			// 客户积分
	
	public SupplierCustomer() {
		super();
	}

	public SupplierCustomer(String id){
		super(id);
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerGroupId() {
		return customerGroupId;
	}

	public void setCustomerGroupId(String customerGroupId) {
		this.customerGroupId = customerGroupId;
	}

	public String getMtCustomerStatusCd() {
		return mtCustomerStatusCd;
	}

	public void setMtCustomerStatusCd(String mtCustomerStatusCd) {
		this.mtCustomerStatusCd = mtCustomerStatusCd;
	}

	public Long getCustomerScore() {
		return customerScore;
	}

	public void setCustomerScore(Long customerScore) {
		this.customerScore = customerScore;
	}
	
}