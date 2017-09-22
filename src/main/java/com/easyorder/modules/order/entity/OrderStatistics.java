package com.easyorder.modules.order.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 统计数据
 * @author Administrator
 *
 */
public class OrderStatistics extends DataEntity<OrderStatistics> {
	private static final long serialVersionUID = 1L;
	private Integer orderCount;						// 订单总数
	private BigDecimal orderTotalPrice = new BigDecimal(0);				// 订单总金额
	private Integer customerCount;					// 客户总数
	private String groupAccordance;				// 分组依据，若为月，则其值为具体某个月的值，若为日，则其值为具体某一日的值
	private String supplierId;
	private String customerId;
	private Date beginDate;
	private Date endDate;
	private String mode;
	
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}
	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	public Integer getCustomerCount() {
		return customerCount;
	}
	public void setCustomerCount(Integer customerCount) {
		this.customerCount = customerCount;
	}
	public String getGroupAccordance() {
		return groupAccordance;
	}
	public void setGroupAccordance(String groupAccordance) {
		this.groupAccordance = groupAccordance;
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
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
}
