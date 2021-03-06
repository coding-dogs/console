package com.easyorder.modules.product.entity;

import java.math.BigDecimal;
import java.util.Map;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 商品多规格客户价
 * @author qiudequan
 * @create 2018年1月29日 下午1:58:54
 */
public class ProductSpecificationCustomerPrice extends DataEntity<ProductSpecificationCustomerPrice> {

	private static final long serialVersionUID = 2790610937447053654L;
	
	private String customerId;						// 客户ID
	private String customerName;					// 客户名称
	private String customerNo;						// 客户编号
	private String customerGroupName;			// 所属客户组名称
	private String mtCityCd;							// 所属城市
	private String productSpecificationId;// 商品规格ID
	private BigDecimal orderPrice;				// 商品规格客户指定价
	
	private Map<String, BigDecimal> customerPrices;		// 多规格客户价格
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProductSpecificationId() {
		return productSpecificationId;
	}
	public void setProductSpecificationId(String productSpecificationId) {
		this.productSpecificationId = productSpecificationId;
	}
	public BigDecimal getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}
	public Map<String, BigDecimal> getCustomerPrices() {
		return customerPrices;
	}
	public void setCustomerPrices(Map<String, BigDecimal> customerPrices) {
		this.customerPrices = customerPrices;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getCustomerGroupName() {
		return customerGroupName;
	}
	public void setCustomerGroupName(String customerGroupName) {
		this.customerGroupName = customerGroupName;
	}
	public String getMtCityCd() {
		return mtCityCd;
	}
	public void setMtCityCd(String mtCityCd) {
		this.mtCityCd = mtCityCd;
	}

}
