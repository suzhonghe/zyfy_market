package com.zhongyang.java.service;

import com.zhongyang.java.pojo.UmpAccount;

/**
 * @author 作者:zhaofq
 * @version 创建时间：2015年12月2日 下午5:23:29 类说明:UmpAccountService
 */
public interface UmpAccountService {

	/*
	 * 根据用户Id获取用户umpAccount信息
	 */
	public UmpAccount byUserIdUmpAccount(UmpAccount umpAccount);
	
	
	public int addUmpAccount(UmpAccount umpAccount);
	
	public UmpAccount queryInfoByUmpAccount(UmpAccount umpAccount);
	
}
