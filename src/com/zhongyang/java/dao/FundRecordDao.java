package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.vo.fund.FundRecordCalenderVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月7日 上午10:52:31
* 类说明
*/
public interface FundRecordDao {
	
	public int createUserRecord(FundRecord fundRecord)throws Exception;
	
	public int  updateFundRecordByOrderId(FundRecord fundRecord);

	public FundRecord findFundRecordByOrderId(FundRecord fundRecord);

	public List<FundRecord> findFundRecordListByOrderId(FundRecord fundRecord);
	
	public List<FundRecord>selectFundRecordsByParams(FundRecord fundRecord);
	
	public List<FundRecord>selectByConditions(String userId, String date);

	/*
	 * 根据订单号和资金操作查询资金记录
	 */
	public FundRecord selectFundRecord(FundRecord fundRecord)throws Exception;
	/*
	 * 根据操作和订单号修改记录状态
	 */
	public void updateFundRecord(FundRecord fundRecord);
    
	/*
	 * 用户资金日历
	 */
	public List<FundRecordCalenderVo> userFundRecordCalendar(FundRecordCalenderVo fundRecordCalenderVo) throws Exception;

	public List<FundRecordCalenderVo> repmentfundRecordCalenderVos(FundRecordCalenderVo fundRecordCalenderVo) throws Exception;
	


}
