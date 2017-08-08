package com.easyorder.modules.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyorder.modules.common.dao.SequenceDao;

@Service(value = "sequenceService")
@Transactional(readOnly = true)
public class SequenceService {
	
	@Autowired
	private SequenceDao dao;
	
	@Transactional(readOnly = false)
	public Long getSeqByType(String type) {
		return dao.getSeqByType(type);
	}
}
