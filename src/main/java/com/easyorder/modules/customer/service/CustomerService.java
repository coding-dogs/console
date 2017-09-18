/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.customer.service;

import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.common.enums.CustomerStatusEnums;
import com.easyorder.common.utils.BeanUtils;
import com.easyorder.common.utils.MD5Utils;
import com.easyorder.common.utils.StringUtils;
import com.easyorder.modules.customer.dao.CustomerDao;
import com.easyorder.modules.customer.dao.SupplierCustomerDao;
import com.easyorder.modules.customer.entity.Contact;
import com.easyorder.modules.customer.entity.Customer;
import com.easyorder.modules.customer.entity.SupplierCustomer;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

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
	@Autowired
	private SupplierCustomerDao supplierCustomerDao;
	
	public Customer get(String id) {
		User user = UserUtils.getUser();
		Customer customer = new Customer(id);
		if(BeanUtils.isNotEmpty(user)) {
			customer.setSupplierId(user.getSupplierId());
		}
		return super.get(customer);
	}
	
	public List<Customer> findList(Customer customer) {
		return super.findList(customer);
	}
	
	public Page<Customer> findPage(Page<Customer> page, Customer customer) {
		return super.findPage(page, customer);
	}
	
	public Page<Customer> searchPage(Page<Customer> page, Customer customer) {
		customer.setPage(page);
		page.setList(dao.findByCondition(customer));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(Customer customer) {
		String password = customer.getPassword();
		if(StringUtils.isNotEmpty(password)) {
			String md5 = MD5Utils.MD5(password);
			customer.setPassword(md5);
		}
		super.save(customer);
		
		// 保存联系人
		if(StringUtils.isNotEmpty(customer.getContactId())) {
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
		
		
		SupplierCustomer supplierCustomer = new SupplierCustomer();
		supplierCustomer.setSupplierId(customer.getSupplierId());
		supplierCustomer.setCustomerId(customer.getId());
		SupplierCustomer sc = supplierCustomerDao.get(supplierCustomer);
		String customerGroupId = customer.getCustomerGroupId();
		if(BeanUtils.isNotEmpty(sc)) {
			sc.setCustomerGroupId(customerGroupId);
			sc.preUpdate();
			supplierCustomerDao.update(sc);
		} else if(BeanUtils.isEmpty(sc) && StringUtils.isNotEmpty(customerGroupId)) {
			supplierCustomer.setCustomerGroupId(customerGroupId);
			// 默认为待激活状态
			supplierCustomer.setMtCustomerStatusCd(customer.getMtCustomerStatusCd());
			supplierCustomer.preInsert();
			supplierCustomerDao.insert(supplierCustomer);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Customer customer) {
		super.delete(customer);
	}
	
	public Customer getByCondition(Customer customer) {
		List<Customer> resultList = dao.findByCondition(customer);
		if(CollectionUtils.isEmpty(resultList)) {
			return null;
		}
		return resultList.get(0);
	}
	
	
}