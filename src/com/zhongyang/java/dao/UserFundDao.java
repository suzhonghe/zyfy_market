package com.zhongyang.java.dao;

import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.vo.UserFundVo;

/**
 * @author 作者:zhaofq
 * @version 创建时间：2015年12月2日 上午9:44:49 类说明:用户资金
 */
public interface UserFundDao {
    /**
     * 
     * @param clientCode
     * @param userid
     * @return:Obiect,
     * 通过用户Id查询用户资金表
     */
	public UserFund byUserID(UserFund userFund);

	/*
	 * 修改用户资金记录信息
	 */
	public int updateUserFund(UserFund userFund);
	
	public int addUserFund(UserFund UserFund);
	/*
	 * 根据用户Id修改用户充值金额和可用金额
	 */
	public int updateUserFundByUserID(UserFund UserFund);

	public UserFund byUserFundId(String userId) throws Exception;

} 
