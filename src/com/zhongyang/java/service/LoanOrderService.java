package com.zhongyang.java.service;

import com.zhongyang.java.pojo.LoanOrder;

/**
 * 
* @Title: Loan.java 
* @Package com.zhongyang.java.service 
* @Description:标的订单业务接口 
* @author 苏忠贺   
* @date 2015年12月3日 下午5:22:23 
* @version V1.0
 */
public interface LoanOrderService {
	
	/*
	 * 添加一条LoanOrder记录
	 */
	public void addloanOrder(LoanOrder loanOrder);
	
	/*
	 * 根据订单号查询订单信息
	 */
	public LoanOrder queryLoanOrderByOrderId(String orderId)throws Exception;
	
	public int modifyLoanOrder(LoanOrder order)throws Exception;
}
