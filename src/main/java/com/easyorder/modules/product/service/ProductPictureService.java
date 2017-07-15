/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.common.utils.DateUtils;
import com.easyorder.modules.product.dao.ProductPictureDao;
import com.easyorder.modules.product.entity.ProductPicture;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 商品图片Service
 * @author qiudequan
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class ProductPictureService extends CrudService<ProductPictureDao, ProductPicture> {

	public ProductPicture get(String id) {
		return super.get(id);
	}
	
	public List<ProductPicture> findList(ProductPicture productPicture) {
		return super.findList(productPicture);
	}
	
	public Page<ProductPicture> findPage(Page<ProductPicture> page, ProductPicture productPicture) {
		return super.findPage(page, productPicture);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductPicture productPicture) {
		super.save(productPicture);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductPicture productPicture) {
		super.delete(productPicture);
	}
	
	@Transactional(readOnly = false)
	public void updateIsMain(ProductPicture productPicture) {
		User user = UserUtils.getUser();
		productPicture.setUpdateBy(user);
		productPicture.setUpdateDate(DateUtils.getCurrentDate());
		dao.updateIsMain(productPicture);
	}
	
	@Transactional(readOnly = false)
	public void deleteByCondition(ProductPicture productPicture) {
		dao.deleteByCondition(productPicture);
	}
	
}