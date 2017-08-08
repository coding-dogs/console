package com.easyorder.modules.common.dao;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface SequenceDao {
	Long getSeqByType(@Param(value = "type") String type);
}
