package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UmpAccountDao;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.service.UmpAccountService;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月2日 下午5:24:11
* 类说明
*/
@Service
public class UmpAccountServiceImpl implements UmpAccountService {
    
	
	@Autowired
	private UmpAccountDao UmpAccountDao;
    /*
     * (non-Javadoc)
     * @see com.zhongyang.java.service.UmpAccountService#byUserIdUmpAccount(java.lang.String)
     * 通过用户id获得用户UmpAccount信息
     */
	@Override
	public UmpAccount byUserIdUmpAccount(UmpAccount umpAccount) {
		return UmpAccountDao.byUserIdUmpAccount(umpAccount);
	}
	
	
	@Override
	public int addUmpAccount(UmpAccount umpAccount) {
		return UmpAccountDao.addUmpAccount(umpAccount);
	}


	@Override
	public UmpAccount queryInfoByUmpAccount(UmpAccount umpAccount) {
		return UmpAccountDao.selectInfoByUmpAccount(umpAccount);
	}
}
