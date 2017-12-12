package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.VirtualRecord;

public interface VirtualRecordDao {
	
	public int insertVirtualRecord(VirtualRecord virtualRecord);
	
	public int updateVirtualRecord(VirtualRecord virtualRecord);
	
	public VirtualRecord selectVirtualRecordByParams(VirtualRecord virtualRecord);
	
	public int updateRecords();
	
	//查询当日体验到期记录
	public List<VirtualRecord> selectRecordByTime();

}
