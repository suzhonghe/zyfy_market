package com.zhongyang.java.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.FundRecordBiz;
import com.zhongyang.java.biz.UmpAccountBiz;
import com.zhongyang.java.biz.UserFundBiz;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.config.ApplicationBean;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.system.umpay.UmpaySignature;
import com.zhongyang.java.vo.ParamsPojo;
import com.zhongyang.java.vo.app.OperatingFundsVo;
import com.zhongyang.java.vo.fund.UmpRechargeVo;
import com.zhongyang.java.vo.fund.UmpWithdrawVo;

/**
 * @author 作者:zhaofq
 * @version 创建时间：2015年12月1日 下午2:50:22 类说明:平台用户与联动账户相关操作类
 */
@Controller
public class UmpController extends BaseController {

	@Autowired
	private UmpAccountBiz umpAccountBiz;

	@Autowired
	FundRecordBiz fundRecordBiz;
	
	@Autowired
	UserFundBiz userFundBiz;

	/*
	 * 个人用户充值请求
	 */
	@RequestMapping(value = "/umpRecharge")
	public @ResponseBody Map<String, String> umpToRecharge(HttpServletRequest request,OperatingFundsVo operatingFundsVo) {
		User userI = (User) request.getSession().getAttribute("zycfLoginUser");
		String prepareUmp = null;
		Map<String, String> map = new HashMap<String,String>();
		if (userI != null) {
			operatingFundsVo.setUserId(userI.getId());
			prepareUmp = umpAccountBiz.byUserIdUmpAccount(operatingFundsVo);
			map.put("prepareUmp", prepareUmp);
			return map;
		}else{
			throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
		}
	}

	/*
	 * 回调修改修改用户资金记录状态表(TB_FUND_RECORD)和用户资金表(TB_USER_FUND);
	 */
	@RequestMapping(value = "/umpCallBack")
	public @ResponseBody String umpSavere(Model model, HttpServletRequest request, UmpRechargeVo umpRechargeVo) {
			return fundRecordBiz.updateFundRecordByOrderId(umpRechargeVo);
	}

	/*
	 * 个人用户提现请求
	 */
	@RequestMapping(value = "/umpWithdraw")
	public @ResponseBody Map<String, String> umpToWithdraw(HttpServletRequest request, @RequestBody OperatingFundsVo operatingFundsVo) {
		User userI = (User) request.getSession().getAttribute("zycfLoginUser");
		Map<String, String> map = new HashMap<String,String>();
		if (userI != null) {
			operatingFundsVo.setUserId(userI.getId());
			String prepareUmp = umpAccountBiz.byUserIdAccount(operatingFundsVo);
			map.put("prepareUmp", prepareUmp);
			return map;
			}else{
				throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
			}

	}

	/*
	 * 回调修改修改用户资金记录状态表(TB_FUND_RECORD)和用户资金表(TB_USER_FUND);
	 */
	@RequestMapping(value = "/umpCallBackWithdraw")
	public @ResponseBody String umpSavereWithdraw(Model model, HttpServletRequest request,
			UmpWithdrawVo umpWithdrawVo) {
			return fundRecordBiz.updateWithdrawByOrderId(umpWithdrawVo);
	}
	/*
	 * 个人用户转账给平台--跳转到转账页面
	 */
	@RequestMapping(value = "/userToBusinessPage")
	public @ResponseBody UserFund userToBusiness(HttpServletRequest request) {
		User userI = (User) request.getSession().getAttribute("zycfLoginUser");
		UserFund userfund  = new UserFund();
		if (userI != null) {
			return userfund = userFundBiz.byUserFundId(userI.getId());
		}else{
			throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
		}
	}
	
	/*
	 * 个人用户转账给平台--发起转账请求
	 */
	@RequestMapping(value = "/userToBusiness")
	public @ResponseBody Map<String, String> userToBusiness(HttpServletRequest request, @RequestBody ParamsPojo params) {
		User userI = (User) request.getSession().getAttribute("zycfLoginUser");
		Map<String, String> map = new HashMap<String,String>();
		if (userI != null) {
			UmpAccount umpAccount = new UmpAccount();
			umpAccount.setUserId(userI.getId());
			String prepareUmp = umpAccountBiz.transferToBusiness(params.getTransferAmount(),userI.getId());
			map.put("prepareUmp", prepareUmp);
			return map;
		}else{
			throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
		}
	}
	
	/*
	 * 回调修改修改用户资金记录状态表(TB_FUND_RECORD)和用户资金表(TB_USER_FUND);
	 */
	@RequestMapping(value = "/userToBusinessCallBack")
	public @ResponseBody void userToBusinessCallBack(Model model, HttpServletRequest request, UmpRechargeVo umpRechargeVo) {
			umpAccountBiz.userToBusinessCallBack(umpRechargeVo);
	}
}
