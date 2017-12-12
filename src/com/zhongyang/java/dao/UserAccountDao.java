package com.zhongyang.java.dao;
import java.util.List;


import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.useraccount.FundRecordAccount;
import com.zhongyang.java.vo.useraccount.UserInvestLoanVo;
import com.zhongyang.java.vo.useraccount.UserInvestMoneyVo;
import com.zhongyang.java.vo.useraccount.UserInvestRecordVo;

public interface UserAccountDao {

	public List<UserInvestMoneyVo> selectUserAccountInfo(String userid);
	
	public List<UserInvestRecordVo> selectUserInvestInfo(Page<UserInvestRecordVo> page);
	
	public List<UserInvestLoanVo> selectUserLoanInvestInfo(Page<UserInvestLoanVo> page);
	
	public List<FundRecordAccount> selectUserFundRecord(Page<FundRecordAccount> page) throws Exception;
}
