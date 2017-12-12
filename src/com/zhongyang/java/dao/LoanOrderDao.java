package com.zhongyang.java.dao;

import com.zhongyang.java.pojo.LoanOrder;

/**
 * 
* @Title: LoanDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 标的订单DAO 
* @author 苏忠贺   
* @date 2015年12月3日 下午3:46:27 
* @version V1.0
 */
public interface LoanOrderDao {
	
	/*
	 * 添加一条LoanOrder记录
	 */
	public void insertLoanOrder(LoanOrder loanOrder);
	
	/*
	 * 根据订单号查询订单信息
	 */
	public LoanOrder selectLoanOrderByOrderId(String orderId)throws Exception;
	
	public int updateLoanOrder(LoanOrder order)throws Exception;
		
}
