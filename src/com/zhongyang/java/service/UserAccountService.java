package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.useraccount.UserInvestMoneyVo;
import com.zhongyang.java.vo.useraccount.UserInvestRecordVo;

public interface UserAccountService {

	public List<UserInvestMoneyVo> getUserAccountInfo(String userid);
	
	public List<FundRecord> getUserFundRecord(String userId) throws Exception;
	
	public List<UserInvestRecordVo> selectUserInvestInfo(Page<UserInvestRecordVo> page); 
	
}
