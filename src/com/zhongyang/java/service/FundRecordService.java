package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.vo.fund.FundRecordCalenderVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月4日 下午1:44:23
* 类说明
*/
public interface FundRecordService {

	public int create(FundRecord fundRecord) throws Exception;
	
    public int updateFundRecordByOrderId(FundRecord fundRecord);

	public FundRecord findFundRecordByOrderId(FundRecord fundRecord);

	public List<FundRecord> findFundRecordListByOrderId(FundRecord fundRecord) throws Exception;
	
	public List<FundRecord>queryFundRecordsByParams(FundRecord fundRecord);
	/*
	 * 根据订单号和资金操作查询资金记录
	 */
	public FundRecord queryFundRecord(FundRecord fundRecord)throws Exception;
	/*
	 * 根据Id修改记录状态
	 */
	public void modifyFundRecord(FundRecord fundRecord);

	public List<FundRecordCalenderVo> userFundRecordCalendar(FundRecordCalenderVo fundRecordCalenderVo) throws Exception;

	public List<FundRecordCalenderVo> repmentfundRecordCalenderVos(FundRecordCalenderVo fundRecordCalenderVo) throws Exception;
	
	public List<FundRecord>queryByConditions(String userId, String date);
}
