/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.supplier.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.modules.supplier.dao.SupplierDao;
import com.easyorder.modules.supplier.dao.SysUserSupplierDao;
import com.easyorder.modules.supplier.entity.Supplier;
import com.easyorder.modules.supplier.entity.SysUserSupplier;
import com.easyorder.modules.supplier.vo.SupplierVO;
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
	@Autowired
	private SysUserSupplierDao sysUserSupplierDao;
	
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
		// 保存用户与供应商关系
		SysUserSupplier sysUserSupplier = new SysUserSupplier();
		sysUserSupplier.preInsert();
		sysUserSupplier.setSysUserId(supplier.getManagerId());
		sysUserSupplier.setSupplierId(supplier.getId());
		sysUserSupplierDao.insert(sysUserSupplier);
	}
	
	@Transactional(readOnly = false)
	public void delete(Supplier supplier) {
		super.delete(supplier);
	}
	
	public SupplierVO getById(String id) {
		return dao.getById(id);
	}
	
	/**
	 * 根据用户ID获取其关联供应商信息
	 * @param userId
	 * @return 关联供应商信息
	 */
	public List<SupplierVO> getByUserId(String userId) {
		return dao.getByUserId(userId);
	}
	
	
}