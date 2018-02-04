package com.easyorder.modules.product.entity;

import java.math.BigDecimal;
import java.util.List;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品规格Entity
 * @author qiudequan
 * @version 2017-06-09
 */
public class ProductSpecification extends DataEntity<ProductSpecification> {

	private static final long serialVersionUID = 1L;
	private String productId;		// 商品ID
	private String supplierId;	// 供应商ID
	private String specificationItemPath;		// 规格项组合ID
	private String specificationNo;					// 规格项编号
	private BigDecimal orderPrice;							// 订货价
	private BigDecimal marketPrice;							// 市场价
	private BigDecimal buyPrice;						// 进货价
	private String barCode;									// 条形码
	// 多规格客户组指定价格
	private List<ProductSpecificationCustomerGroupPrice> productSpecificationCustomerGroupPrices;
	// 多规格客户指定价格
	private List<ProductSpecificationCustomerPrice> productSpecificationCustomerPrices;

	public ProductSpecification() {
		super();
	}

	public ProductSpecification(String id){
		super(id);
	}

	@ExcelField(title="商品ID", align=2, sort=3)
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSpecificationItemPath() {
		return specificationItemPath;
	}

	public void setSpecificationItemPath(String specificationItemPath) {
		this.specificationItemPath = specificationItemPath;
	}

	public String getSpecificationNo() {
		return specificationNo;
	}

	public void setSpecificationNo(String specificationNo) {
		this.specificationNo = specificationNo;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public List<ProductSpecificationCustomerGroupPrice> getProductSpecificationCustomerGroupPrices() {
		return productSpecificationCustomerGroupPrices;
	}

	public void setProductSpecificationCustomerGroupPrices(
			List<ProductSpecificationCustomerGroupPrice> productSpecificationCustomerGroupPrices) {
		this.productSpecificationCustomerGroupPrices = productSpecificationCustomerGroupPrices;
	}

	public List<ProductSpecificationCustomerPrice> getProductSpecificationCustomerPrices() {
		return productSpecificationCustomerPrices;
	}

	public void setProductSpecificationCustomerPrices(
			List<ProductSpecificationCustomerPrice> productSpecificationCustomerPrices) {
		this.productSpecificationCustomerPrices = productSpecificationCustomerPrices;
	}

}