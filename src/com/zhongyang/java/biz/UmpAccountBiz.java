package com.zhongyang.java.biz;

import com.zhongyang.java.vo.app.OperatingFundsVo;
import com.zhongyang.java.vo.fund.UmpRechargeVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月2日 下午5:18:24
* 类说明：用户ump账户
*/
public interface UmpAccountBiz {
    
	/*
     *根据用户Id获取用户umpAccount信息 
     */
	public String byUserIdUmpAccount(OperatingFundsVo operatingFundsVo);
	/*
     *根据用户Id获取用户umpAccount信息:提现 
     */
	public String byUserIdAccount(OperatingFundsVo operatingFundsVo);
	/*
	 * 用户转账到平台
	 */
	public String transferToBusiness(String transferAmount, String string);
	
	public void userToBusinessCallBack(UmpRechargeVo umpRechargeVo);
    

}
