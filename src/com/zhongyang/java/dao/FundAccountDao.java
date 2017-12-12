package com.zhongyang.java.dao;

import com.zhongyang.java.pojo.FundAccount;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月4日 下午12:31:32
* 类说明
*/
public interface FundAccountDao {
	public FundAccount getByUserAndAccount(FundAccount fundAccount);

	public FundAccount getFundAccountById(FundAccount fundAccount);
	
    public int addFunAccount(FundAccount fundAccount); 
    
    public int updateFunAccount(FundAccount fundAccount);
    
    public FundAccount selectFundAccountByParams(FundAccount fundAccount);
}
