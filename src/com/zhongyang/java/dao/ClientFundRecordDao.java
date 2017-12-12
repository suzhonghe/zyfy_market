package com.zhongyang.java.dao;

import com.zhongyang.java.pojo.ClientFundRecord;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月21日 下午4:12:36
* 类说明
*/
public interface ClientFundRecordDao {
    /*
     * 平台资金记录
     */
	public int addClientFundRecord(ClientFundRecord clientFundRecord) throws Exception;
	
	public int updateClientFundRecord(ClientFundRecord clientFundRecord);
}
