/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.easyorder.modules.product.entity.Unit;
import com.easyorder.modules.product.dao.UnitDao;

/**
 * 商品类目单位Service
 * @author qiudequan
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class UnitService extends CrudService<UnitDao, Unit> {

	public Unit get(String id) {
		return super.get(id);
	}
	
	public List<Unit> findList(Unit unit) {
		return super.findList(unit);
	}
	
	public Page<Unit> findPage(Page<Unit> page, Unit unit) {
		return super.findPage(page, unit);
	}
	
	@Transactional(readOnly = false)
	public void save(Unit unit) {
		super.save(unit);
	}
	
	@Transactional(readOnly = false)
	public void delete(Unit unit) {
		super.delete(unit);
	}
	
	
	
	
}