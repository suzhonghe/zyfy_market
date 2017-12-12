package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ClientFundRecordDao;
import com.zhongyang.java.pojo.ClientFundRecord;
import com.zhongyang.java.service.ClientFundRecordService;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月21日 上午11:11:42
* 类说明:平台标的交易记录
*/
@Service
public class ClientFundRecordServiceImpl implements ClientFundRecordService{
	
	@Autowired
	ClientFundRecordDao clientFundRecordDao;
	@Override
	public int addClientFundRecord(ClientFundRecord cfr) throws Exception {
		return clientFundRecordDao.addClientFundRecord(cfr);
	}
	@Override
	public int modifyClientFundRecord(ClientFundRecord clientFundRecord) {
		return clientFundRecordDao.updateClientFundRecord(clientFundRecord);
	}
}
