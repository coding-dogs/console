package com.easyorder.modules.customer.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 联系人Entity
 * @author qiudequan
 * @version 2017-06-05
 */
public class Contact extends DataEntity<Contact> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 联系人名称
	private String tel;		// 联系人号码
	private String phone;		// 联系人手机号
	private String email;		// 联系人邮箱
	private String mtProvinceCd;		// 收货地址所在省份
	private String mtCityCd;		// 收货地址所在城市
	private String mtCountyCd;		// 收货地址所在区县
	private String address;		// 联系人地址
	private String customerId;		// 客户ID
	
	public Contact() {
		super();
	}

	public Contact(String id){
		super(id);
	}

	@ExcelField(title="联系人名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="联系人号码", align=2, sort=2)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@ExcelField(title="联系人手机号", align=2, sort=3)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ExcelField(title="联系人邮箱", align=2, sort=4)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	@ExcelField(title="联系人地址", align=2, sort=8)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="客户ID", align=2, sort=9)
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
}