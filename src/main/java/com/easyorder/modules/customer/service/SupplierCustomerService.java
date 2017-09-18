package com.easyorder.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.modules.customer.dao.SupplierCustomerDao;
import com.easyorder.modules.customer.entity.SupplierCustomer;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;

/**
 * 供应商及客户关系Entity
 * @author qiudequan
 * @version 2017-05-11
 */
@Service
@Transactional(readOnly = true)
public class SupplierCustomerService extends CrudService<SupplierCustomerDao, SupplierCustomer> {
	
	public SupplierCustomer get(String id) {
		return super.get(id);
	}
	
	public List<SupplierCustomer> findList(SupplierCustomer supplierCustomer) {
		return super.findList(supplierCustomer);
	}
	
	public Page<SupplierCustomer> findPage(Page<SupplierCustomer> page, SupplierCustomer supplierCustomer) {
		return super.findPage(page, supplierCustomer);
	}
	
	@Transactional(readOnly = false)
	public void save(SupplierCustomer supplierCustomer) {
		super.save(supplierCustomer);
	}
	
	@Transactional(readOnly = false)
	public void delete(SupplierCustomer supplierCustomer) {
		super.delete(supplierCustomer);
	}

}