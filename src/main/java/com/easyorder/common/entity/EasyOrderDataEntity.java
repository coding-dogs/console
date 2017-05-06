package com.easyorder.common.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.jeeplus.common.utils.IdGen;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

public abstract class EasyOrderDataEntity<T> extends com.jeeplus.common.persistence.DataEntity<T> {

	private static final long serialVersionUID = 6875976631648902307L;

	protected Integer version;

	protected EasyOrderDataEntity() {

	}

	protected EasyOrderDataEntity(String id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert() {
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (!this.isNewRecord) {
			setId(IdGen.uuid());
		}
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())) {
			this.updateBy = user;
			this.createBy = user;
		}
		this.updateDate = new Date();
		this.createDate = this.updateDate;
		this.version = 0;
	}

}
