package com.zhongyang.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.FreshAmountBiz;
import com.zhongyang.java.pojo.FreshAmount;

@Controller
public class FreshAmountController extends BaseController {
	
	@Autowired
	private FreshAmountBiz freshAmountBiz;
	
	@RequestMapping(value="/queryFreshAmounts")
	public @ResponseBody List<FreshAmount>queryFreshAmounts(String loanId){
		return freshAmountBiz.queryFreshAmounts(loanId);
	}

}
