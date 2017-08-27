package com.easyorder.modules.order.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 订单发货物流信息Entity
 * @author qiudequan
 * @version 2017-05-11
 */
public class OrderExpress extends DataEntity<OrderExpress> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// 订单ID
	private String companyName;		// 物流公司名称
	private String expressNo;		// 物流单号
	
	public OrderExpress() {
		super();
	}

	public OrderExpress(String id){
		super(id);
	}

	@ExcelField(title="客户ID", align=2, sort=1)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	
}