package com.easyorder.modules.order.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jeeplus.common.persistence.DataEntity;

public class Order extends DataEntity<Order> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String orderNo;
	private BigDecimal totalPrice;
	private BigDecimal reductionFee;
	private BigDecimal actualPrice;
	private Long totalCount;
	private BigDecimal fare;
	private Date payTime;
	private Date shipTime;
	private String mtOrderStatusCd;
	private String shippingAddressId;
	private String customerId;
	private String mtPaymentMethodCd;
	private Date completedTime;
	private Date closeTime;
	private String supplierId;
	
	
	private String customerName;
	private List<OrderItem> orderItems;
	private OrderShippingAddress orderShippingAddress;
	private OrderExpress orderExpress;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public BigDecimal getReductionFee() {
		return reductionFee;
	}
	public void setReductionFee(BigDecimal reductionFee) {
		this.reductionFee = reductionFee;
	}
	public BigDecimal getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public BigDecimal getFare() {
		return fare;
	}
	public void setFare(BigDecimal fare) {
		this.fare = fare;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Date getShipTime() {
		return shipTime;
	}
	public void setShipTime(Date shipTime) {
		this.shipTime = shipTime;
	}
	public String getMtOrderStatusCd() {
		return mtOrderStatusCd;
	}
	public void setMtOrderStatusCd(String mtOrderStatusCd) {
		this.mtOrderStatusCd = mtOrderStatusCd;
	}
	public String getShippingAddressId() {
		return shippingAddressId;
	}
	public void setShippingAddressId(String shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getMtPaymentMethodCd() {
		return mtPaymentMethodCd;
	}
	public void setMtPaymentMethodCd(String mtPaymentMethodCd) {
		this.mtPaymentMethodCd = mtPaymentMethodCd;
	}
	public Date getCompletedTime() {
		return completedTime;
	}
	public void setCompletedTime(Date completedTime) {
		this.completedTime = completedTime;
	}
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public OrderShippingAddress getOrderShippingAddress() {
		return orderShippingAddress;
	}
	public void setOrderShippingAddress(OrderShippingAddress orderShippingAddress) {
		this.orderShippingAddress = orderShippingAddress;
	}
	public OrderExpress getOrderExpress() {
		return orderExpress;
	}
	public void setOrderExpress(OrderExpress orderExpress) {
		this.orderExpress = orderExpress;
	}
	
}
