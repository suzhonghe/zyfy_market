package com.zhongyang.java.biz.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.LoanBiz;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.BigDecimalAlgorithm;
import com.zhongyang.java.vo.LoanDetail;
import com.zhongyang.java.vo.PagerVO;

/**
 * 
 * @Title: LoanBizImpl.java
 * @Package com.zhongyang.java.biz.impl
 * @Description:标的业务处理实现类
 * @author 苏忠贺
 * @date 2015年12月4日 上午9:09:55
 * @version V1.0
 */
@Service
public class LoanBizImpl implements LoanBiz {

	private static Logger logger = Logger.getLogger(LoanBizImpl.class);

	@Autowired
	private LoanService loanService;

	/*
	 * @see com.zhongyang.java.biz.LoanBiz#queryLoanById(java.lang.String)
	 */
	@Override
	public LoanDetail queryLoanById(String loanId) {
		LoanDetail loanVo = new LoanDetail();
		try {
			if (loanId == null) {
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "参数接收异常");
			}
			Loan loan = loanService.queryLoanById(loanId);
			loanVo.setLoanId(loan.getId());
			loanVo.setAmount(loan.getAmount());
			loanVo.setMethod(loan.getMethod());
			loanVo.setMonths(loan.getMonths());
			if (loan.getBidAmount() != null && loan.getAmount() != null) {
				BigDecimal planing = BigDecimalAlgorithm.divScale(loan.getBidAmount(), loan.getAmount(), 2,
						RoundingMode.DOWN);
				loanVo.setPlaning(BigDecimalAlgorithm.mul(planing, new BigDecimal(100)));
				loanVo.setSuplusAmount(BigDecimalAlgorithm.sub(loan.getAmount(), loan.getBidAmount()));
			}
			loanVo.setLoanName(loan.getTitle());
			loanVo.setMinAmount(loan.getMinAmount());
			loanVo.setMaxAmount(loan.getMaxAmount());
			loanVo.setStepAmount(loan.getStepAmount());
			loanVo.setProductName(loan.getProductName());
			loanVo.setRate(loan.getRate());
			loanVo.setAddRate(loan.getAddRate());
			loanVo.setBidAmount(loan.getBidAmount());
			loanVo.setStatus(loan.getStatus());
			return loanVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e, e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public PagerVO<LoanDetail> queryLoanByProductId(PagerVO<LoanDetail> loanDetail) {
		try {
			PagerVO<LoanDetail> pagerVo = new PagerVO<LoanDetail>();
			if (loanDetail == null) {
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "参数接收出错");
			}
			Page<Loan> page = new Page<Loan>();
			if (loanDetail.getStart() != 0) {
				page.setPageNo(loanDetail.getStart());
			} else {
				page.setPageNo(1);
			}
			if (loanDetail.getLength() != 0) {
				page.setPageSize(loanDetail.getLength());
			} else {
				page.setPageSize(5);
			}
			Loan lo = new Loan();
			lo.setProductId(loanDetail.getValue());
			Integer count = loanService.queryTotalCount(lo);
			int totalPage;
			if (count % page.getPageSize() == 0) {
				totalPage = count / page.getPageSize();
			} else {
				totalPage = (count / page.getPageSize()) + 1;
			}
			pagerVo.setTotalPage(totalPage);
			if (loanDetail.getField() != null && loanDetail.getValue() != null) {
				String[] fileds = loanDetail.getField().split(",");
				String[] values = loanDetail.getValue().split(",");
				if ("".equals(loanDetail.getField())) {
					page.getParams().put("timeOpen", "TIMEOPEN");
				} else {
					if (!loanDetail.getField().contains("timeOpen")) {
						page.getParams().put("timeOpen", "TIMEOPEN");
					}
				}
				for (int i = 0; i < fileds.length; i++) {
					if ("".equals(values[i]))
						continue;
					page.getParams().put(fileds[i], values[i]);
				}
				if (loanDetail.getSort() != null) {
					page.getParams().put("sort", loanDetail.getSort());
				} else {
					page.getParams().put("sort", "desc");
				}
			}
			List<Loan> loans = loanService.queryLoanByLoan(page);
			pagerVo.setRecordsTotal(count);
			List<LoanDetail> loanDetails = new ArrayList<LoanDetail>();
			for (Loan loan : loans) {
				LoanDetail lv = new LoanDetail();
				lv.setLoanId(loan.getId());
				lv.setAmount(loan.getAmount());
				if (loan.getTitle().contains("】")) {
					lv.setLoanName(loan.getTitle().substring(loan.getTitle().lastIndexOf("】") + 1));
				} else {
					lv.setLoanName(loan.getTitle());
				}
				lv.setMethod(loan.getMethod());
				lv.setMonths(loan.getMonths());
				if (loan.getBidAmount() != null && loan.getAmount() != null) {
					BigDecimal planing = BigDecimalAlgorithm.divScale(loan.getBidAmount(), loan.getAmount(), 2,
							RoundingMode.DOWN);
					lv.setPlaning(BigDecimalAlgorithm.mul(planing, new BigDecimal(100)));
					lv.setSuplusAmount(BigDecimalAlgorithm.sub(loan.getAmount(), loan.getBidAmount()).setScale(2,
							BigDecimal.ROUND_DOWN));
				}
				Date date = new Date();
				if (loan.getTimeOpen() != null) {
					lv.setDivTime(loan.getTimeOpen().getTime() - date.getTime());
				}
				lv.setProductName(loan.getProductName());
				lv.setRate(loan.getRate());
				lv.setBidAmount(loan.getBidAmount());
				lv.setStatus(loan.getStatus());
				lv.setTimeOpen(loan.getTimeOpen());
				lv.setAddRate(loan.getAddRate());
				loanDetails.add(lv);
			}
			pagerVo.setData(loanDetails);
			return pagerVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e, e.fillInStackTrace());
		}
		return null;
	}

	@Override
	public PagerVO<LoanDetail> queryAllLoan(PagerVO<LoanDetail> loanDetail) {
		try {
			PagerVO<LoanDetail> pagerVo = new PagerVO<LoanDetail>();
			List<LoanDetail> loanDetails = new ArrayList<>();
			Page<Loan> page = new Page<Loan>();
			if (loanDetail == null) {
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "参数接收出错");
			}
			if (loanDetail.getStart() != 0) {
				page.setPageNo(loanDetail.getStart());
			} else {
				page.setPageNo(1);
			}
			if (loanDetail.getLength() != 0) {
				page.setPageSize(loanDetail.getLength());
			} else {
				page.setPageSize(5);
			}
			if (loanDetail.getField() != null && loanDetail.getValue() != null) {
				String[] fileds = loanDetail.getField().split(",");
				String[] values = loanDetail.getValue().split(",");
				if ("".equals(loanDetail.getValue())) {
					page.getParams().put("timeOpen", "TIMEOPEN");
				} else {
					if (!loanDetail.getField().contains("timeOpen")) {
						page.getParams().put("timeOpen", "TIMEOPEN");
					}
				}
				for (int i = 0; i < fileds.length; i++) {
					page.getParams().put(fileds[i], values[i]);
				}
				if (loanDetail.getSort() != null) {
					page.getParams().put("sort", loanDetail.getSort());
				} else {
					page.getParams().put("sort", "desc");
				}
			}

			Integer count = loanService.queryTotalCount(null);
			int totalPage;
			if (count % page.getPageSize() == 0) {
				totalPage = count / page.getPageSize();
			} else {
				totalPage = (count / page.getPageSize()) + 1;
			}
			pagerVo.setTotalPage(totalPage);
			pagerVo.setRecordsTotal(count);
			List<Loan> loans = loanService.queryAllLoan(page);
			for (Loan loan : loans) {
				LoanDetail lv = new LoanDetail();
				lv.setLoanId(loan.getId());
				lv.setAmount(loan.getAmount());
				if (loan.getTitle().contains("】")) {
					lv.setLoanName(loan.getTitle().substring(loan.getTitle().lastIndexOf("】")+1));
				}
				else{
					lv.setLoanName(loan.getTitle());
				}
				lv.setMethod(loan.getMethod());
				lv.setMonths(loan.getMonths());
				if (loan.getBidAmount() != null && loan.getAmount() != null) {
					BigDecimal planing = BigDecimalAlgorithm.divScale(loan.getBidAmount(), loan.getAmount(), 2,
							RoundingMode.DOWN);
					lv.setPlaning(BigDecimalAlgorithm.mul(planing, new BigDecimal(100)));
					lv.setSuplusAmount(BigDecimalAlgorithm.sub(loan.getAmount(), loan.getBidAmount()).setScale(2,
							BigDecimal.ROUND_DOWN));
				}
				Date date = new Date();
				if (loan.getTimeOpen() != null) {
					Long res = loan.getTimeOpen().getTime() - date.getTime();
					if (res < 0) {
						lv.setDivTime(0L);
					} else {
						lv.setDivTime(res);
					}

				}
				lv.setProductName(loan.getProductName());
				lv.setRate(loan.getRate());
				lv.setBidAmount(loan.getBidAmount());
				lv.setStatus(loan.getStatus());
				lv.setTimeOpen(loan.getTimeOpen());
				lv.setAddRate(loan.getAddRate());
				loanDetails.add(lv);
			}
			pagerVo.setData(loanDetails);
			return pagerVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e, e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未知错误");
		}
	}

}
