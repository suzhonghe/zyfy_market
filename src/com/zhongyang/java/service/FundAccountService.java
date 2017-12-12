package com.zhongyang.java.service;

import com.zhongyang.java.pojo.FundAccount;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月4日 下午12:28:16
* 类说明
*/
public interface FundAccountService {

	public FundAccount getByUserAndAccount(FundAccount fundAccount) throws Exception;

	public FundAccount getFundAccountById(FundAccount fundAccount);
	
	public int addFucndAccount(FundAccount fundAccount);
	
	public int modifyFunAccount(FundAccount fundAccount);
	
	public FundAccount queryFundAccountByParams(FundAccount fundAccount);
}
