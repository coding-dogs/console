/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.customer.dao;

import com.easyorder.modules.customer.entity.Contact;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;

/**
 * 联系人DAO接口
 * @author qiudequan
 * @version 2017-06-05
 */
@MyBatisDao
public interface ContactDao extends CrudDao<Contact> {

	
}