package com.zhongyang.java.dao;

import com.zhongyang.java.pojo.UmpTenderTransferRecord;

/**
 * 
* @Title: UmpTenderTransferRecord.java 
* @Package com.zhongyang.java.dao 
* @Description: 联动交易记录DAO 
* @author 苏忠贺   
* @date 2015年12月10日 下午3:45:59 
* @version V1.0
 */
public interface UmpTenderTransferRecordDao {
	
	/*
	 * 添加一天记录
	 */
	public void insertUmpTenderTransferRecord(UmpTenderTransferRecord uttRecord)throws Exception;
	
	public void updateUmpTenderTransferRecord(UmpTenderTransferRecord uttRecord)throws Exception;
}
