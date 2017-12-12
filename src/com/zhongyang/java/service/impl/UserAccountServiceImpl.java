package com.zhongyang.java.service.impl;

import java.util.List;









import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.FundRecordDao;
import com.zhongyang.java.dao.UserAccountDao;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.service.UserAccountService;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.useraccount.UserInvestMoneyVo;
import com.zhongyang.java.vo.useraccount.UserInvestRecordVo;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	@Autowired
	UserAccountDao userAccountDao;

	@Autowired
	FundRecordDao fundRecordDao;
	
	@Override
	public List<UserInvestMoneyVo> getUserAccountInfo(String userid) {
		// TODO Auto-generated method stub
		
		
		return userAccountDao.selectUserAccountInfo(userid);
	}

	@Override
	public List<FundRecord> getUserFundRecord(String userId)  throws Exception{
		// TODO Auto-generated method stub
		
		
	//		return fundRecordDao.selectUserFundRecord(userId);
		return null;
		
		
	}

	@Override
	public List<UserInvestRecordVo> selectUserInvestInfo(
			Page<UserInvestRecordVo> page) {
		// TODO Auto-generated method stub
		return userAccountDao.selectUserInvestInfo(page);
	}

}
