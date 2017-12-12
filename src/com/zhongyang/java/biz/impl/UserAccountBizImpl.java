package com.zhongyang.java.biz.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.UserAccountBiz;
import com.zhongyang.java.dao.InvestContractInfoMapper;
import com.zhongyang.java.dao.UserAccountDao;
import com.zhongyang.java.pojo.FreshAmount;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.service.FreshAmountService;
import com.zhongyang.java.service.UserAccountService;
import com.zhongyang.java.service.UserFundService;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.fund.FundRecordType;
import com.zhongyang.java.vo.loan.Method;
import com.zhongyang.java.vo.useraccount.FundRecordAccount;
import com.zhongyang.java.vo.useraccount.LoanRepayMentAccount;
import com.zhongyang.java.vo.useraccount.LoanStatusAccount;
import com.zhongyang.java.vo.useraccount.UserAccountVo;
import com.zhongyang.java.vo.useraccount.UserInvestLoanVo;
import com.zhongyang.java.vo.useraccount.UserInvestMoneyVo;
import com.zhongyang.java.vo.useraccount.UserInvestRecordVo;

@Service
public class UserAccountBizImpl implements UserAccountBiz {

	private static Logger logger = Logger.getLogger(UserAccountBizImpl.class);

	static {
		Map<String, Object> map = SystemPro.getProperties();
		ip = (String) map.get("ZYCFMARKET_IP");
		path = (String) map.get("CONTRACT_HOME");
		secondContractsPath = (String) map.get("SECOND_CONTRACT_HOME");
	}

	private static String ip;

	private static String path;

	private static String secondContractsPath;

	@Autowired
	UserAccountService userAccountService;

	@Autowired
	UserFundService userFundService;

	@Autowired
	UserAccountDao userAccountDao;

	@Autowired
	InvestContractInfoMapper investContractInfoMapper;

	@Autowired
	private FreshAmountService freshAmountService;

	@Override
	public List<UserInvestMoneyVo> getUserInvestInfo(String userid) {
		return userAccountService.getUserAccountInfo(userid);
	}

	@Override
	public UserAccountVo getUserAaccountInfo(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		if (user == null || user.getId() == null) {
			throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
		}
		UserFund userFund = new UserFund();
		userFund.setUserId(user.getId());

		UserAccountVo userAccountVo = new UserAccountVo();

		try {
			userFund = userFundService.byUserID(userFund);
			if (userFund != null) {
				userAccountVo.setAvailableAmount(userFund.getAvailableAmount());
				userAccountVo.setDueInAmount(userFund.getDueInAmount());
				userAccountVo.setFrozenAmount(userFund.getFrozenAmount());
				userAccountVo.setDepositAmount(userFund.getDepositAmount());
				userAccountVo.setWithdrawAmount(userFund.getWithdrawAmount());
			}
			userAccountVo.setFreshAmount(new BigDecimal(0));
			FreshAmount freshAmount = new FreshAmount();
			freshAmount.setUserId(user.getId());
			freshAmount.setStatus(0);
			freshAmount.setType(2);
			List<FreshAmount> results = freshAmountService.queryFreshAmountByParams(freshAmount);
			if (results != null && results.size() != 0) {
				for (FreshAmount fa : results) {
					userAccountVo.setFreshAmount(userAccountVo.getFreshAmount().add(fa.getAmount()));
				}
			}
		} catch (Exception e) {
			logger.info(e, e.fillInStackTrace());
		}

		List<UserInvestMoneyVo> userInvests = this.getUserInvestInfo(user.getId());

		for (UserInvestMoneyVo userInvest : userInvests) {
			if (userInvest.getStatus().equals("UNDUE") || "OVERDUE".equals(userInvest.getStatus())) {
				userAccountVo.setUndueAmountInterest(
						userInvest.getAmountInterest().add(userAccountVo.getUndueAmountInterest()));
				userAccountVo.setUndueAmountCapital(
						userInvest.getAmountCapital().add(userAccountVo.getUndueAmountCapital()));
			} else if (userInvest.getStatus().equals("REPAYED") || "PREPAY".equals(userInvest.getStatus().toString()))
				userAccountVo.setAllRevue(userInvest.getAmountInterest().add(userAccountVo.getAllRevue()));
		}
		userAccountVo.setAllCapital(userAccountVo.getAvailableAmount().add(userAccountVo.getFrozenAmount())
				.add(userAccountVo.getUndueAmountCapital()).add(userAccountVo.getUndueAmountInterest()));

		return userAccountVo;
	}

	@Override
	public Page<FundRecordAccount> getUserFundRecord(HttpServletRequest request, Page<FundRecordAccount> page) {
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		if (user == null || user.getId() == null)
			throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");

		if (page == null) {
			page = new Page<FundRecordAccount>();
			page.setPageNo(1);
			page.setPageSize(10);

		}

		try {

			page.getParams().put("userId", user.getId());
			List<FundRecordAccount> fundRecords = userAccountDao.selectUserFundRecord(page);
			if (fundRecords != null) {
				for (FundRecordAccount record : fundRecords) {

					record.setType(FundRecordType.valueOf(record.getType()).getKey());
					record.setStatus(FundRecordStatus.valueOf(record.getStatus()).getKey());
					record.setStrTimeRecorded(FormatUtils.millisDateFormat(record.getTimerecorded()));
				}
			}
			page.setResults(fundRecords);
			return page;

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e, e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未知错误");
		}
	}

	@Override
	public Page<UserInvestRecordVo> selectUserInvestInfo(HttpServletRequest request, Page<UserInvestRecordVo> page) {
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		if (user == null || user.getId() == null)
			throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
		page.getParams().put("userId", user.getId());
		List<UserInvestRecordVo> records = userAccountService.selectUserInvestInfo(page);
		for (UserInvestRecordVo record : records) {
			record.setRepayStatus(LoanRepayMentAccount.valueOf(record.getRepayStatus()).getKey());
			record.setLoanStatus(LoanStatusAccount.valueOf(record.getLoanStatus()).getKey());
			Date date = FormatUtils.dateFormat(record.getDueDate());
			record.setDueDate(FormatUtils.simpleFormat(date));
			record.setStrRepayDate(FormatUtils.simpleFormat(record.getRepayDate()));
			record.setInvestTime(record.getInvestTime().substring(0, 10));
		}
		page.setResults(records);
		return page;
	}

	@Override
	public Page<UserInvestLoanVo> getUserInvestLoanRecord(HttpServletRequest request, Page<UserInvestLoanVo> page) {
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		if (user == null || user.getId() == null)
			throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");

		page.getParams().put("userId", user.getId());
		List<UserInvestLoanVo> list = userAccountDao.selectUserLoanInvestInfo(page);
		if (list != null) {
			for (UserInvestLoanVo record : list) {

				BigDecimal temp = record.getAmount().multiply(record.getRate().add(new BigDecimal(record.getAddRate())))
						.multiply(new BigDecimal(record.getMonths()))
						.divide(new BigDecimal(100), 4, BigDecimal.ROUND_DOWN)
						.divide(new BigDecimal(12), 2, BigDecimal.ROUND_DOWN);

				record.setDueInterest(temp);
				record.setLoanStatus(LoanStatusAccount.valueOf(record.getLoanStatus()).getKey());
				record.setRepayMethod(Method.valueOf(record.getRepayMethod()).getKey());
				record.setInvestStatus(FundRecordStatus.valueOf(record.getInvestStatus()).getKey());

				record.setStrTimeSettled(FormatUtils.simpleFormat(record.getTimeSettled()));
				record.setInvestTime(record.getInvestTime().substring(0, 10));

			}
		}

		page.setResults(list);
		return page;
	}

	@Override
	public String getInvestContract(HttpServletRequest request, String investId) {
		String rootUrl = ip.substring(0, ip.lastIndexOf("/"));

		return rootUrl + investContractInfoMapper.selectByInvestId(investId).getPath();

	}

	@Override
	public ResponseEntity<byte[]> downLoadContract(String investId) {

		try {
			String contractPath = path + investId + ".pdf";
			String secondContrcatsPath = secondContractsPath + investId + ".pdf";
			File file = new File(contractPath);
			File secondFile = new File(secondContrcatsPath);
			HttpHeaders headers = new HttpHeaders();
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + investId + ".pdf");
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			if (file.exists()) {
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
			} else if (secondFile.exists()) {
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(secondFile), headers, HttpStatus.OK);
			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
