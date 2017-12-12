package com.zhongyang.java.biz;

import java.util.List;

import com.zhongyang.java.pojo.FreshAmount;

public interface FreshAmountBiz {

	public List<FreshAmount>queryFreshAmounts(String loanId);
}
