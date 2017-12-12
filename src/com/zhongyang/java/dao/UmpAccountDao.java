package com.zhongyang.java.dao;

import com.zhongyang.java.pojo.FundAccount;
import com.zhongyang.java.pojo.UmpAccount;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月2日 下午5:25:05
* 类说明
*/
public interface UmpAccountDao {
	
	
	public UmpAccount byUserIdUmpAccount(UmpAccount umpAccount);
	
	public FundAccount byUserIdAccount(Object umpAccount);
	
	public int addUmpAccount(UmpAccount umpAccount);
	
	/*
	 * 根据UmpAccount查询联动信息
	 */
	public UmpAccount selectInfoByUmpAccount(UmpAccount umpAccount);
	
}
