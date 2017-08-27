/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.easyorder.modules.product.entity.Specification;
import com.easyorder.modules.product.entity.SpecificationItem;
import com.easyorder.common.utils.GSONUtils;
import com.easyorder.modules.product.dao.SpecificationDao;
import com.easyorder.modules.product.dao.SpecificationItemDao;

/**
 * 商品规格Service
 * @author qiudequan
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class SpecificationService extends CrudService<SpecificationDao, Specification> {

	@Autowired
	private SpecificationItemDao specificationItemDao;
	
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
		
		String children = specification.getChildren();
		if(!StringUtils.hasText(children)) {
			return;
		}
		children = StringEscapeUtils.unescapeHtml4(children);
		SpecificationItem si = new SpecificationItem();
		si.setSupplierId(specification.getSupplierId());
		si.setSpecificationId(specification.getId());
		List<SpecificationItem> specificationItems = specificationItemDao.findList(si);
		if(CollectionUtils.isNotEmpty(specificationItems)) {
			specificationItems.forEach(specificationItem -> {
				specificationItemDao.delete(specificationItem);
			});
		}
		
		List<SpecificationItem> sis = GSONUtils.jsonToList(children, SpecificationItem.class);
		if(CollectionUtils.isNotEmpty(sis)) {
			sis.forEach(specItem -> {
				specItem.setSpecificationId(specification.getId());
				specItem.setSupplierId(specification.getSupplierId());
				specItem.preInsert();
				specificationItemDao.insert(specItem);
			});
		}
		
	}
	
	@Transactional(readOnly = false)
	public void delete(Specification specification) {
		super.delete(specification);
	}
	
	
	
	
}