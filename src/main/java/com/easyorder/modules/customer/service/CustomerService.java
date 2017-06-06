/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.easyorder.modules.customer.entity.Contact;
import com.easyorder.modules.customer.entity.Customer;
import com.easyorder.common.utils.BeanUtils;
import com.easyorder.common.utils.MD5Utils;
import com.easyorder.common.utils.StringUtils;
import com.easyorder.modules.customer.dao.CustomerDao;

/**
 * 客户Service
 * @author qiudequan
 * @version 2017-05-11
 */
@Service
@Transactional(readOnly = true)
public class CustomerService extends CrudService<CustomerDao, Customer> {

	@Autowired
	private ContactService contactService;
	
	public Customer get(String id) {
		return super.get(id);
	}
	
	public List<Customer> findList(Customer customer) {
		return super.findList(customer);
	}
	
	public Page<Customer> findPage(Page<Customer> page, Customer customer) {
		return super.findPage(page, customer);
	}
	
	@Transactional(readOnly = false)
	public void save(Customer customer) {
		User user = UserUtils.getUser();
		if(BeanUtils.isNotEmpty(user)) {
			customer.setSupplierId(user.getSupplierId());
		}
		String password = customer.getPassword();
		if(StringUtils.isNotEmpty(password)) {
			String md5 = MD5Utils.MD5(password);
			customer.setPassword(md5);
		}
		super.save(customer);
		
		// 保存联系人
		Contact contact = contactService.get(customer.getContactId());
		if(BeanUtils.isEmpty(contact)) {
			contact = new Contact();
		}
		contact.setId(customer.getContactId());
		contact.setName(customer.getContactName());
		contact.setAddress(customer.getContactAddress());
		contact.setEmail(customer.getContactEmail());
		contact.setPhone(customer.getContactPhone());
		contact.setCustomerId(customer.getId());
		if(BeanUtils.isNotEmpty(contact)) {
			contactService.save(contact);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Customer customer) {
		super.delete(customer);
	}
	
	
	
	
}