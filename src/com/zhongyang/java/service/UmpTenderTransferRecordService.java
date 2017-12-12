package com.zhongyang.java.service;

import com.zhongyang.java.pojo.UmpTenderTransferRecord;

/**
 * 
* @Title: UmpTenderTransferRecord.java 
* @Package com.zhongyang.java.dao 
* @Description: 联动交易记录业务接口 
* @author 苏忠贺   
* @date 2015年12月10日 下午3:45:59 
* @version V1.0
 */
public interface UmpTenderTransferRecordService {
	
	/*
	 * 添加一天记录
	 */
	public void addUmpTenderTransferRecord(UmpTenderTransferRecord uttRecord)throws Exception;
	
	public void modifyUmpTenderTransferRecord(UmpTenderTransferRecord uttRecord)throws Exception;
}
