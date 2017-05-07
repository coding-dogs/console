/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.supplier.dao;

import java.util.List;

import com.easyorder.modules.supplier.entity.Supplier;
import com.easyorder.modules.supplier.vo.SupplierVO;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;

/**
 * 供货商DAO接口
 * @author qiudequan
 * @version 2017-04-22
 */
@MyBatisDao
public interface SupplierDao extends CrudDao<Supplier> {
	/**
	 * 根据ID获取供应商信息
	 * @param id 供应商ID
	 * @return 供应商信息
	 */
	public SupplierVO getById(String id);
	
	/**
	 * 根据用户ID获取其关联供应商信息
	 * @param userId
	 * @return 关联供应商信息
	 */
	public List<SupplierVO> getByUserId(String userId);
}