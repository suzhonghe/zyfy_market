package com.zhongyang.java.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.useraccount.FundRecordAccount;
import com.zhongyang.java.vo.useraccount.UserAccountVo;
import com.zhongyang.java.vo.useraccount.UserInvestLoanVo;
import com.zhongyang.java.vo.useraccount.UserInvestMoneyVo;
import com.zhongyang.java.vo.useraccount.UserInvestRecordVo;

public interface UserAccountBiz {

	public List<UserInvestMoneyVo> getUserInvestInfo(String userid);
	
	public UserAccountVo getUserAaccountInfo(HttpServletRequest request);
	
	public Page<FundRecordAccount> getUserFundRecord(HttpServletRequest request, Page<FundRecordAccount> page) ;
	
	public Page<UserInvestRecordVo>  selectUserInvestInfo(HttpServletRequest request,Page<UserInvestRecordVo> page);
	
	public Page<UserInvestLoanVo> getUserInvestLoanRecord(HttpServletRequest request,Page<UserInvestLoanVo> page);
	
	public String getInvestContract(HttpServletRequest request,String investId);
	
	public ResponseEntity<byte[]> downLoadContract(String investId);    
}
