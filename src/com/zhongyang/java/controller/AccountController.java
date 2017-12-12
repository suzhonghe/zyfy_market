package com.zhongyang.java.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.FundAccountBiz;
import com.zhongyang.java.biz.UserFundBiz;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.vo.BankInfoVo;
import com.zhongyang.java.vo.ParamsPojo;
import com.zhongyang.java.vo.UserFundVo;
import com.zhongyang.java.vo.loan.LoanRepaymentVo;
import com.zhongyang.java.vo.loan.LoanVo;

/**
 * @author 作者:zhaofq
 * @version 创建时间：2015年12月1日 下午4:17:46 类说明:用户账户信息
 */
@Controller
public class AccountController extends BaseController {

	@Autowired
	private UserFundBiz userFundBiz;
	
	@Autowired
	private FundAccountBiz fundAccountBiz;
	/*
	 * @return Obiect 通过userId获得UserFund跳转到充值页面--pc
	 */
	@RequestMapping(value = "/userFundRecharge")
	public @ResponseBody UserFundVo userFundRecharge(HttpServletRequest request) {
		User userI = (User) request.getSession().getAttribute("zycfLoginUser");
		UserFundVo userFundVo = new UserFundVo();
			try {
				if (userI != null) {
					userFundVo.setUserid(userI.getId());
				    userFundVo =  userFundBiz.byUserId(userFundVo);
				}
				return userFundVo;
			} catch (Exception e) {
				throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
			}
		
	}
	
	/*
	 * @return Obiect 通过userId获得UserFund跳转到充值页面--app
	 */
	@RequestMapping(value = "/mobileUserFundRecharge")
	public @ResponseBody UserFundVo mobileUserFundRecharge(HttpServletRequest request) {
		User userI = (User) request.getSession().getAttribute("zycfLoginUser");
		UserFundVo userFundVo = new UserFundVo();
			try {
				if (userI != null) {
					userFundVo.setUserid(userI.getId());
				    userFundVo =  userFundBiz.mobileUserFundRecharge(userFundVo);
				}
				return userFundVo;
			} catch (Exception e) {
				throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
			}
		
	}
	

	/*
	 * @return Obiect 通过userId获得UserFund跳转到提现页面:app和pc
	 */
	@RequestMapping(value = "/userFundWithdraw")
	public @ResponseBody UserFundVo ByUserIdWithdraw(HttpServletRequest request) {
		User userI = (User) request.getSession().getAttribute("zycfLoginUser");
		try {
			UserFundVo userFundVo = new UserFundVo();
			if (userI != null) {
				userFundVo.setUserid(userI.getId());
				return userFundBiz.byUserIdWithdraw(userFundVo);
				}else{
					throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
				}
		} catch (Exception e) {
			throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
		}
		
	}

	/*
	 * @return Obiect 通过userId查询用户借款记录
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/loanManagement")
	public @ResponseBody List<LoanVo> loanManagement(HttpServletRequest request,LoanVo loanVo) {		
		User userI = (User) request.getSession().getAttribute("zycfLoginUser");
		try {
			if (userI != null) {
				loanVo.setLoanUserId(userI.getId());
				return fundAccountBiz.byUserIdLoanVo(loanVo);
				}else{
					throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
				}
		} catch (Exception e) {
			throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
		}
	}
	/*
	 * @return Obiect 通过userId查询用户还款计划记录
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/loanRepaymentPlan")
	public @ResponseBody List<LoanRepaymentVo> loanRepaymentPlan(HttpServletRequest request,LoanVo loanVo) {		
		User userI = (User) request.getSession().getAttribute("zycfLoginUser");
		try {
			if (userI != null) {
				loanVo.setLoanUserId(userI.getId());
				return fundAccountBiz.loanRepaymentPlan(loanVo);
				}else{
					throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
				}
			
		} catch (Exception e) {
			throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
		}
	}
	
	/*
	 * 查询移动移动端用户绑卡银行列表信息
	 */
	@RequestMapping(value = "/getUserBankInfo")
	public @ResponseBody List<BankInfoVo> getUserBankInfo(HttpServletRequest request) {		
			return fundAccountBiz.getUserBankInfo();
	}
	
	@RequestMapping(value = "/queryBankList")
	public @ResponseBody List<BankInfoVo> queryBankList(@RequestBody ParamsPojo params) {		
			return fundAccountBiz.queryBankList(params.getNumber());
	}
	
}
