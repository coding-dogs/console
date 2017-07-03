package com.easyorder.modules.product.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品图片Entity
 * @author qiudequan
 * @version 2017-06-09
 */
public class ProductPicture extends DataEntity<ProductPicture> {
	
	private static final long serialVersionUID = 1L;
	private String url;		// 图片地址
	private String productId;		// 商品ID
	private String isMain;		// 是否为关联的主要图片
	
	public ProductPicture() {
		super();
	}

	public ProductPicture(String id){
		super(id);
	}

	@ExcelField(title="图片地址", align=2, sort=1)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@ExcelField(title="商品ID", align=2, sort=2)
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@ExcelField(title="是否为关联的主要图片", align=2, sort=3)
	public String getIsMain() {
		return isMain;
	}

	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}
	
}