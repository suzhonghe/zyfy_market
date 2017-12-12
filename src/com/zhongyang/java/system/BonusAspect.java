package com.zhongyang.java.system;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.umpay.api.exception.ReqDataException;
import com.umpay.api.exception.RetDataException;
import com.zhongyang.java.pojo.ClientFundRecord;
import com.zhongyang.java.pojo.ExperienceAmount;
import com.zhongyang.java.pojo.FreshAmount;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.service.ClientFundRecordService;
import com.zhongyang.java.service.ExperienceAmountService;
import com.zhongyang.java.service.FreshAmountService;
import com.zhongyang.java.service.FundRecordService;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.service.UmpAccountService;
import com.zhongyang.java.service.UserFundService;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.system.config.ApplicationBean;
import com.zhongyang.java.system.umpay.UmpaySignature;
import com.zhongyang.java.vo.fund.FundRecordOperation;
import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.fund.FundRecordType;

@SuppressWarnings("unchecked")
@Service
@Aspect
public class BonusAspect {

	static {
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCF_ADDUSER_AMOUNT = (String) sysMap.get("ZYCF_ADDUSER_AMOUNT");// 注册赠送体验金金额
		ZYCF_IDNUM_AMOUNT = (String) sysMap.get("ZYCF_IDNUM_AMOUNT");// 实名认证赠送体验金金额
		ZYCF_AMOUNT_LIMITDAYS = (String) sysMap.get("ZYCF_AMOUNT_LIMITDAYS");// 体验金有效天数
		ZYCF_UMP_ACCOUNT = (String) sysMap.get("ZYCF_UMP_ACCOUNT");
		ZYCFMARKET_IP = (String) sysMap.get("ZYCFMARKET_IP");
		ZYCF_ALL_LIMITAMOUNT = (Map<String, Object>) sysMap.get("ZYCF_ALL_LIMITAMOUNT");
		ZYCF_ONE_LIMITAMOUNT = (Map<String, Object>) sysMap.get("ZYCF_ONE_LIMITAMOUNT");
		ZYCF_THREE_LIMITAMOUNT = (Map<String, Object>) sysMap.get("ZYCF_THREE_LIMITAMOUNT");
		ZYCF_SIX_LIMITAMOUNT = (Map<String, Object>) sysMap.get("ZYCF_SIX_LIMITAMOUNT");
		ZYCF_TWELVE_LIMITAMOUNT = (Map<String, Object>) sysMap.get("ZYCF_TWELVE_LIMITAMOUNT");
		ZYCF_RECHARGE_LIMITAMOUNT = (Map<String, Object>) sysMap.get("ZYCF_RECHARGE_LIMITAMOUNT");
	}

	Logger logger = Logger.getLogger(BonusAspect.class.getName());

	private static String ZYCF_ADDUSER_AMOUNT;

	private static String ZYCF_IDNUM_AMOUNT;

	private static String ZYCF_AMOUNT_LIMITDAYS;

	private static String ZYCF_UMP_ACCOUNT;

	private static String ZYCFMARKET_IP;

	private static Map<String, Object> ZYCF_ALL_LIMITAMOUNT;

	private static Map<String, Object> ZYCF_ONE_LIMITAMOUNT;

	private static Map<String, Object> ZYCF_THREE_LIMITAMOUNT;

	private static Map<String, Object> ZYCF_SIX_LIMITAMOUNT;

	private static Map<String, Object> ZYCF_TWELVE_LIMITAMOUNT;

	private static Map<String, Object> ZYCF_RECHARGE_LIMITAMOUNT;

	@Autowired
	private ExperienceAmountService experienceAmountService;

	@Autowired
	private FreshAmountService freshAmountService;

	@Autowired
	private ClientFundRecordService clientFundRecordService;

	@Autowired
	private FundRecordService fundRecordService;

	@Autowired
	private UserFundService userFundService;

	@Autowired
	private UmpAccountService umpAccountService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoanService loanService;

	//@AfterReturning(value="execution(* com.zhongyang.java.biz.impl.UserBizImpl.addUser(..))")
	public void addUserAfter() {
		try{
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			User user = (User) request.getSession().getAttribute("zycfLoginUser");
			if (user == null)
				return;

			ExperienceAmount condition = new ExperienceAmount();
			condition.setUserId(user.getId());
			ExperienceAmount result = experienceAmountService.queryByParams(condition);
			if (result != null)
				return;

			ExperienceAmount experienceAmount = new ExperienceAmount();
			experienceAmount.setId(UUID.randomUUID().toString().toUpperCase());
			experienceAmount.setName("用户注册赠送体验金");
			experienceAmount.setAmount(new BigDecimal(ZYCF_ADDUSER_AMOUNT));
			Date date = new Date();
			experienceAmount.setCreateTime(date);
			experienceAmount.setLimitDays(Integer.parseInt(ZYCF_AMOUNT_LIMITDAYS));
			experienceAmount
					.setEndTime(new Date(date.getTime() + Integer.parseInt(ZYCF_AMOUNT_LIMITDAYS) * 24 * 3600 * 1000L));
			experienceAmount.setEnable(false);
			experienceAmount.setEnableUse(false);
			experienceAmount.setUserId(user.getId());
			int count = experienceAmountService.addExperienceAmount(experienceAmount);
			if(count>0){
				DESTextCipher cipher = DESTextCipher.getDesTextCipher();
				Map<String,Object> message=new HashMap<String,Object>();
				message.put("info", "恭喜您成功获得"+experienceAmount.getAmount().toString()+"元体验金,赶快去尝试投资新手体验标，赚取第一笔收益吧!体验金有限期为30天,请尽快使用!");
				message.put("mobiles",cipher.decrypt(user.getMobile()));
				ShortMessage.getShortMessage().sendToUserMsg(message);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		}
	}

	@AfterReturning("execution(* com.zhongyang.java.biz.impl.UserBizImpl.idCertificat(..))")
	public void idCertificatAfter() {
		try{
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			User user = (User) request.getSession().getAttribute("zycfLoginUser");
			if (user == null)
				return;
	
			ExperienceAmount condition = new ExperienceAmount();
			condition.setUserId(user.getId());
			ExperienceAmount result = experienceAmountService.queryByParams(condition);
			if (result != null)
				return;
	
			ExperienceAmount experienceAmount = new ExperienceAmount();
			experienceAmount.setId(UUID.randomUUID().toString().toUpperCase());
			experienceAmount.setName("用户实名认证赠送体验金");
			experienceAmount.setAmount(new BigDecimal(ZYCF_IDNUM_AMOUNT));
			Date date = new Date();
			experienceAmount.setCreateTime(date);
			experienceAmount.setLimitDays(Integer.parseInt(ZYCF_AMOUNT_LIMITDAYS));
			experienceAmount
					.setEndTime(new Date(date.getTime() + Integer.parseInt(ZYCF_AMOUNT_LIMITDAYS) * 24 * 3600 * 1000L));
			experienceAmount.setEnable(false);
			experienceAmount.setEnableUse(false);
			experienceAmount.setUserId(user.getId());
			int count=experienceAmountService.addExperienceAmount(experienceAmount);
			if(count>0){
				DESTextCipher cipher = DESTextCipher.getDesTextCipher();
				Map<String,Object> message=new HashMap<String,Object>();
				message.put("info", "恭喜您成功获得"+experienceAmount.getAmount().toString()+"元体验金,赶快去尝试投资新手体验标，赚取第一笔收益吧!体验金有限期为30天,请尽快使用!");
				message.put("mobiles",cipher.decrypt(user.getMobile()));
				ShortMessage.getShortMessage().sendToUserMsg(message);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		}
	}

	// 充值发放红包
	//活动开始时间2016-11-01
//	@AfterReturning("execution(* com.zhongyang.java.biz.impl.FundRecordBizImpl.updateFundRecordByOrderId(..))")
	public void umpRechargeAfter() {
		try{
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			FundRecord fundRecord = (FundRecord) request.getSession().getAttribute("record");
			if(fundRecord==null)return;
			
			//活动开始时间2016-11-01
			String date="2016-11-01";
			
 			List<FundRecord> results = fundRecordService.queryByConditions(fundRecord.getUserId(), date);
			if(results!=null&&results.size()>1){
				request.getSession().removeAttribute("record");
				return;
			}
			
			int days = 0;
			BigDecimal oneLimitAmount = null;
			BigDecimal oneAmount = null;

			BigDecimal threeLimitAmount = null;
			BigDecimal threeAmount = null;

			BigDecimal sixLimitAmount = null;
			BigDecimal sixAmount = null;

			BigDecimal twelveLimitAmount = null;
			BigDecimal twelveAmount = null;
			double com=-Double.MAX_VALUE;
			for (String key : ZYCF_RECHARGE_LIMITAMOUNT.keySet()) {
				String res = (String) ZYCF_RECHARGE_LIMITAMOUNT.get(key);
				String[] split = res.split(",");
				BigDecimal amount = new BigDecimal(split[0]);// 充值额度限制
				double value = amount.subtract(fundRecord.getAmount()).doubleValue();
				if (value <= 0&&value>com) {
					days = Integer.valueOf(split[1]);
					
					if(new BigDecimal(split[3]).doubleValue()!=0){
						oneLimitAmount = new BigDecimal(split[2]);// 月标红包使用限制额度
						oneAmount = new BigDecimal(split[3]);// 月标红包
					}
					
					if(new BigDecimal(split[5]).doubleValue()!=0){
						threeLimitAmount = new BigDecimal(split[4]);
						threeAmount = new BigDecimal(split[5]);
					}
					
					if(new BigDecimal(split[7]).doubleValue()!=0){
						sixLimitAmount = new BigDecimal(split[6]);
						sixAmount = new BigDecimal(split[7]);
					}
					
					if(new BigDecimal(split[9]).doubleValue()!=0){
						twelveLimitAmount = new BigDecimal(split[8]);
						twelveAmount = new BigDecimal(split[9]);
					}
					
					com=value;
				}
			}
			BigDecimal totalAmount=new BigDecimal(0);
			List<FreshAmount>freshAmounts=new ArrayList<FreshAmount>();
			if (oneLimitAmount != null&&oneAmount!=null) {
				FreshAmount firstFreshAmount = this.getFreshAmount(oneLimitAmount, oneAmount, days, new Date(), 1,
						fundRecord.getUserId());
				freshAmounts.add(firstFreshAmount);
				totalAmount=totalAmount.add(oneAmount);
			}
			if(threeLimitAmount!=null&&threeAmount!=null){
				FreshAmount secondFreshAmount = this.getFreshAmount(threeLimitAmount, threeAmount, days, new Date(), 3,
						fundRecord.getUserId());
				freshAmounts.add(secondFreshAmount);
				totalAmount=totalAmount.add(threeAmount);
			}
			if(sixLimitAmount!=null&&sixAmount!=null){
				FreshAmount thirdFreshAmount = this.getFreshAmount(sixLimitAmount, sixAmount, days, new Date(), 6,
						fundRecord.getUserId());
				freshAmounts.add(thirdFreshAmount);
				totalAmount=totalAmount.add(sixAmount);
			}
			if(twelveLimitAmount!=null&&twelveAmount!=null){
				FreshAmount fourFreshAmount = this.getFreshAmount(twelveLimitAmount, twelveAmount, days, new Date(), 12,
						fundRecord.getUserId());
				freshAmounts.add(fourFreshAmount);
				totalAmount=totalAmount.add(twelveAmount);
			}
			if(freshAmounts.size()==0){
				request.getSession().removeAttribute("record");
				return;
			}
			int count = freshAmountService.addBatchFreshAmount(freshAmounts);
			User user = userService.queryById(fundRecord.getUserId());
			if(count>0&&totalAmount.doubleValue()!=0&&user!=null){
				DESTextCipher cipher = DESTextCipher.getDesTextCipher();
				Map<String,Object> message=new HashMap<String,Object>();
				message.put("info", "恭喜您成功获得"+totalAmount.toString()+"元红包,快去“我的账户-我的福利”中查收吧!成功使用后可直接变现哦!");
				message.put("mobiles",cipher.decrypt(user.getMobile()));
				ShortMessage.getShortMessage().sendToUserMsg(message);
			}
			request.getSession().removeAttribute("record");
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		}
	}

	public FreshAmount getFreshAmount(BigDecimal lmitAmount, BigDecimal amount, int days, Date date, int months,
			String userId) {
		FreshAmount freshAmount = new FreshAmount();
		freshAmount.setId(UUID.randomUUID().toString().toUpperCase());
		freshAmount.setCreateTime(date);
		freshAmount.setAmount(amount);
		freshAmount.setStatus(0);
		freshAmount.setType(1);
		freshAmount.setName("红包");
		freshAmount.setEffectDay(days);
		if (days == 0) {
			freshAmount.setEndTime(new Date(date.getTime() + 100000 * 24 * 3600 * 1000L));
		} else {
			freshAmount.setEndTime(new Date(date.getTime() + days * 24 * 3600 * 1000L));
		}
		freshAmount.setAmountLimit(lmitAmount);
		freshAmount.setMonths(months);
		freshAmount.setUserId(userId);
		return freshAmount;
	}
	
	//投资发放红包并兑现红包
//	@AfterReturning("execution(* com.zhongyang.java.biz.impl.InvestBizImpl.bidTransactionSettleBiz(..))")
	public void investFreshAmount(){
		try{
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			Invest invest = (Invest) request.getSession().getAttribute("fresh");
			if (invest == null)
				return;
			
			Loan loan = loanService.queryLoanById(invest.getLoanid());
			if(loan.getAddRate()!=0){
				request.getSession().removeAttribute("fresh");
				return;
			}
			
			User user = userService.queryById(invest.getUserid());
			List<FreshAmount> results=null;
			FreshAmount freshAmount = new FreshAmount();
			if(invest.getFreshAmountId()!=null&&!"".equals(invest.getFreshAmountId())){
				freshAmount.setId(invest.getFreshAmountId());
				freshAmount.setStatus(0);
				freshAmount.setType(1);
				results = freshAmountService.queryFreshAmountByParams(freshAmount);
			}
			//投资兑现红包
			if (results!= null&&results.size()!= 0) {
				logger.info("投资兑现红包:"+invest.getUserid());
				for (FreshAmount fa : results) {
					double value = fa.getAmountLimit().subtract(invest.getAmount()).doubleValue();
					if (value > 0)
						continue;
					if (fa.getMonths() != 0 && fa.getMonths() != invest.getMonths())
						continue;
					String ret_code = this.getUmpaySignature(fa);
					if("0000".equals(ret_code)){
						if(user!=null){
							DESTextCipher cipher = DESTextCipher.getDesTextCipher();
							Map<String,Object> message=new HashMap<String,Object>();
							message.put("info", "恭喜您成功使用红包,"+fa.getAmount().toString()+"元已经直接进入您的个人资金账户,请注意查收。");
							message.put("mobiles",cipher.decrypt(user.getMobile()));
							ShortMessage.getShortMessage().sendToUserMsg(message);
						}
					}
				}
				
			}
			
			
			//投资发放红包
			
			Map<String, Object>map=null;
			
			//所有标的都可以投的红包
//			map=ZYCF_ALL_LIMITAMOUNT;
			
			//限标的投资的红包
			switch (invest.getMonths()) {
				case 0:map=ZYCF_ALL_LIMITAMOUNT; break;
				case 1:map=ZYCF_ONE_LIMITAMOUNT; break;
				case 3:map=ZYCF_THREE_LIMITAMOUNT; break;
				case 6:map=ZYCF_SIX_LIMITAMOUNT; break;
				case 12:map=ZYCF_TWELVE_LIMITAMOUNT; break;
			}
			int days = 0;
			BigDecimal limitAmount = null;
			BigDecimal amount = null;
			double com=-Double.MAX_VALUE;
			for (String key : map.keySet()) {
				String res = (String) map.get(key);
				String[] split = res.split(",");
				BigDecimal sub = new BigDecimal(split[0]);// 发放红包投资额度
				double value = sub.subtract(invest.getAmount()).doubleValue();
				if(value<=0&&value>com&&new BigDecimal(split[2]).doubleValue()!=0){
					limitAmount=new BigDecimal(split[1]);
					amount=new BigDecimal(split[2]);
					days=Integer.valueOf(split[4]);
					com=value;
				}
			}
			FreshAmount fresh=null;
			if(limitAmount!=null&&amount!=null){
				fresh = getFreshAmount(limitAmount,amount,days,new Date(),invest.getMonths(),invest.getUserid());
			}
			if(fresh!=null){
				int count = freshAmountService.addFreshAmount(fresh);
				if(count>0&&user!=null&&amount!=null&&amount.doubleValue()!=0){
					logger.info("投资发放红包:"+invest.getUserid()+amount.toString()+"元");
					DESTextCipher cipher = DESTextCipher.getDesTextCipher();
					Map<String,Object> message=new HashMap<String,Object>();
					message.put("info", "恭喜您成功获得"+amount.toString()+"元红包,快去“我的账户-我的福利”中查收吧！成功使用后可直接变现哦！");
					message.put("mobiles",cipher.decrypt(user.getMobile()));
					ShortMessage.getShortMessage().sendToUserMsg(message);
				}
			}
			request.getSession().removeAttribute("fresh");
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		}
		
	}
	
	//体验金利息兑现
	@AfterReturning("execution(* com.zhongyang.java.biz.impl.InvestBizImpl.bidTransactionSettleBiz(..))")
	public void investAfter() {
		try{
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			Invest invest = (Invest) request.getSession().getAttribute("invest");
			if (invest == null)
				return;
			logger.info("体验金利息兑现:"+invest.getUserid());
			FreshAmount freshAmount = new FreshAmount();
			freshAmount.setUserId(invest.getUserid());
			freshAmount.setStatus(0);
			freshAmount.setType(2);
			List<FreshAmount> results = freshAmountService.queryFreshAmountByParams(freshAmount);
			if (results == null || results.size() == 0) {
				request.getSession().removeAttribute("invest");
				return;
			}
			
			for (FreshAmount fa : results) {
				int value = fa.getAmountLimit().subtract(invest.getAmount()).intValue();
				if (value > 0)
					continue;
				if (fa.getMonths() != 0 && fa.getMonths() != invest.getMonths())
					continue;
				this.getUmpaySignature(fa);
				
			}
			request.getSession().removeAttribute("invest");
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		}
	}
	
	public String getUmpaySignature(FreshAmount fa){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			
			UserFund userFund = new UserFund();
			userFund.setUserId(fa.getUserId());
			UserFund fund = userFundService.byUserID(userFund);
			String order = ApplicationBean.order();
			this.addClientFundRecord(fa, order);
			this.addFundRecord(fa, order, fund);
			UmpAccount umpAccount = new UmpAccount();
			umpAccount.setUserId(fa.getUserId());
			UmpAccount account = umpAccountService.queryInfoByUmpAccount(umpAccount);
			BigDecimal multiply = fa.getAmount().multiply(new BigDecimal(100)).setScale(0,
					BigDecimal.ROUND_HALF_UP);
			Map<String, String> map = new HashMap<String, String>();
			map.put("notify_url", ZYCFMARKET_IP + "/transferCallback");
			map.put("order_id", order);
			map.put("mer_date", sdf.format(new Date()));
			map.put("mer_account_id", ZYCF_UMP_ACCOUNT);
			map.put("partic_acc_type", "01");
			map.put("trans_action", "02");
			map.put("partic_user_id", account.getAccountName());
			map.put("amount", multiply.toString());
			UmpaySignature umpaySignature = new UmpaySignature("transfer", map);
			Map<String, String> signature;
			
			signature = umpaySignature.getSignature();
			 
			String ret_code = signature.get("ret_code");
			if (!"0000".equals(ret_code)) {
				ClientFundRecord record = new ClientFundRecord();
				record.setOrderid(order);
				record.setStatus(FundRecordStatus.FAILED);
				clientFundRecordService.modifyClientFundRecord(record);
	
				FundRecord fundRecord = new FundRecord();
				fundRecord.setOrderid(order);
				fundRecord.setStatus(FundRecordStatus.FAILED);
				fundRecordService.modifyFundRecord(fundRecord);
			} else {
				ClientFundRecord record = new ClientFundRecord();
				record.setOrderid(order);
				record.setStatus(FundRecordStatus.AUDITING);
				clientFundRecordService.modifyClientFundRecord(record);
	
				FundRecord fundRecord = new FundRecord();
				fundRecord.setOrderid(order);
				fundRecord.setStatus(FundRecordStatus.AUDITING);
				fundRecordService.modifyFundRecord(fundRecord);
	
				fa.setStatus(1);
				fa.setUseTime(new Date());
				freshAmountService.modifyFreshAmount(fa);
			}
			return ret_code;
		} catch (ReqDataException | RetDataException e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		}
		return null;
	}

	public void addClientFundRecord(FreshAmount freshAmount, String orderId) {
		ClientFundRecord record = new ClientFundRecord();
		record.setId(UUID.randomUUID().toString().toUpperCase());
		record.setAccount(ZYCF_UMP_ACCOUNT);
		record.setAmount(freshAmount.getAmount());
		if(freshAmount.getType()==1){
			record.setDescription("红包兑现");
			record.setRecordpriv("用户userId=" + freshAmount.getUserId() + "红包返现");
		}else{
			record.setDescription("体验金利息兑现");
			record.setRecordpriv("用户userId=" + freshAmount.getUserId() + "体验金利息返现");
		}
		record.setOperation(FundRecordOperation.OUT);
		record.setOrderid(orderId);
		record.setStatus(FundRecordStatus.PROCESSING);
		record.setTimerecorded(new Date());
		record.setType(FundRecordType.TRANSFEROUT);
		record.setUserid(freshAmount.getUserId());
		try {
			clientFundRecordService.addClientFundRecord(record);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		}
	}

	public void addFundRecord(FreshAmount freshAmount, String orderId, UserFund fund) {
		try {
			FundRecord fundRecord = new FundRecord();
			fundRecord.setId(UUID.randomUUID().toString().toUpperCase());
			fundRecord.setAmount(freshAmount.getAmount());
			fundRecord.setAviAmount(fund.getAvailableAmount());
			if(freshAmount.getType()==1){
				fundRecord.setDescription("红包兑现");
			}else{
				fundRecord.setDescription("体验金利息兑现");
			}
			fundRecord.setOperation(FundRecordOperation.IN);
			fundRecord.setOrderid(orderId);
			fundRecord.setStatus(FundRecordStatus.PROCESSING);
			fundRecord.setTimerecorded(new Date());
			fundRecord.setType(FundRecordType.TRANSFERIN);
			fundRecord.setUserId(freshAmount.getUserId());
			fundRecordService.create(fundRecord);

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		}
	}

}
