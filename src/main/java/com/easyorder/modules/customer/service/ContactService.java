/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.modules.customer.dao.ContactDao;
import com.easyorder.modules.customer.entity.Contact;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;

/**
 * 联系人Service
 * @author qiudequan
 * @version 2017-06-05
 */
@Service
@Transactional(readOnly = true)
public class ContactService extends CrudService<ContactDao, Contact> {

	public Contact get(String id) {
		return super.get(id);
	}
	
	public List<Contact> findList(Contact contact) {
		return super.findList(contact);
	}
	
	public Page<Contact> findPage(Page<Contact> page, Contact contact) {
		return super.findPage(page, contact);
	}
	
	@Transactional(readOnly = false)
	public void save(Contact contact) {
		super.save(contact);
	}
	
	@Transactional(readOnly = false)
	public void delete(Contact contact) {
		super.delete(contact);
	}
	
	
	
	
}