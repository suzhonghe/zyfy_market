package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.LoanRepayment;
import com.zhongyang.java.vo.loan.LoanRepaymentVo;
import com.zhongyang.java.vo.loan.LoanVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年2月19日 上午11:24:38
* 类说明
*/
public interface LoanRepaymentDao {
	
	public List<LoanRepaymentVo> getLoanRepayment(LoanRepaymentVo loanRepaymentVo) throws Exception;


	public List<LoanRepaymentVo> getLoanRepaymentAndStatusVo(LoanRepaymentVo loanRepaymentVos) throws Exception;
//
//	public List<LoanRepaymentVo> loanRepaymentPlan(LoanVo loanVoc) throws Exception;
//
//	public List<LoanRepaymentVo> loanRepaymentPlanStatus(LoanVo loanVoc) throws Exception;

}
