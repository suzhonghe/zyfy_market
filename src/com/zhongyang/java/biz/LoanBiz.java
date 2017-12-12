package com.zhongyang.java.biz;

import com.zhongyang.java.vo.LoanDetail;
import com.zhongyang.java.vo.PagerVO;

/**
 * 
* @Title: LoanBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:标的业务处理接口 
* @author 苏忠贺   
* @date 2015年12月4日 上午9:08:03 
* @version V1.0
 */
public interface LoanBiz {
	/*
	 * 根据标的Id查询标的信息
	 */
	public LoanDetail queryLoanById(String loanId);
	
	/*
	 * 根据产品Id查询标的信息
	 */
	public PagerVO<LoanDetail> queryLoanByProductId(PagerVO<LoanDetail> loanDetail);
	
	/*
	 * 查询所有标的信息
	 */
	public PagerVO<LoanDetail> queryAllLoan(PagerVO<LoanDetail> loanDetail);
}
