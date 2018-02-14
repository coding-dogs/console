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
import com.easyorder.modules.product.entity.ProductSpecificationCustomerGroupPrice;
import com.easyorder.modules.product.entity.ProductSpecificationCustomerPrice;
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
	@Autowired
	ProductSpecificationCustomerGroupPriceService productSpecificationCustomerGroupPriceService;
	@Autowired
	ProductSpecificationCustomerPriceService productSpecificationCustomerPriceService;

	public Product get(String id) {
		Product product = super.get(id);
		if(BeanUtils.isNotEmpty(product)) {
			handlerPrice(product, HANDLER_TYPE_LIST);
			handlerSpecification(product);
		}
		return product;
	}

	public Product get(Product product) {
		Product p = super.get(product);
		if(BeanUtils.isNotEmpty(p)) {
			handlerPrice(p, HANDLER_TYPE_LIST);
			handlerSpecification(p);
		}
		return p;
	}

	/**
	 * 处理多规格信息
	 * @param product
	 */
	private void handlerSpecification(Product product) {
		ProductSpecification ps = new ProductSpecification();
		String productId = product.getId();
		String supplierId = product.getSupplierId();
		ps.setProductId(productId);
		ps.setSupplierId(supplierId);
		List<ProductSpecification> psList = productSpecificationService.findList(ps);
		if(CollectionUtils.isEmpty(psList)) {
			return;
		}
		psList.forEach(psl -> {
			// 查询多规格客户组指定价格
			ProductSpecificationCustomerGroupPrice pscgp = new ProductSpecificationCustomerGroupPrice();
			pscgp.setProductSpecificationId(psl.getId());
			List<ProductSpecificationCustomerGroupPrice> customerGroupPriceList = productSpecificationCustomerGroupPriceService.findList(pscgp);
			// 查询多规格客户指定价格
			ProductSpecificationCustomerPrice pscp = new ProductSpecificationCustomerPrice();
			pscp.setProductSpecificationId(psl.getId());
			List<ProductSpecificationCustomerPrice> customerPriceList = productSpecificationCustomerPriceService.findList(pscp);
			psl.setProductSpecificationCustomerGroupPrices(customerGroupPriceList);
			psl.setProductSpecificationCustomerPrices(customerPriceList);
		});
		product.setSpecJson(GSONUtils.toJSON(psList));

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
			for (ProductSpecification psl : psList) {
				productSpecificationService.delete(psl);
				// 清除相应的客户组指定价格
				ProductSpecificationCustomerGroupPrice pscgp = new ProductSpecificationCustomerGroupPrice();
				pscgp.setProductSpecificationId(psl.getId());
				List<ProductSpecificationCustomerGroupPrice> customerGroupPriceList = productSpecificationCustomerGroupPriceService.findList(pscgp);
				if(CollectionUtils.isNotEmpty(customerGroupPriceList)) {
					customerGroupPriceList.forEach(cgpl -> { productSpecificationCustomerGroupPriceService.delete(cgpl); });
				}
				// 清除相应的客户指定价格
				ProductSpecificationCustomerPrice pscp = new ProductSpecificationCustomerPrice();
				pscgp.setProductSpecificationId(psl.getId());
				List<ProductSpecificationCustomerPrice> customerPriceList = productSpecificationCustomerPriceService.findList(pscp);
				if(CollectionUtils.isNotEmpty(customerPriceList)) {
					customerPriceList.forEach(cpl -> { productSpecificationCustomerPriceService.delete(cpl); });
				}
			}
		}
		String specJson = product.getSpecJson();
		if(!StringUtils.hasText(specJson)) {
			return;
		}

		List<ProductSpecification> pss = GSONUtils.jsonToList(specJson, ProductSpecification.class);
		if(CollectionUtils.isEmpty(pss)) {
			return;
		}

		for (ProductSpecification productSpec : pss) {
			// 由于原先记录已经清除，此处提出id属性，直接视为新增
			productSpec.setId(null);
			productSpec.setSupplierId(supplierId);
			productSpec.setProductId(productId);
			productSpecificationService.save(productSpec);

			List<ProductSpecificationCustomerGroupPrice> pscgpList = productSpec.getProductSpecificationCustomerGroupPrices();
			List<ProductSpecificationCustomerPrice> pscpList = productSpec.getProductSpecificationCustomerPrices();
			// 保存多规格客户组指定价格
			if(CollectionUtils.isNotEmpty(pscgpList)) {
				pscgpList.forEach(cgpl -> {
					cgpl.setId(null);
					cgpl.setProductSpecificationId(productSpec.getId());
					productSpecificationCustomerGroupPriceService.save(cgpl);
				});
			}
			// 保存多规格客户指定价格
			if(CollectionUtils.isNotEmpty(pscpList)) {
				pscpList.forEach(cpl -> {
					cpl.setId(null);
					cpl.setProductSpecificationId(productSpec.getId());
					productSpecificationCustomerPriceService.save(cpl);
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
		productCustomerPrice.setSupplierId(product.getSupplierId());
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
		// 两种情况：1.保存基本信息  2.保存图片及详情信息
		if("1".equals(product.getStep())) {
			saveProductBaseInfo(product);
		} else if("2".equals(product.getStep())) {
			savePictureAndDetail(product);
		}
	}

	private void saveProductBaseInfo(Product product) {
		super.save(product);
		// 保存分类与品牌关联关系
		saveCategoryBrand(product);
		
		// 保存商品指定价格(含客户与客户组)
		saveExclusivePrice(product);

		// 保存多规格信息
		saveSpecification(product);
	}

	/**
	 * 保存商品指定价格
	 * @param product
	 * @param productId
	 */
	private void saveExclusivePrice(Product product) {
		// 保存客户指定价格
		Map<String, Double> customerPrice = product.getCustomerPrice();
		if(CollectionUtils.isNotEmpty(customerPrice)) {
			ProductCustomerPrice productCustomerPrice = new ProductCustomerPrice();;
			productCustomerPrice.setProductId(product.getId());
			productCustomerPriceService.deleteByCondition(productCustomerPrice);
			// 不忽略价格信息才添加客户指定价格
			if(Constants.NO.equals(product.getIgnorePriceExistSpec())) {
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
		}

		// 保存客户组指定价格
		Map<String, Double> customerGroupPrice = product.getCustomerGroupPrice();
		if(CollectionUtils.isNotEmpty(customerGroupPrice)) {
			// 先删除
			ProductCustomerGroupPrice productCustomerGroupPrice = new ProductCustomerGroupPrice();
			productCustomerGroupPrice.setProductId(product.getId());
			productCustomerGroupPriceService.deleteByCondition(productCustomerGroupPrice);
			// 不忽略价格信息才添加客户组指定价格
			if(Constants.NO.equals(product.getIgnorePriceExistSpec())) {
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
		}
	}

	/**
	 * 保存分类与品牌关联关系
	 * @param product
	 */
	private void saveCategoryBrand(Product product) {
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
	}

	/**
	 * 保存图片及详情
	 * @param product
	 */
	private void savePictureAndDetail(Product product) {
		// 保存详情等信息
		super.save(product);
		// 保存图片信息
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
		} else if((product.getPictures() == null || product.getPictures().length == 0) && "2".equals(product.getStep())) {
			// 若当前保存的是图片信息，且图片信息为空，则直接删除商品图片关联
			ProductPicture productPicture = new ProductPicture();
			productPicture.setProductId(product.getId());
			productPictureService.deleteByCondition(productPicture);
		}

		// 保存主图
		ProductPicture pp = new ProductPicture();
		pp.setProductId(product.getId());
		pp.setIsMain(Constants.NO);
		productPictureService.updateIsMain(pp);
		if (StringUtils.isNotEmpty(product.getCoverUrl())) {
			ProductPicture productPicture = new ProductPicture();
			productPicture.setProductId(product.getId());
			productPicture.setUrl(product.getCoverUrl());
			productPicture = productPictureService.get(productPicture);
			if(BeanUtils.isNotEmpty(productPicture)) {
				productPicture.setIsMain(Constants.YES);
				productPictureService.save(productPicture);
			}
		}
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