package com.zhongyang.java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zhongyang.java.vo.loan.LoanRepaymentVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年2月19日 上午11:18:07
* 类说明
*/
@Service
public interface LoanRepaymentService {
	/*
	 * 查询所有标的信息
	 */
	public List<LoanRepaymentVo> getLoanRepayment(LoanRepaymentVo loanRepaymentVo) throws Exception;

	public List<LoanRepaymentVo> getLoanRepaymentAndStatusVo(LoanRepaymentVo loanRepaymentVos)throws Exception;
/*
	public List<LoanRepaymentVo> loanRepaymentPlan(LoanVo loanVoc)throws Exception;

	public List<LoanRepaymentVo> loanRepaymentPlanStatus(LoanVo loanVoc)throws Exception;*/
	
}
