/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.supplier.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.modules.supplier.dao.SysUserSupplierDao;
import com.easyorder.modules.supplier.entity.SysUserSupplier;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;

/**
 * 用户-供应商Service
 * @author qiudequan
 * @version 2017-05-07
 */
@Service
@Transactional(readOnly = true)
public class SysUserSupplierService extends CrudService<SysUserSupplierDao, SysUserSupplier> {

	public SysUserSupplier get(String id) {
		return super.get(id);
	}
	
	public List<SysUserSupplier> findList(SysUserSupplier sysUserSupplier) {
		return super.findList(sysUserSupplier);
	}
	
	public Page<SysUserSupplier> findPage(Page<SysUserSupplier> page, SysUserSupplier sysUserSupplier) {
		return super.findPage(page, sysUserSupplier);
	}
	
	@Transactional(readOnly = false)
	public void save(SysUserSupplier sysUserSupplier) {
		super.save(sysUserSupplier);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysUserSupplier sysUserSupplier) {
		super.delete(sysUserSupplier);
	}
	
	
	
	
}