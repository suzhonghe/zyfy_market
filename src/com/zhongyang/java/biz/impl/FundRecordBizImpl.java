package com.zhongyang.java.biz.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.umpay.api.exception.ReqDataException;
import com.zhongyang.java.biz.FundAccountBiz;
import com.zhongyang.java.biz.FundRecordBiz;
import com.zhongyang.java.dao.UserFundDao;
import com.zhongyang.java.pojo.FundAccount;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.service.FundRecordService;
import com.zhongyang.java.service.UmpAccountService;
import com.zhongyang.java.service.UserFundService;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.uitl.BigDecimalAlgorithm;
import com.zhongyang.java.system.umpay.UmpaySignature;
import com.zhongyang.java.vo.app.OperatingFundsVo;
import com.zhongyang.java.vo.fund.FundRecordCalenderVo;
import com.zhongyang.java.vo.fund.FundRecordOperation;
import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.fund.FundRecordType;
import com.zhongyang.java.vo.fund.OrderTime;
import com.zhongyang.java.vo.fund.PayType;
import com.zhongyang.java.vo.fund.UmpRechargeVo;
import com.zhongyang.java.vo.fund.UmpWithdrawVo;

/**
 * @author 作者:zhaofq
 * @version 创建时间：2015年12月4日 下午1:00:52 类说明
 */
@Service
public class FundRecordBizImpl implements FundRecordBiz {

	private static Logger logger=Logger.getLogger(InvestBizImpl.class);
	
	@Autowired
	UserFundDao userFundDao;

	@Autowired
	FundAccountBiz fundAccountBiz;

	@Autowired
	FundRecordService fundRecordService;

	@Autowired
	UmpAccountService umpAccountService;

	@Autowired
	UserFundService userFundService;

	@Override
	@Transactional
	public String addNewFundRecord(OperatingFundsVo operatingFundsVo){
	
		String rechargeResults = null;
		try {
			UmpAccount umpAccount = new UmpAccount();
			umpAccount.setUserId(operatingFundsVo.getUserId());
			UmpAccount locaUmpAccount = this.getUmpAccount(umpAccount);//查询用户ump账户
			FundAccount fundAccount = new FundAccount();
			fundAccount.setUserId(operatingFundsVo.getUserId());
			// 充值记录做操
			int i = fundRecordService.create(convertFundRecord(operatingFundsVo));
			if(i != 0){
			rechargeResults = this.rechargeUmpPay(operatingFundsVo,locaUmpAccount);
			}else{
				logger.info("添加用户资金记录失败，请重新发起充值:userId"+operatingFundsVo.getUserId());
				throw new UException(SystemEnum.NOREGISTERED_UMPACCOUNT, "添加用户资金记录失败，请重新发起充值");
			}
		} catch (Exception e) {
			logger.info(e,e.fillInStackTrace());
			e.printStackTrace();
		}
		
		return rechargeResults;
	}
	
	//保存用户资金记录
	private  FundRecord convertFundRecord(OperatingFundsVo operatingFundsVo) {
		FundRecord fundRecord = new FundRecord();
				String userId = operatingFundsVo.getUserId();
				fundRecord.setAmount(new BigDecimal(operatingFundsVo.getOperatingLimit()));
				fundRecord.setOrderid(operatingFundsVo.getOrderId());
				fundRecord.setStatus(FundRecordStatus.PROCESSING);
				fundRecord.setOperation(FundRecordOperation.IN);;
				fundRecord.setTimerecorded(new Date());
				fundRecord.setType(FundRecordType.DEPOSIT);
				fundRecord.setDescription("个人用户充值");
				fundRecord.setUserId(operatingFundsVo.getUserId());
				fundRecord.setId(UUID.randomUUID().toString().toUpperCase());
		return fundRecord;
	}
	
	
	private UmpAccount getUmpAccount(UmpAccount umpAccount) {
		try {
			return umpAccountService.byUserIdUmpAccount(umpAccount);
		} catch (Exception e) {
			logger.info("查询用户账户失败userId: "+umpAccount.getUserId());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.NOREGISTERED_UMPACCOUNT, "查询用户账户失败");
		}
		
	}
	//调用第三方充值接口
	private String rechargeUmpPay(OperatingFundsVo operatingFundsVo,UmpAccount locaUmpAccount) {
		String rechargeResults = null;
		
		try {
			OrderTime ot = new OrderTime();
			BigDecimal dj = new BigDecimal(100.00);
			BigDecimal bAmount  = new BigDecimal(operatingFundsVo.getOperatingLimit());
			BigDecimal amount = bAmount.multiply(dj).setScale(0);
			Map map = new HashMap<String, String>();
			Map<String, Object> sysMap = SystemPro.getProperties();
		    String zycfurlIp = (String) sysMap.get("ZYCFMARKET_IP");
			
			map.put("notify_url",zycfurlIp+"/umpCallBack");
			map.put("order_id", operatingFundsVo.getOrderId());
			map.put("mer_date", ot.getTmeo());
			map.put("user_id", locaUmpAccount.getAccountName());
			map.put("account_id", locaUmpAccount.getAccountId());
			map.put("user_id", locaUmpAccount.getAccountName());
			map.put("account_id", locaUmpAccount.getAccountId());
			map.put("user_id", locaUmpAccount.getAccountName());
			map.put("account_id", locaUmpAccount.getAccountId());
			map.put("com_amt_type", "2");
			map.put("amount", amount);
			if(null != operatingFundsVo.getSourceV() && null==operatingFundsVo.getWap()){
				map.put("sourceV", "HTML5");
				map.put("pay_type", PayType.DEBITCARD);
				map.put("ret_url", "app:umpRecharge");
			
			}else if(null != operatingFundsVo.getSourceV() && "wap".equals(operatingFundsVo.getWap())){
				map.put("sourceV", "HTML5");
				map.put("pay_type", PayType.DEBITCARD);
				map.put("ret_url", "http://www.zuoyoufy.com/zycfMarket/wap/html/account/withdrawalsSuc.html");
			}else{
				map.put("pay_type", PayType.B2CDEBITBANK);
				map.put("gate_id", operatingFundsVo.getBankCode());
				map.put("ret_url", zycfurlIp+"/html/umpreturnMsg/umpReturnMsg.html");
			}
			UmpaySignature umpaySignature = new UmpaySignature("mer_recharge_person", map);
			logger.info("充值访问map参数："+map);
			rechargeResults = umpaySignature.getSignatureStrng();
		} catch (ReqDataException e) {
			logger.info("充值访问map参数："+rechargeResults);
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return rechargeResults;
	}
	
	

	/*
	 * 根据orderId更新用户资金记录状体
	 */
	@Transactional
	public String updateFundRecordByOrderId(UmpRechargeVo umpRechargeVo){
		String rechargeResults = null;
		Map map = new HashMap<String, String>();
		try {
			if (umpRechargeVo != null) {
				map.put("order_id", umpRechargeVo.getOrder_id());
				map.put("mer_date", umpRechargeVo.getMer_date());
				map.put("ret_code", umpRechargeVo.getRet_code());
				UmpaySignature umpaySignature = new UmpaySignature(umpRechargeVo.getService(), map);
				rechargeResults = umpaySignature.callBackSignature();
				FundRecord fundRecord = new FundRecord();
				fundRecord.setOrderid(umpRechargeVo.getOrder_id());
				// 交易成功
				FundRecord fundRecordOb = fundRecordService.findFundRecordByOrderId(fundRecord);
				UserFund userFund = new UserFund();
				OrderTime lastTime = new OrderTime();
				if (fundRecordOb.getStatus().equals(FundRecordStatus.SUCCESSFUL)) {
					return null;
				} else {
					if (fundRecordOb.getStatus().equals(FundRecordStatus.PROCESSING)
							&& umpRechargeVo.getRet_code().endsWith("0000")) {
						userFund.setUserId(fundRecordOb.getUserId());
						userFund = this.getUserFund(userFund);
						BigDecimalAlgorithm algorithm = new BigDecimalAlgorithm();
						fundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
						fundRecord.setAviAmount(algorithm.add(fundRecordOb.getAmount(), userFund.getAvailableAmount()));//fundFecord实时交易额度
						fundRecordService.updateFundRecordByOrderId(fundRecord);
						userFund.setUserId(fundRecordOb.getUserId());
						
						userFund = this.getUserFund(userFund);
						userFund.setTimeLastUpdated(lastTime.getLastUpdateTime());
						BigDecimal bigDecimalAccount = fundRecordOb.getAmount();
						userFund.setDepositAmount(algorithm.add(bigDecimalAccount, userFund.getDepositAmount()));// 充值金额
						userFund.setAvailableAmount(algorithm.add(bigDecimalAccount, userFund.getAvailableAmount()));// 可用金额=充值金额+数据库中的金额
						int upuserFund = userFundService.updateUserFundByUserID(userFund);
						
						//赠送红包时使用
//						if(upuserFund>0){
//							HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//									.getRequest();
//							request.getSession().setAttribute("record", fundRecordOb);
//						}
						
					} else if (umpRechargeVo.getRet_code().endsWith("00240000")) {
						logger.info("未知错误：00240000");
					} else {
						if("FAILED".endsWith(fundRecordOb.getStatus().toString())){
							return null;
						}else{
							fundRecord.setStatus(FundRecordStatus.FAILED);
							fundRecord.setOrderid(umpRechargeVo.getOrder_id());
							fundRecordService.updateFundRecordByOrderId(fundRecord);
						}

					}
					
				}

			}
		} catch (Exception e) {
			logger.info("我靠又特么充值失败了orderId" + umpRechargeVo.getOrder_id() +"--"+e);
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return rechargeResults;

	}

	private UserFund getUserFund(UserFund userFund) {
		try {
			return userFundService.byUserID(userFund);
		} catch (Exception e) {
			logger.info("查询用户账户失败" + userFund.getUserId());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.NOREGISTERED_UMPACCOUNT, "查询用户账户失败");
		}
		
	}
	/*
	 * 更新用户提现资金记录
	 */
	public String updateWithdrawByOrderId(UmpWithdrawVo umpWithdrawVo){
		String withdrawResults = null;
		Map map = new HashMap<String, String>();
		try {
			map.put("order_id", umpWithdrawVo.getOrder_id());
			map.put("mer_date", umpWithdrawVo.getMer_date());
			map.put("ret_code", umpWithdrawVo.getRet_code());
			UmpaySignature umpaySignature = new UmpaySignature(umpWithdrawVo.getService(), map);
				           withdrawResults = umpaySignature.callBackSignature();
		if (umpWithdrawVo != null) {
			FundRecord fundRecord = new FundRecord();
			fundRecord.setOrderid(umpWithdrawVo.getOrder_id());
			// 交易成功
			List<FundRecord> fundRecordOb = this.fundRecord(fundRecord);
			FundRecord fundRecordFee = new FundRecord();
			BigDecimal bigDecimalAccount = null;
			for (int i = 0; i < fundRecordOb.size(); i++) {
				String type = fundRecordOb.get(i).getType().name();
				if("WITHDRAW".equals(type)){
					bigDecimalAccount = fundRecordOb.get(i).getAmount();
				}
			}
			UserFund userFund = new UserFund();
			userFund.setUserId(fundRecordOb.get(0).getUserId());
			userFund = this.getUserFund(userFund);
			OrderTime lastTime = new OrderTime();
			BigDecimalAlgorithm algorithm = new BigDecimalAlgorithm();
			
			if (umpWithdrawVo.getRet_code().endsWith("0000")) {
				switch (fundRecordOb.get(0).getStatus()) {
				case PROCESSING:
					fundRecord.setStatus(FundRecordStatus.AUDITING);
					for (int i = 0; i < fundRecordOb.size(); i++) {
						
						fundRecordService.updateFundRecordByOrderId(fundRecord);
					}
					UserFund prepareUserFund = this.preparewithdrawUserFund(userFund, umpWithdrawVo,bigDecimalAccount);
					int upuserFundr = userFundService.updateUserFundByUserID(prepareUserFund);
					break;
				case AUDITING:
					fundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
					for (int i = 0; i < fundRecordOb.size(); i++) {
						fundRecord.setAmount(algorithm.sub(userFund.getAvailableAmount(), bigDecimalAccount));
						fundRecordService.updateFundRecordByOrderId(fundRecord);
					}
					userFund.setTimeLastUpdated(lastTime.getLastUpdateTime());
					userFund.setAvailableAmount(userFund.getAvailableAmount());//可用金额=冻结总金额-本次提现金额
					userFund.setWithdrawAmount(algorithm.add(bigDecimalAccount, userFund.getWithdrawAmount()));// 提现金额
					userFund.setFrozenAmount(algorithm.sub(bigDecimalAccount, userFund.getWithdrawAmount()));// 冻结金额=总冻结金额-本次解冻的额度(提现金额)
					int upuserFundrt = userFundService.updateUserFundByUserID(userFund);
					break;
				case SUCCESSFUL:
					break;
				}
			} else if (umpWithdrawVo.getRet_code().endsWith("00240000")) {
				System.out.println("为止错误：00240000");
			} else {
				if("FAILED".endsWith(fundRecordOb.get(0).getStatus().toString())){
					return null;
				}else{
					fundRecord.setStatus(FundRecordStatus.FAILED);
					fundRecord.setOrderid(umpWithdrawVo.getOrder_id());
					for (int i = 0; i < fundRecordOb.size(); i++) {
						fundRecordService.updateFundRecordByOrderId(fundRecord);
					}
					userFund.setFrozenAmount(algorithm.sub(bigDecimalAccount, algorithm.sToBd(umpWithdrawVo.getAmount())));// 冻结金额=当前冻结金额-本次提现金额
					userFund.setAvailableAmount(algorithm.add(bigDecimalAccount, userFund.getAvailableAmount()));// 可用金额=提现金额+数据库中可用金额
					int upuserFund = userFundService.updateUserFundByUserID(userFund);
					System.out.println("我靠又特么提现失败了------" + fundRecord.getOrderid());
				}
				
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return withdrawResults;
	}
	private List<FundRecord> fundRecord(FundRecord fundRecord) {
		try {
			return fundRecordService.findFundRecordListByOrderId(fundRecord);
		} catch (Exception e) {
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询用户资金记录失败");
		}
		
	}
	
	//提现UserFund(本地用户资金总账)记录表更新第一次更新(第三方返回之前)
	private UserFund preparewithdrawUserFund(UserFund userFund, UmpWithdrawVo umpWithdrawVo, BigDecimal bigDecimalAccount) {
			BigDecimalAlgorithm bda = new BigDecimalAlgorithm();
			BigDecimal chu = new BigDecimal(100.00);
	        BigDecimal	availableAmount = bda.sub(userFund.getAvailableAmount(), bigDecimalAccount);
			OrderTime orderTime =new OrderTime();
			UserFund userFundObj =new UserFund();
			userFundObj.setAvailableAmount(availableAmount);//更改后的可用金额
			if(userFund.getFrozenAmount()!=null){
				BigDecimal	frozenAmount = bda.add(userFund.getFrozenAmount(), bigDecimalAccount);
				userFundObj.setFrozenAmount(frozenAmount);
			}else{
				userFundObj.setFrozenAmount(bda.sToBd(umpWithdrawVo.getAmount()).divide(chu));
			}
			userFundObj.setTimeLastUpdated(orderTime.getLastUpdateTime());
			userFundObj.setUserId(userFund.getUserId());
			return userFundObj;
		}

	
	
	
	
	//用户资金日历
	public List<FundRecordCalenderVo> userFundRecordCalendar(String newData, User user) {
		try {
			List<FundRecordCalenderVo> transList = new ArrayList<FundRecordCalenderVo>();
			//充值
			List<FundRecordCalenderVo> rechargeList = new ArrayList<FundRecordCalenderVo>();
			//回款
			List<FundRecordCalenderVo> repList = new  ArrayList<FundRecordCalenderVo>();
			String []  lastday = getMonthDays(newData);
			String []  lastday1 = getMonthDays1(newData);
			FundRecordCalenderVo fundRecordCalenderVo = new FundRecordCalenderVo();
			fundRecordCalenderVo.setStartTime(lastday[0]);
			fundRecordCalenderVo.setEndTime(lastday[1]);
			fundRecordCalenderVo.setUserId(user.getId());

			List<FundRecordCalenderVo> fundRecordCalenderVos = fundRecordService.userFundRecordCalendar(fundRecordCalenderVo);
			fundRecordCalenderVo.setStartTime(lastday1[0]);
			fundRecordCalenderVo.setEndTime(lastday1[1]);
			List<FundRecordCalenderVo> repmentfundRecordCalenderVos = fundRecordService.repmentfundRecordCalenderVos(fundRecordCalenderVo);//回款和还款
			SimpleDateFormat df= new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat dfs= new SimpleDateFormat("dd");
			if(fundRecordCalenderVos.size()>0){
				
				for(int i = 0;i<fundRecordCalenderVos.size();i++){
					FundRecordCalenderVo fdc = new FundRecordCalenderVo();
					if("DEPOSIT".endsWith(fundRecordCalenderVos.get(i).getType().toString())){
						fdc.setTimeRecordeds(df.format(fundRecordCalenderVos.get(i).getTimeRecorded()));//时间
						fdc.setAmount(fundRecordCalenderVos.get(i).getAmount());
						fdc.setDates(dfs.format(fundRecordCalenderVos.get(i).getTimeRecorded()));
						fdc.setType("充值");
						fdc.setStatuss(fundRecordCalenderVos.get(i).getStatus().getKey());
					}else if("INVEST".endsWith(fundRecordCalenderVos.get(i).getType().toString())){
						fdc.setTimeRecordeds(df.format(fundRecordCalenderVos.get(i).getTimeRecorded()));//时间
						fdc.setAmount(fundRecordCalenderVos.get(i).getAmount());
						fdc.setDates(dfs.format(fundRecordCalenderVos.get(i).getTimeRecorded()));
						fdc.setType("投资");
						fdc.setStatuss(fundRecordCalenderVos.get(i).getStatus().getKey());
					}else if("WITHDRAW".endsWith(fundRecordCalenderVos.get(i).getType().toString())){
						fdc.setTimeRecordeds(df.format(fundRecordCalenderVos.get(i).getTimeRecorded()));//时间
						fdc.setDates(dfs.format(fundRecordCalenderVos.get(i).getTimeRecorded()));
						fdc.setAmount(fundRecordCalenderVos.get(i).getAmount());
						fdc.setType("提现");
						fdc.setStatuss(fundRecordCalenderVos.get(i).getStatus().getKey());
					}else{
						fdc.setTimeRecordeds(df.format(fundRecordCalenderVos.get(i).getTimeRecorded()));//时间
						fdc.setDates(dfs.format(fundRecordCalenderVos.get(i).getTimeRecorded()));
						fdc.setAmount(fundRecordCalenderVos.get(i).getAmount());
						fdc.setType("放款");
						fdc.setStatuss(fundRecordCalenderVos.get(i).getStatus().getKey());
					}
					rechargeList.add(fdc);
				}
				transList.addAll(rechargeList);
			}
			if(repmentfundRecordCalenderVos.size()>0){
				for(int i = 0;i<repmentfundRecordCalenderVos.size();i++){
					FundRecordCalenderVo fdc = new FundRecordCalenderVo();
					fdc.setDudate(repmentfundRecordCalenderVos.get(i).getDudate());//时间
					fdc.setAmountInterest(repmentfundRecordCalenderVos.get(i).getAmountInterest());
					fdc.setAmountOutStanding(repmentfundRecordCalenderVos.get(i).getAmountOutStanding());
					String strdate = repmentfundRecordCalenderVos.get(i).getDudate();
					if(null !=strdate){
						strdate = strdate.substring(8,10);
						fdc.setDates(strdate);
					}
					fdc.setAmountPrincipal(repmentfundRecordCalenderVos.get(i).getAmountPrincipal());
					fdc.setType("回款");
					repList.add(fdc);
				}
				transList.addAll(repList);
			}
			return transList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, e.getMessage());
		}
		//回款,借款，
	
	}


	private static String[]  getMonthDays(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));//年
		calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(5, 7))-1);//月
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		String yaer = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = String.valueOf(maxDay);
		String [] days = new String[2];
		days[0] = yaer+"-"+month+"-01 00:00:00";//开始时间
		days[1] = yaer+"-"+month+"-"+day+" 23:59:59";//开始时间
		return (days);
		}
	private static String[]  getMonthDays1(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));//年
		calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(5, 7))-1);//月
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		String yaer = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = String.valueOf(maxDay);
		String [] days = new String[2];
		days[0] = yaer+"-"+month+"-01";//开始时间
		days[1] = yaer+"-"+month+"-"+day;//开始时间
		return (days);
		}
	
	
	public static void main(String[] args) {
		
		String date = "2016-08";
		String []  lastday = getMonthDays(date);
		}
}
