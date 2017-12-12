package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.LoanRepaymentDao;
import com.zhongyang.java.pojo.LoanRepayment;
import com.zhongyang.java.service.LoanRepaymentService;
import com.zhongyang.java.vo.loan.LoanRepaymentVo;
import com.zhongyang.java.vo.loan.LoanVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年2月19日 上午11:19:54
* 类说明
*/
@Service
public class LoanRepaymentServiceImpl implements LoanRepaymentService {
	
	@Autowired
	LoanRepaymentDao loanRepaymentDao;
	
	@Override
	public List<LoanRepaymentVo> getLoanRepayment(LoanRepaymentVo loanRepaymentVo) throws Exception {
		return loanRepaymentDao.getLoanRepayment(loanRepaymentVo);
	}

	@Override
	public List<LoanRepaymentVo> getLoanRepaymentAndStatusVo(LoanRepaymentVo loanRepaymentVos) throws Exception {
		
		return loanRepaymentDao.getLoanRepaymentAndStatusVo(loanRepaymentVos);
	}

//	@Override
//	public List<LoanRepaymentVo> loanRepaymentPlan(LoanVo loanVoc) throws Exception {
//		return loanRepaymentDao.loanRepaymentPlan(loanVoc);
//	}
//
//	@Override
//	public List<LoanRepaymentVo> loanRepaymentPlanStatus(LoanVo loanVoc) throws Exception {
//		return loanRepaymentDao.loanRepaymentPlanStatus(loanVoc);
//	}

}
