package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.VirtualRecord;

public interface VirtualRecordService {
	
	public int addVirtualRecord(VirtualRecord virtualRecord);
	
	public int modifyVirtualRecord(VirtualRecord virtualRecord);
	
	public VirtualRecord queryVirtualRecordByParams(VirtualRecord virtualRecord);
	
	public List<VirtualRecord> queryRecordByTime();
}
