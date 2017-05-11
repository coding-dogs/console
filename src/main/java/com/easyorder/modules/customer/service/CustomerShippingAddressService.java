/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.easyorder.modules.customer.entity.CustomerShippingAddress;
import com.easyorder.modules.customer.dao.CustomerShippingAddressDao;

/**
 * 客户收货地址Service
 * @author qiudequan
 * @version 2017-05-11
 */
@Service
@Transactional(readOnly = true)
public class CustomerShippingAddressService extends CrudService<CustomerShippingAddressDao, CustomerShippingAddress> {

	public CustomerShippingAddress get(String id) {
		return super.get(id);
	}
	
	public List<CustomerShippingAddress> findList(CustomerShippingAddress customerShippingAddress) {
		return super.findList(customerShippingAddress);
	}
	
	public Page<CustomerShippingAddress> findPage(Page<CustomerShippingAddress> page, CustomerShippingAddress customerShippingAddress) {
		return super.findPage(page, customerShippingAddress);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerShippingAddress customerShippingAddress) {
		super.save(customerShippingAddress);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerShippingAddress customerShippingAddress) {
		super.delete(customerShippingAddress);
	}
	
	
	
	
}