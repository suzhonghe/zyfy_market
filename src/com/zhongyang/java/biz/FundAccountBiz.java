package com.zhongyang.java.biz;

import java.util.List;

import com.zhongyang.java.pojo.FundAccount;
import com.zhongyang.java.vo.BankInfoVo;
import com.zhongyang.java.vo.UserFundVo;
import com.zhongyang.java.vo.loan.LoanRepaymentVo;
import com.zhongyang.java.vo.loan.LoanVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月4日 上午11:22:00
* 类说明
*/
public interface FundAccountBiz {
	public FundAccount getByUserAndAccount(FundAccount fundAccount) throws Exception;

	public FundAccount getFundAccountById(FundAccount fundAccount);
    /*
     * 根据用户查询userId的借款记录
     */
	public List<LoanVo> byUserIdLoanVo(LoanVo loanVo);
    /*
     * 根据userid查询用户借款还款计划
     */
	public List<LoanRepaymentVo> loanRepaymentPlan(LoanVo loanVo);
	/*
     * 根据查询移动移动端用户绑卡银行列表信息
     */
	public List<BankInfoVo> getUserBankInfo();
	
	public List<BankInfoVo> queryBankList(String number);

}
