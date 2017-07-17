package com.easyorder.modules.product.entity;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品Entity
 * @author qiudequan
 * @version 2017-06-09
 */
public class Product extends DataEntity<Product> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 商品名称
	private String title;		// 商品标题
	private String productNo;		// 商品编号
	private String productCategoryId;		// 商品分类ID
	private String repositoryId;		// 仓库ID，仓库表获取
	private String repositoryNo;		// 仓库位号，手动填写
	private String productBrandId;		// 品牌ID
	private String mtProductLabelCd;		// 标签
	private Long sort;		// 排序号
	private String modelNumber;		// 型号
	private String key;		// 关键字
	private Double minimumOrderNumber;		// 起订量
	private String mtProductUpdownCd;		// 商品上下架标识
	private Double orderPrice;		// 订货价格
	private Double marketPrice;		// 市场价格
	private Double buyPrice;		// 进货价格
	private String coverUrl;		// 封面图片地址
	private String unitId;		// 单位ID
	private String description;		// 商品描述
	private String barCode;		// 商品条形码
	private String supplierId;		// 所属供货商ID
	
	private String productCategoryName;
	private String productBrandName;
	private String unitName;
	
	private Map<String, Double> customerPrice;	// 客户指定价格
	private Map<String, Double> customerGroupPrice; // 客户组指定价格
	private List<ProductCustomerPrice> productCustomerPrices;
	private List<ProductCustomerGroupPrice> productCustomerGroupPrices;
	
	private String[] pictures;
	
	public Product() {
		super();
	}

	public Product(String id){
		super(id);
	}

	@ExcelField(title="商品名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="商品标题", align=2, sort=2)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="商品编号", align=2, sort=3)
	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
	@ExcelField(title="商品分类ID", align=2, sort=4)
	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	
	@ExcelField(title="仓库ID，仓库表获取", align=2, sort=5)
	public String getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(String repositoryId) {
		this.repositoryId = repositoryId;
	}
	
	@ExcelField(title="仓库位号，手动填写", align=2, sort=6)
	public String getRepositoryNo() {
		return repositoryNo;
	}

	public void setRepositoryNo(String repositoryNo) {
		this.repositoryNo = repositoryNo;
	}
	
	@ExcelField(title="品牌ID", align=2, sort=7)
	public String getProductBrandId() {
		return productBrandId;
	}

	public void setProductBrandId(String productBrandId) {
		this.productBrandId = productBrandId;
	}
	
	@ExcelField(title="标签", align=2, sort=8)
	public String getMtProductLabelCd() {
		return mtProductLabelCd;
	}

	public void setMtProductLabelCd(String mtProductLabelCd) {
		this.mtProductLabelCd = mtProductLabelCd;
	}
	
	@ExcelField(title="排序号", align=2, sort=9)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@ExcelField(title="型号", align=2, sort=10)
	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	
	@ExcelField(title="关键字", align=2, sort=11)
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@NotNull(message="起订量不能为空")
	@ExcelField(title="起订量", align=2, sort=12)
	public Double getMinimumOrderNumber() {
		return minimumOrderNumber;
	}

	public void setMinimumOrderNumber(Double minimumOrderNumber) {
		this.minimumOrderNumber = minimumOrderNumber;
	}
	
	@ExcelField(title="商品上下架标识", align=2, sort=13)
	public String getMtProductUpdownCd() {
		return mtProductUpdownCd;
	}

	public void setMtProductUpdownCd(String mtProductUpdownCd) {
		this.mtProductUpdownCd = mtProductUpdownCd;
	}
	
	@NotNull(message="订货价格不能为空")
	@ExcelField(title="订货价格", align=2, sort=14)
	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}
	
	@ExcelField(title="市场价格", align=2, sort=15)
	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	@ExcelField(title="进货价格", align=2, sort=16)
	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}
	
	@ExcelField(title="封面图片地址", align=2, sort=17)
	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	
	@ExcelField(title="单位ID", align=2, sort=18)
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	@ExcelField(title="商品描述", align=2, sort=19)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ExcelField(title="商品条形码", align=2, sort=20)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	@ExcelField(title="所属供货商ID", align=2, sort=21)
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Map<String, Double> getCustomerPrice() {
		return customerPrice;
	}

	public void setCustomerPrice(Map<String, Double> customerPrice) {
		this.customerPrice = customerPrice;
	}

	public Map<String, Double> getCustomerGroupPrice() {
		return customerGroupPrice;
	}

	public void setCustomerGroupPrice(Map<String, Double> customerGroupPrice) {
		this.customerGroupPrice = customerGroupPrice;
	}

	public List<ProductCustomerPrice> getProductCustomerPrices() {
		return productCustomerPrices;
	}

	public void setProductCustomerPrices(List<ProductCustomerPrice> productCustomerPrices) {
		this.productCustomerPrices = productCustomerPrices;
	}

	public List<ProductCustomerGroupPrice> getProductCustomerGroupPrices() {
		return productCustomerGroupPrices;
	}

	public void setProductCustomerGroupPrices(List<ProductCustomerGroupPrice> productCustomerGroupPrices) {
		this.productCustomerGroupPrices = productCustomerGroupPrices;
	}

	public String[] getPictures() {
		return pictures;
	}

	public void setPictures(String[] pictures) {
		this.pictures = pictures;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	public String getProductBrandName() {
		return productBrandName;
	}

	public void setProductBrandName(String productBrandName) {
		this.productBrandName = productBrandName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
}