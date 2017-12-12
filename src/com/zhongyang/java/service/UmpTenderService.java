package com.zhongyang.java.service;

import com.zhongyang.java.pojo.UmpTender;

/**
 * 
* @Title: Loan.java 
* @Package com.zhongyang.java.service 
* @Description:联动业务接口 
* @author 苏忠贺   
* @date 2015年12月3日 下午5:22:23 
* @version V1.0
 */
public interface UmpTenderService {
	/*
	 *添加一条UmpTender记录
	 */
	public void addOneUmpTender(UmpTender umpTender)throws Exception;
	/*
	 * 根据标的Id查询umpTender
	 */
	public UmpTender queryUmpTenderByLoanId(String loanId)throws Exception;
	/**
	 * 
	* @Title: modifyUmpTender 
	* @Description: 修改账户金额 
	* @return void    返回类型 
	* @throws
	 */
	public void modifyUmpTender(UmpTender umpTender)throws Exception;
	
}
