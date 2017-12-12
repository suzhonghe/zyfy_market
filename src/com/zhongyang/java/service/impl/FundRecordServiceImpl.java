package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.FundRecordDao;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.service.FundRecordService;
import com.zhongyang.java.vo.fund.FundRecordCalenderVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月4日 下午1:44:45
* 类说明
*/
@Service
public class FundRecordServiceImpl implements FundRecordService {
    
	@Autowired
	FundRecordDao fundRecordDao;
	@Override
	public int create(FundRecord fundRecord)  throws Exception{
		return fundRecordDao.createUserRecord(fundRecord);

	}
	
    @Override
	public List<FundRecord> queryFundRecordsByParams(FundRecord fundRecord) {
		return fundRecordDao.selectFundRecordsByParams(fundRecord);
	}

	/*
     * (non-Javadoc)
     * @see com.zhongyang.java.service.FundRecordService#updateFundRecordByOrderId()
     * 根据orderId更新资金状态
     */
	public int updateFundRecordByOrderId(FundRecord fundRecord) {

		return fundRecordDao.updateFundRecordByOrderId(fundRecord);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.zhongyang.java.service.FundRecordService#findFundRecordByOrderId(com.zhongyang.java.pojo.FundRecord)
	 * 根据orderId查询用户ID
	 *
	 */
	public FundRecord findFundRecordByOrderId(FundRecord fundRecord) {
		
		return fundRecordDao.findFundRecordByOrderId(fundRecord);
	}


	public List<FundRecord> findFundRecordListByOrderId(FundRecord fundRecord) {
		
		return fundRecordDao.findFundRecordListByOrderId(fundRecord);
	}
	public FundRecord queryFundRecord(FundRecord fundRecord) throws Exception {
		return fundRecordDao.selectFundRecord(fundRecord);
	}

	@Override
	public void modifyFundRecord(FundRecord fundRecord){
		fundRecordDao.updateFundRecord(fundRecord);

	}

	@Override
	public List<FundRecordCalenderVo> userFundRecordCalendar(FundRecordCalenderVo fundRecordCalenderVo) throws Exception {
		return fundRecordDao.userFundRecordCalendar(fundRecordCalenderVo);
	}

	@Override
	public List<FundRecordCalenderVo> repmentfundRecordCalenderVos(FundRecordCalenderVo fundRecordCalenderVo)
			throws Exception {
		return fundRecordDao.repmentfundRecordCalenderVos(fundRecordCalenderVo);
	}

	@Override
	public List<FundRecord> queryByConditions(String userId, String date) {
		return fundRecordDao.selectByConditions(userId, date);
	}

}
