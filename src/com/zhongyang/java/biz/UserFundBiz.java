package com.zhongyang.java.biz;

import com.zhongyang.java.pojo.User;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.vo.UserFundVo;
/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月1日 下午5:22:40
* 类说明：用户资金
*/
public interface UserFundBiz {
    

	public UserFundVo byUserId(UserFundVo userFundVo) throws Exception;
	
	public void addUserFund(UserFund UserFund) throws Exception;

	public UserFundVo byUserIdWithdraw(UserFundVo userFundVo) throws Exception;
	
	public UserFundVo queryByUseId(User user);

	public UserFund byUserFundId(String id);

	public UserFundVo mobileUserFundRecharge(UserFundVo userFundVo);

}
