package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.FundAccountDao;
import com.zhongyang.java.pojo.FundAccount;
import com.zhongyang.java.service.FundAccountService;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月4日 下午12:30:17
* 类说明
*/
@Service
public class FundAccountServiceImpl implements FundAccountService{
    
	@Autowired
	FundAccountDao fundAccountDao;

	@Override
	public FundAccount getByUserAndAccount(FundAccount fundAccount) {	
		return fundAccountDao.getByUserAndAccount(fundAccount);
	}

	@Override
	public FundAccount getFundAccountById(FundAccount fundAccount) {
		return fundAccountDao.getFundAccountById(fundAccount);
	}

	@Override
	public int addFucndAccount(FundAccount fundAccount) {
		return fundAccountDao.addFunAccount(fundAccount);
	}

	@Override
	public int modifyFunAccount(FundAccount fundAccount) {
		return fundAccountDao.updateFunAccount(fundAccount);
	}

	@Override
	public FundAccount queryFundAccountByParams(FundAccount fundAccount) {
		return fundAccountDao.selectFundAccountByParams(fundAccount);
	}
}
