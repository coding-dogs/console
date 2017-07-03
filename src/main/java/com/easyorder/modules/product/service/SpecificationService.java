/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.easyorder.modules.product.entity.Specification;
import com.easyorder.modules.product.dao.SpecificationDao;

/**
 * 商品规格Service
 * @author qiudequan
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class SpecificationService extends CrudService<SpecificationDao, Specification> {

	public Specification get(String id) {
		return super.get(id);
	}
	
	public List<Specification> findList(Specification specification) {
		return super.findList(specification);
	}
	
	public Page<Specification> findPage(Page<Specification> page, Specification specification) {
		return super.findPage(page, specification);
	}
	
	@Transactional(readOnly = false)
	public void save(Specification specification) {
		super.save(specification);
	}
	
	@Transactional(readOnly = false)
	public void delete(Specification specification) {
		super.delete(specification);
	}
	
	
	
	
}