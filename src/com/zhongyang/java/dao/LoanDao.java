package com.zhongyang.java.dao;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.pojo.Test;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.loan.LoanRepaymentVo;
import com.zhongyang.java.vo.loan.LoanVo;

/**
 * 
* @Title: LoanDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 标的DAO 
* @author 苏忠贺   
* @date 2015年12月3日 下午3:46:27 
* @version V1.0
 */
public interface LoanDao {
	
	
	/*
	 * 根据Id查询标的信息
	 */
	public Loan selectLoanById(Loan loan)throws Exception;
	
	/*
	 * 根据标的参数查询标的信息
	 */
	public List<Loan> selectLoanByLoan(Page<Loan> page)throws Exception;
	/*
	 * 查询所有标的信息
	 */
	public List<Loan> selectAllLoan(Page<Loan> page)throws Exception;
	
	/*
	 * 修改标的信息
	 */
	public void updateLoan(Loan loan)throws Exception;
	/**
	 * 
	* @Title: slectTotalCount 
	* @Description:根据条件查询总条数
	* @return int    返回类型 
	* @throws
	 */
	public int slectTotalCount(Loan loan)throws Exception;
    /*
     *  根据用户查询userId的借款记录
     */
	public List<Loan> byUserIdLoanVo(LoanVo loanVo)throws Exception;

	public List<Loan> byUserIdAndStatusLoanVo(LoanVo loanVo) throws Exception;
	
}
