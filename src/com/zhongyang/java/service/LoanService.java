package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.loan.LoanVo;

/**
 * 
* @Title: Loan.java 
* @Package com.zhongyang.java.service 
* @Description:标的业务接口 
* @author 苏忠贺   
* @date 2015年12月3日 下午5:22:23 
* @version V1.0
 */
public interface LoanService {
	
	/*
	 * 根据Id查询标的信息
	 */
	public Loan queryLoanById(String id)throws Exception;
	
	/*
	 * 根据项目Id查询标的信息
	 */
	public List<Loan> queryLoanByLoan(Page<Loan> page)throws Exception;
	
	/*
	 * 查询所有标的信息
	 */
	public List<Loan> queryAllLoan(Page<Loan> page)throws Exception;
	
	/*
	 * 修改标的信息
	 */
	public void modifyLoan(Loan loan)throws Exception;
	/**
	 * 
	* @Title: selectCount 
	* @Description:条件查询结果数量
	* @return Integer    返回类型 
	* @throws
	 */
	public int queryTotalCount(Loan loan)throws Exception;
    /*
     * 根据用户查询userId的借款记录
     */
	public List<Loan> byUserIdLoanVo(LoanVo loanVoc)throws Exception;
	 /*
     * 根据用户查询userId的借款记录
     */
	public List<Loan> byUserIdAndStatusLoanVo(LoanVo loanVoc) throws Exception;

}
