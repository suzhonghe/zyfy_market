package com.zhongyang.java.service.impl;
/**
 * 
* @Title: UmpTenderTransferRecordServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description: 联动交易记录业务实现 
* @author 苏忠贺   
* @date 2015年12月10日 下午4:47:28 
* @version V1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UmpTenderTransferRecordDao;
import com.zhongyang.java.pojo.UmpTenderTransferRecord;
import com.zhongyang.java.service.UmpTenderTransferRecordService;

@Service
public class UmpTenderTransferRecordServiceImpl implements UmpTenderTransferRecordService{
	
	@Autowired
	private UmpTenderTransferRecordDao uttrDao;

	@Override
	public void addUmpTenderTransferRecord(UmpTenderTransferRecord uttRecord) throws Exception {
		uttrDao.insertUmpTenderTransferRecord(uttRecord);
	}

	@Override
	public void modifyUmpTenderTransferRecord(UmpTenderTransferRecord uttRecord) throws Exception {
		uttrDao.updateUmpTenderTransferRecord(uttRecord);
	}

}
