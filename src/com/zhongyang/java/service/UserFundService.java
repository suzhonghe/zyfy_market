package com.zhongyang.java.service;

import com.zhongyang.java.pojo.UserFund;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月1日 下午5:32:23
* 类说明：用户资金
*/
public interface UserFundService {
	
	
    public UserFund byUserID(UserFund userFund);
    /*
	 * 修改用户资金记录信息
	 */
	public int modifyUserFund(UserFund userFund);

    public void addUserFund(UserFund UserFund);
    
    public int updateUserFundByUserID(UserFund UserFund);
    
	public UserFund byUserFundId(String id) throws Exception;

}
