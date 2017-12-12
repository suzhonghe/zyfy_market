package com.zhongyang.java.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.UserAccountBiz;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.useraccount.FundRecordAccount;
import com.zhongyang.java.vo.useraccount.UserAccountVo;
import com.zhongyang.java.vo.useraccount.UserInvestLoanVo;
import com.zhongyang.java.vo.useraccount.UserInvestRecordVo;

@Controller

public class UserAccountController extends BaseController{
	
	@Autowired
	UserAccountBiz userAccountBiz;

	@RequestMapping(value = "/userAccountInfo", method = RequestMethod.POST)
	public @ResponseBody UserAccountVo getUserInvestInfo(
			HttpServletRequest request) {

		return userAccountBiz.getUserAaccountInfo(request);
	}

	@RequestMapping(value = "/userFundRecordList")
	public @ResponseBody Page<FundRecordAccount> getUserFundRecordList(
			HttpServletRequest request,Page<FundRecordAccount> page,String appType) {
		if(appType!=null){
			page.getParams().put("type",appType);
		}
		return userAccountBiz.getUserFundRecord(request, page);
	}

	@RequestMapping(value = "/userInvestRecordList")
	public @ResponseBody Page<UserInvestRecordVo>  getUserInvestRecordList(
			HttpServletRequest request, Page<UserInvestRecordVo> page,String appRepayStatus) {
		if(appRepayStatus!=null){
			page.getParams().put("repayStatus",appRepayStatus);
		}
		return userAccountBiz.selectUserInvestInfo(request,page);
	}

	@RequestMapping(value = "/userInvestLoanList")
	public @ResponseBody Page<UserInvestLoanVo>  getUserInvestLoanList(
			HttpServletRequest request,Page<UserInvestLoanVo> page,String appLoanStatus) {
		if(appLoanStatus!=null){
			page.getParams().put("loanStatus",appLoanStatus);
		}
		return userAccountBiz.getUserInvestLoanRecord(request,page);
	}
	
	@RequestMapping(value="/user/investContract")
	public @ResponseBody String getInvestContract(HttpServletRequest request,String investId){
		return userAccountBiz.getInvestContract(request, investId);
	}
	
	@RequestMapping(value="/user/downLoadContract")
	public ResponseEntity<byte[]> downLoadContract(String investId){    
		return userAccountBiz.downLoadContract(investId);     
	}    

}
