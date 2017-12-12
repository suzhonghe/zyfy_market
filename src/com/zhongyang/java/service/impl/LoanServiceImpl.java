package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.LoanDao;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.loan.LoanVo;
/**
 * 
* @Title: LoanServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:标的业务实现类
* @author 苏忠贺   
* @date 2015年12月4日 上午9:05:01 
* @version V1.0
 */
@Service
public class LoanServiceImpl implements LoanService{
	
	@Autowired
	private LoanDao loanDao;
	
	/*
	 * @see com.zhongyang.java.service.LoanService#queryLoanById(java.lang.String)
	 */
	@Override
	public Loan queryLoanById(String id) throws Exception {
		Loan loan=new Loan();
		loan.setId(id);
		return loanDao.selectLoanById(loan);
	}
	
	/*
	 * @see com.zhongyang.java.service.LoanService#queryLoanByProjectId(java.lang.String)
	 */
	@Override
	public List<Loan> queryLoanByLoan(Page<Loan> page) throws Exception {

		return loanDao.selectLoanByLoan(page);
	}

	@Override
	public List<Loan> queryAllLoan(Page<Loan> page) throws Exception {
		return loanDao.selectAllLoan(page);
	}

	@Override
	public void modifyLoan(Loan loan) throws Exception {
		loanDao.updateLoan(loan);
	}

	@Override
	public int queryTotalCount(Loan loan) throws Exception {
		return loanDao.slectTotalCount(loan);
	}

	@Override
	public List<Loan> byUserIdLoanVo(LoanVo loanVo) throws Exception {
		return loanDao.byUserIdLoanVo(loanVo);
	}

	@Override
	public List<Loan> byUserIdAndStatusLoanVo(LoanVo loanVo) throws Exception {
		return loanDao.byUserIdAndStatusLoanVo(loanVo) ;
	}


}
