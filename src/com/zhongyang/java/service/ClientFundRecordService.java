package com.zhongyang.java.service;

import com.zhongyang.java.pojo.ClientFundRecord;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月21日 上午11:11:12
* 类说明：标的交易记录
*/
public interface ClientFundRecordService {
	
    public int addClientFundRecord(ClientFundRecord cfr) throws Exception;
    
    public int modifyClientFundRecord(ClientFundRecord clientFundRecord);
}
