package com.easyorder.modules.customer.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 客户Entity
 * @author qiudequan
 * @version 2017-05-11
 */
public class Customer extends DataEntity<Customer> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 客户名称
	private String storeName; // 客户店铺名称
	private String customerNo;		// 客户编号
	private String accountNo;		// 登录账号
	private String password;		// 登录密码
	private String mtCustTypeCd;		// 客户类型代码CD
	private String mtCustomerStatusCd;		// 客户状态代码CD
	private String mtCityCd;		// 客户所在城市代码CD
	private String mtCustomerSourceCd;		// 客户来源代码CD
	private String supplierId;		// 供货商ID
	private String customerGroupId;		// 客户组ID
	private String tel;		// 座机号
	private String phone;		// 手机号
	private String email;		// 邮箱号
	private String headPicUrl;		// 用户头像地址
	private String customerGroupName;
	
	private String contactId;
	private String contactName;
	private String contactPhone;
	private String contactEmail;
	private String contactAddress;
	
	public Customer() {
		super();
	}

	public Customer(String id){
		super(id);
	}

	@ExcelField(title="客户名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="客户编号", align=2, sort=2)
	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	@ExcelField(title="登录账号", align=2, sort=3)
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	@ExcelField(title="登录密码", align=2, sort=4)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@ExcelField(title="客户类型代码CD", align=2, sort=5)
	public String getMtCustTypeCd() {
		return mtCustTypeCd;
	}

	public void setMtCustTypeCd(String mtCustTypeCd) {
		this.mtCustTypeCd = mtCustTypeCd;
	}
	
	@ExcelField(title="客户状态代码CD", align=2, sort=6)
	public String getMtCustomerStatusCd() {
		return mtCustomerStatusCd;
	}

	public void setMtCustomerStatusCd(String mtCustomerStatusCd) {
		this.mtCustomerStatusCd = mtCustomerStatusCd;
	}
	
	@ExcelField(title="客户所在城市代码CD", align=2, sort=7)
	public String getMtCityCd() {
		return mtCityCd;
	}

	public void setMtCityCd(String mtCityCd) {
		this.mtCityCd = mtCityCd;
	}
	
	@ExcelField(title="客户来源代码CD", align=2, sort=8)
	public String getMtCustomerSourceCd() {
		return mtCustomerSourceCd;
	}

	public void setMtCustomerSourceCd(String mtCustomerSourceCd) {
		this.mtCustomerSourceCd = mtCustomerSourceCd;
	}
	
	@ExcelField(title="供货商ID", align=2, sort=9)
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	@ExcelField(title="客户组ID", align=2, sort=10)
	public String getCustomerGroupId() {
		return customerGroupId;
	}

	public void setCustomerGroupId(String customerGroupId) {
		this.customerGroupId = customerGroupId;
	}
	
	@ExcelField(title="座机号", align=2, sort=11)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@ExcelField(title="手机号", align=2, sort=12)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ExcelField(title="邮箱号", align=2, sort=13)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ExcelField(title="用户头像地址", align=2, sort=14)
	public String getHeadPicUrl() {
		return headPicUrl;
	}

	public void setHeadPicUrl(String headPicUrl) {
		this.headPicUrl = headPicUrl;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getCustomerGroupName() {
		return customerGroupName;
	}

	public void setCustomerGroupName(String customerGroupName) {
		this.customerGroupName = customerGroupName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
}