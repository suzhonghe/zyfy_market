package com.zhongyang.java.biz.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zhongyang.java.biz.FreshAmountBiz;
import com.zhongyang.java.pojo.FreshAmount;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.FreshAmountService;
import com.zhongyang.java.service.LoanService;
@Service
public class FreshAmountBizImpl implements FreshAmountBiz {
	
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private FreshAmountService freshAmountService;

	@Override
	public List<FreshAmount> queryFreshAmounts(String loanId) {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			User user=(User)request.getSession().getAttribute("zycfLoginUser");
			if(user==null){
				return null;
			}
			Loan loan = loanService.queryLoanById(loanId);
			FreshAmount freshAmount=new FreshAmount();
			freshAmount.setMonths(loan.getMonths());
			freshAmount.setStatus(0);
			freshAmount.setType(1);
			freshAmount.setUserId(user.getId());
			List<FreshAmount> results = freshAmountService.queryFreshAmountByParams(freshAmount);
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
