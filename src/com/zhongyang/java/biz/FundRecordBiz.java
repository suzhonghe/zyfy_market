package com.zhongyang.java.biz;

import java.util.List;

import com.zhongyang.java.pojo.User;
import com.zhongyang.java.vo.app.OperatingFundsVo;
import com.zhongyang.java.vo.fund.FundRecordCalenderVo;
import com.zhongyang.java.vo.fund.UmpRechargeVo;
import com.zhongyang.java.vo.fund.UmpWithdrawVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月4日 下午1:00:11
* 类说明
*/
public interface FundRecordBiz {

    /*
     * 根据orderId修改资金状态
     */
    public String updateFundRecordByOrderId(UmpRechargeVo umpRechargeVo);
    /*
     * 根据orderId修改提现资金状态
     */
	public String updateWithdrawByOrderId(UmpWithdrawVo umpWithdrawVo);
	
    /*
     * 添加资金记录
     */
	public String addNewFundRecord(OperatingFundsVo operatingFundsVo);
	
	/*
	 * 用户资金日历
	 */
	public List<FundRecordCalenderVo> userFundRecordCalendar(String newData, User user);
 
}
