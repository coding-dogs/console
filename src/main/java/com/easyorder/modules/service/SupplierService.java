/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.modules.dao.SupplierDao;
import com.easyorder.modules.entity.Supplier;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;

/**
 * 供货商Service
 * @author qiudequan
 * @version 2017-04-22
 */
@Service
@Transactional(readOnly = true)
public class SupplierService extends CrudService<SupplierDao, Supplier> {

	public Supplier get(String id) {
		return super.get(id);
	}
	
	public List<Supplier> findList(Supplier supplier) {
		return super.findList(supplier);
	}
	
	public Page<Supplier> findPage(Page<Supplier> page, Supplier supplier) {
		return super.findPage(page, supplier);
	}
	
	@Transactional(readOnly = false)
	public void save(Supplier supplier) {
		String supplierNo = "GHS" + System.currentTimeMillis();
		supplier.setSupplierNo(supplierNo);
		super.save(supplier);
	}
	
	@Transactional(readOnly = false)
	public void delete(Supplier supplier) {
		super.delete(supplier);
	}
	
	
	
	
}