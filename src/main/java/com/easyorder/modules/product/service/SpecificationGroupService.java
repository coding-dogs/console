/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.easyorder.modules.product.entity.SpecificationGroup;
import com.easyorder.modules.product.dao.SpecificationGroupDao;

/**
 * 商品规格组Service
 * @author qiudequan
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class SpecificationGroupService extends CrudService<SpecificationGroupDao, SpecificationGroup> {

	public SpecificationGroup get(String id) {
		return super.get(id);
	}
	
	public List<SpecificationGroup> findList(SpecificationGroup specificationGroup) {
		return super.findList(specificationGroup);
	}
	
	public Page<SpecificationGroup> findPage(Page<SpecificationGroup> page, SpecificationGroup specificationGroup) {
		return super.findPage(page, specificationGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(SpecificationGroup specificationGroup) {
		super.save(specificationGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(SpecificationGroup specificationGroup) {
		super.delete(specificationGroup);
	}
	
	
	
	
}