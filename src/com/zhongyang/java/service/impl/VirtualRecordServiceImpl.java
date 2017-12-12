package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.VirtualRecordDao;
import com.zhongyang.java.pojo.VirtualRecord;
import com.zhongyang.java.service.VirtualRecordService;

@Service
public class VirtualRecordServiceImpl implements VirtualRecordService {
	
	@Autowired
	private VirtualRecordDao virtualRecordDao;

	@Override
	public int addVirtualRecord(VirtualRecord virtualRecord) {
		return virtualRecordDao.insertVirtualRecord(virtualRecord);
	}

	@Override
	public int modifyVirtualRecord(VirtualRecord virtualRecord) {
		return virtualRecordDao.updateVirtualRecord(virtualRecord);
	}

	@Override
	public VirtualRecord queryVirtualRecordByParams(VirtualRecord virtualRecord) {
		return virtualRecordDao.selectVirtualRecordByParams(virtualRecord);
	}

	@Override
	public List<VirtualRecord> queryRecordByTime() {
		return virtualRecordDao.selectRecordByTime();
	}
}
