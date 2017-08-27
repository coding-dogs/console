package com.easyorder.modules.order.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 订单收货地址Entity
 * @author qiudequan
 * @version 2017-05-11
 */
public class OrderShippingAddress extends DataEntity<OrderShippingAddress> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// 订单ID
	private String shippingName;		// 收货人名称
	private String shippingPhone;		// 收货人手机号
	private String shippingTel;		// 收货人电话
	private String mtProvinceCd;		// 收货地址所在省份
	private String mtCityCd;		// 收货地址所在城市
	private String mtCountyCd;		// 收货地址所在区县
	private String isDefault;		// 是否为默认收货地址
	private String address;		// 收货详细地址
	private String zipCode;		// 邮政编码
	
	public OrderShippingAddress() {
		super();
	}

	public OrderShippingAddress(String id){
		super(id);
	}

	@ExcelField(title="客户ID", align=2, sort=1)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@ExcelField(title="收货人名称", align=2, sort=2)
	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	
	@ExcelField(title="收货人手机号", align=2, sort=3)
	public String getShippingPhone() {
		return shippingPhone;
	}

	public void setShippingPhone(String shippingPhone) {
		this.shippingPhone = shippingPhone;
	}
	
	@ExcelField(title="收货人电话", align=2, sort=4)
	public String getShippingTel() {
		return shippingTel;
	}

	public void setShippingTel(String shippingTel) {
		this.shippingTel = shippingTel;
	}
	
	@ExcelField(title="收货地址所在省份", align=2, sort=5)
	public String getMtProvinceCd() {
		return mtProvinceCd;
	}

	public void setMtProvinceCd(String mtProvinceCd) {
		this.mtProvinceCd = mtProvinceCd;
	}
	
	@ExcelField(title="收货地址所在城市", align=2, sort=6)
	public String getMtCityCd() {
		return mtCityCd;
	}

	public void setMtCityCd(String mtCityCd) {
		this.mtCityCd = mtCityCd;
	}
	
	@ExcelField(title="收货地址所在区县", align=2, sort=7)
	public String getMtCountyCd() {
		return mtCountyCd;
	}

	public void setMtCountyCd(String mtCountyCd) {
		this.mtCountyCd = mtCountyCd;
	}
	
	@ExcelField(title="是否为默认收货地址", align=2, sort=8)
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
	@ExcelField(title="收货详细地址", align=2, sort=9)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="邮政编码", align=2, sort=10)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
}