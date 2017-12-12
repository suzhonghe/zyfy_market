package com.zhongyang.java.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.LoanBiz;
import com.zhongyang.java.biz.VirtualLoanBiz;
import com.zhongyang.java.vo.LoanDetail;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.ReturnVirtualLoanVO;

/**
 * 
* @Title: LoanController.java 
* @Package com.zhongyang.java.controller 
* @Description:标的控制器 
* @author 苏忠贺   
* @date 2015年12月4日 上午9:58:10 
* @version V1.0
 */
@Controller
public class LoanController extends BaseController{

	@Autowired
	private LoanBiz loanBiz;
	
	@Autowired
	private VirtualLoanBiz virtualLoanBiz;
	/*
	 * 标的列表
	 */
	@RequestMapping(value="/investList")
	public @ResponseBody PagerVO<LoanDetail> investList(PagerVO<LoanDetail> loanDetail){
		if("".equals(loanDetail.getValue())||loanDetail.getValue()==null){
			return loanBiz.queryAllLoan(loanDetail);
		}
		//根据产品id查询标的列表
		return loanBiz.queryLoanByProductId(loanDetail);
	}
	/*
	 * 标的详情
	 */
	@RequestMapping(value="/loanDetail")
	public @ResponseBody LoanDetail loanDetail(String loanId){
		return loanBiz.queryLoanById(loanId);
	}
	
	//体验标详情
	@RequestMapping(value="/queryVirtualLoanOpened")
	public @ResponseBody ReturnVirtualLoanVO queryVirtualLoanOpened(){
		return virtualLoanBiz.queryVirtualLoanOpened();
	}
	
	//体验标详情
	@RequestMapping(value="/queryVirtualLoan")
	public @ResponseBody  Map<String, String>queryVirtualLoan(){
		return virtualLoanBiz.queryVirtualLoan();
	}
}
