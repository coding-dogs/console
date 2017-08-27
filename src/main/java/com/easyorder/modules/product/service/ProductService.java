/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.common.constant.Constants;
import com.easyorder.common.utils.BeanUtils;
import com.easyorder.common.utils.CollectionUtils;
import com.easyorder.common.utils.GSONUtils;
import com.easyorder.common.utils.StringUtils;
import com.easyorder.modules.product.dao.ProductDao;
import com.easyorder.modules.product.entity.Product;
import com.easyorder.modules.product.entity.ProductCategoryBrand;
import com.easyorder.modules.product.entity.ProductCustomerGroupPrice;
import com.easyorder.modules.product.entity.ProductCustomerPrice;
import com.easyorder.modules.product.entity.ProductPicture;
import com.easyorder.modules.product.entity.ProductSpecification;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;

/**
 * 商品Service
 * @author qiudequan
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class ProductService extends CrudService<ProductDao, Product> {
	private static final String HANDLER_TYPE_LIST = "list";
	private static final String HANDLER_TYPE_MAP = "map";
	@Autowired
	ProductCategoryBrandService productCategoryBrandService;
	@Autowired
	ProductPictureService productPictureService;
	@Autowired
	ProductCustomerPriceService productCustomerPriceService;
	@Autowired
	ProductCustomerGroupPriceService productCustomerGroupPriceService;
	@Autowired
	ProductSpecificationService productSpecificationService;

	public Product get(String id) {
		Product product = super.get(id);
		if(BeanUtils.isNotEmpty(product)) {
			handlerPrice(product, HANDLER_TYPE_LIST);
			handlerSpecification(product);
		}
		return product;
	}
	
	private void handlerSpecification(Product product) {
		ProductSpecification ps = new ProductSpecification();
		String productId = product.getId();
		String supplierId = product.getSupplierId();
		ps.setProductId(productId);
		ps.setSupplierId(supplierId);
		List<ProductSpecification> psList = productSpecificationService.findList(ps);
		if(CollectionUtils.isNotEmpty(psList)) {
			product.setSpecJson(GSONUtils.toJSON(psList));
		}
	}

	/**
	 * 处理商品多规格
	 * @param product
	 */
	private void saveSpecification(Product product) {
		ProductSpecification ps = new ProductSpecification();
		String productId = product.getId();
		String supplierId = product.getSupplierId();
		ps.setProductId(productId);
		ps.setSupplierId(supplierId);
		List<ProductSpecification> psList = productSpecificationService.findList(ps);
		if(CollectionUtils.isNotEmpty(psList)) {
			psList.forEach(psl -> {
				productSpecificationService.delete(psl);
			});
		}
		String specJson = product.getSpecJson();
		if(StringUtils.hasText(specJson)) {
			List<ProductSpecification> pss = GSONUtils.jsonToList(specJson, ProductSpecification.class);
			if(CollectionUtils.isNotEmpty(pss)) {
				pss.forEach(productSpec -> {
					productSpec.setSupplierId(supplierId);
					productSpec.setProductId(productId);
					productSpecificationService.save(productSpec);
				});
			}
		}
	}

	public List<Product> findList(Product product) {
		return super.findList(product);
	}

	public Page<Product> findPage(Page<Product> page, Product product) {
		Page<Product> resultPage = super.findPage(page, product);
		return resultPage;
	}

	private void handlerPrice(Product product, String type) {
		String productId = product.getId();
		ProductCustomerPrice productCustomerPrice = new ProductCustomerPrice();
		productCustomerPrice.setProductId(productId);
		List<ProductCustomerPrice> customerPriceList = productCustomerPriceService.findList(productCustomerPrice);
		if(CollectionUtils.isNotEmpty(customerPriceList)) {
			if(HANDLER_TYPE_MAP.equals(type)) {
				Map<String, Double> customerPriceMap = new HashMap<>();
				customerPriceList.forEach(cp -> {
					customerPriceMap.put(cp.getCustomerId(), cp.getPrice());
				});
				product.setCustomerPrice(customerPriceMap);
			} else if(HANDLER_TYPE_LIST.equals(type)) {
				product.setProductCustomerPrices(customerPriceList);
			}
		}

		ProductCustomerGroupPrice productCustomerGroupPrice = new ProductCustomerGroupPrice();
		productCustomerGroupPrice.setProductId(productId);
		List<ProductCustomerGroupPrice> customerGroupPriceList = productCustomerGroupPriceService.findList(productCustomerGroupPrice);
		if(CollectionUtils.isNotEmpty(customerGroupPriceList)) {
			if(HANDLER_TYPE_MAP.equals(type)) {
				Map<String, Double> customerGroupPriceMap = new HashMap<>();
				customerGroupPriceList.forEach(cgp -> {
					customerGroupPriceMap.put(cgp.getCustomerGroupId(), cgp.getPrice());
				});
				product.setCustomerGroupPrice(customerGroupPriceMap);
			} else if(HANDLER_TYPE_LIST.equals(type)) {
				product.setProductCustomerGroupPrices(customerGroupPriceList);
			}
		}
	}

	@Transactional(readOnly = false)
	public void save(Product product) {
		super.save(product);
		String productId = product.getId();
		// 查询商品分类和品牌的关系，不存在关联关系，否则向数据库插入一条数据
		if(StringUtils.hasText(product.getProductBrandId()) && StringUtils.hasText(product.getProductCategoryId())) {
			ProductCategoryBrand productCategoryBrand = new ProductCategoryBrand();
			productCategoryBrand.setSupplierId(product.getSupplierId());
			productCategoryBrand.setProductBrandId(product.getProductBrandId());
			productCategoryBrand.setProductCategoryId(product.getProductCategoryId());
			ProductCategoryBrand pcb = productCategoryBrandService.get(productCategoryBrand);
			if(BeanUtils.isEmpty(pcb)) {
				productCategoryBrandService.save(productCategoryBrand);
			}
		}
		// 保存客户指定价格
		Map<String, Double> customerPrice = product.getCustomerPrice();
		if(CollectionUtils.isNotEmpty(customerPrice)) {
			ProductCustomerPrice productCustomerPrice = new ProductCustomerPrice();;
			productCustomerPrice.setProductId(productId);
			productCustomerPriceService.deleteByCondition(productCustomerPrice);
			
			for (Map.Entry<String, Double> entry : customerPrice.entrySet()) {
				String customerId = entry.getKey();
				Double price = entry.getValue();
				productCustomerPrice = new ProductCustomerPrice();
				productCustomerPrice.setPrice(price);
				productCustomerPrice.setProductId(product.getId());
				productCustomerPrice.setCustomerId(customerId);
				productCustomerPriceService.save(productCustomerPrice);
			}
		}

		// 保存客户组指定价格
		Map<String, Double> customerGroupPrice = product.getCustomerGroupPrice();
		if(CollectionUtils.isNotEmpty(customerGroupPrice)) {
			// 先删除
			ProductCustomerGroupPrice productCustomerGroupPrice = new ProductCustomerGroupPrice();
			productCustomerGroupPrice.setProductId(product.getId());
			productCustomerGroupPriceService.deleteByCondition(productCustomerGroupPrice);
			
			for (Map.Entry<String, Double> entry : customerGroupPrice.entrySet()) {
				String customerGroupId = entry.getKey();
				Double price = entry.getValue();
				productCustomerGroupPrice = new ProductCustomerGroupPrice();
				productCustomerGroupPrice.setCustomerGroupId(customerGroupId);
				productCustomerGroupPrice.setPrice(price);
				productCustomerGroupPrice.setProductId(product.getId());
				productCustomerGroupPriceService.save(productCustomerGroupPrice);
			}
		}
		
		if (product.getPictures() != null && product.getPictures().length > 0) {
			ProductPicture productPicture = new ProductPicture();
			productPicture.setProductId(product.getId());
			productPictureService.deleteByCondition(productPicture);
			String[] pictures = product.getPictures();
			ProductPicture pp;
			for (String picUrl : pictures) {
				if(StringUtils.hasText(picUrl)) {
					pp = new ProductPicture();
					pp.setProductId(product.getId());
					pp.setIsMain(Constants.NO);
					pp.setUrl(picUrl);
					productPictureService.save(pp);
				}
			}
		}
		
		if (StringUtils.isNotEmpty(product.getCoverUrl())) {
			ProductPicture productPicture = new ProductPicture();
			productPicture.setProductId(product.getId());
			productPicture.setUrl(product.getCoverUrl());
			productPicture = productPictureService.get(productPicture);
			if(BeanUtils.isNotEmpty(productPicture)) {
				ProductPicture pp = new ProductPicture();
				pp.setProductId(product.getId());
				pp.setIsMain(Constants.NO);
				productPictureService.updateIsMain(pp);
				productPicture.setIsMain(Constants.YES);
				productPictureService.save(productPicture);
			}
		}
		
		// 保存多规格
		saveSpecification(product);
		
	}

	@Transactional(readOnly = false)
	public void delete(Product product) {
		// 删除商品
		super.delete(product);
		String productId = product.getId();
		// 删除商品客户指定价和客户组指定价
		ProductCustomerPrice pcp = new ProductCustomerPrice();
		pcp.setProductId(productId);
		productCustomerPriceService.deleteByCondition(pcp);
		
		ProductCustomerGroupPrice pcgp = new ProductCustomerGroupPrice();
		pcgp.setProductId(productId);
		productCustomerGroupPriceService.deleteByCondition(pcgp);
		
		// 删除商品图片数据库记录
		ProductPicture pp = new ProductPicture();
		pp.setProductId(productId);
		productPictureService.deleteByCondition(pp);
		
	}




}