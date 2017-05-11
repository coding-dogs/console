/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.easyorder.modules.customer.entity.CustomerGroup;
import com.easyorder.modules.customer.dao.CustomerGroupDao;

/**
 * 客户组Service
 * @author qiudequan
 * @version 2017-05-11
 */
@Service
@Transactional(readOnly = true)
public class CustomerGroupService extends CrudService<CustomerGroupDao, CustomerGroup> {

	public CustomerGroup get(String id) {
		return super.get(id);
	}
	
	public List<CustomerGroup> findList(CustomerGroup customerGroup) {
		return super.findList(customerGroup);
	}
	
	public Page<CustomerGroup> findPage(Page<CustomerGroup> page, CustomerGroup customerGroup) {
		return super.findPage(page, customerGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerGroup customerGroup) {
		super.save(customerGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerGroup customerGroup) {
		super.delete(customerGroup);
	}
	
	
	
	
}