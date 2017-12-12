package com.zhongyang.java.biz.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umpay.api.exception.ReqDataException;
import com.umpay.api.exception.RetDataException;
import com.zhongyang.java.biz.FundAccountBiz;
import com.zhongyang.java.biz.FundRecordBiz;
import com.zhongyang.java.biz.UmpAccountBiz;
import com.zhongyang.java.biz.UmpAgreementBiz;
import com.zhongyang.java.biz.UserFundBiz;
import com.zhongyang.java.pojo.ClientFundRecord;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.service.ClientFundRecordService;
import com.zhongyang.java.service.FundRecordService;
import com.zhongyang.java.service.UmpAccountService;
import com.zhongyang.java.service.UserFundService;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.config.ApplicationBean;
import com.zhongyang.java.system.uitl.BigDecimalAlgorithm;
import com.zhongyang.java.system.umpay.UmpaySignature;
import com.zhongyang.java.vo.app.OperatingFundsVo;
import com.zhongyang.java.vo.fund.FundRecordOperation;
import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.fund.FundRecordType;
import com.zhongyang.java.vo.fund.OrderTime;
import com.zhongyang.java.vo.fund.PayType;
import com.zhongyang.java.vo.fund.UmpRechargeVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月2日 下午5:21:08
* 类说明：用户UmpAccount
*/
@Service	
public class UmpAccountBizImpl  implements UmpAccountBiz{
	
	private static Logger loger=Logger.getLogger(UmpAccountBizImpl.class.getName());
	
	@Autowired
	UmpAccountService umpAccountService;
	
	@Autowired
	UmpAgreementBiz umpAgreementBiz;
	
	@Autowired
	FundAccountBiz fundAccountBiz;
	
	@Autowired
	UserFundBiz userFundBiz;
	
	@Autowired
	FundRecordBiz fundRecordBiz;
	
	@Autowired
	UserFundService userFundService;
	
	@Autowired
	FundRecordService fundRecordService;
	
	@Autowired
	ClientFundRecordService clientFundRecordService;
    /*
     * (non-Javadoc)
     * @see com.zhongyang.java.biz.UmpAccountBiz#byUserIdUmpAccount()
     * 根据用户Id查询用户umpaccount信息
     */
	@Override
	public String byUserIdUmpAccount(OperatingFundsVo operatingFundsVo){
		
		try {
			UmpAccount umpAccount = new UmpAccount();
			UmpAccount locaUmpAccount = new UmpAccount();
			umpAccount.setUserId(operatingFundsVo.getUserId());
			locaUmpAccount = umpAccountService.byUserIdUmpAccount(umpAccount);
			if(locaUmpAccount == null){
				loger.info("没有开通第三方账号:userId"+operatingFundsVo.getUserId());
				throw new UException(SystemEnum.NOREGISTERED_UMPACCOUNT, "没有开通第三方账号");
			}else{
				ApplicationBean appen = new ApplicationBean();
				 String orderId = appen.orderId();
				 operatingFundsVo.setOrderId(orderId);
//				 String prepare = this.prepareDeposit(rechargeVo);//准备充值数据
				 String prepare = fundRecordBiz.addNewFundRecord(operatingFundsVo);
				return prepare;
			}
		} catch (Exception e) {
			loger.info("没有开通第三方账号:userId"+operatingFundsVo.getUserId());
			loger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.RECHARGE_EXCEPTION, "充值遇到问题，请联系客服");
		}
		
	}

	 
    /*
     * 根据用户Id查询用户umpaccount信息取现
     */
	@Override
	@Transactional
	public String byUserIdAccount(OperatingFundsVo operatingFundsVo){
		Map<String, Object> sysMap = SystemPro.getProperties();
		ApplicationBean appen = new ApplicationBean();
		String orderId = appen.orderId();
		operatingFundsVo.setOrderId(orderId);
		String withdrawResults = null;
		UserFund userFund = new UserFund();
		userFund.setUserId(operatingFundsVo.getUserId());
		userFund = this.userFundthis(userFund);
		UmpAccount umpAccount = new UmpAccount();
		umpAccount.setUserId(operatingFundsVo.getUserId());
		UmpAccount locaUmpAccount = umpAccountService.byUserIdUmpAccount(umpAccount);
		BigDecimal withdrawAmount= userFund.getAvailableAmount();
		String withdarWfee = (String) sysMap.get("WITHDRAWFEE");
		BigDecimal withdarWfees = new BigDecimal(withdarWfee);
		BigDecimal withdarWAmount= new BigDecimal(operatingFundsVo.getOperatingLimit());
		int amount = withdrawAmount.compareTo(new BigDecimal(operatingFundsVo.getOperatingLimit()));
		int amountFee = withdarWAmount.compareTo(withdarWfees);
		if(amount <= -1 || amountFee <= -1){
			throw new UException(SystemEnum.AMOUNT_TOO_BIG, "申请金额必须大于手续费或者可用余额");
		}else{
			FundRecord fundRecord = this.preparewithdraw(operatingFundsVo,userFund);
			FundRecord fundRecordFee = this.preparewithdrawFee(userFund.getUserId(),orderId);
			try {
			    String zycfurlIp = (String) sysMap.get("ZYCFMARKET_IP");
			    BigDecimal dj = new BigDecimal(100.00);
				BigDecimal cc = fundRecord.getAmount().subtract(fundRecordFee.getAmount());
				BigDecimal amountwd =  cc.multiply(dj).setScale(0);
				fundRecordService.create(fundRecordFee);//手续费
				fundRecordService.create(fundRecord);//本金
				Map map = new HashMap<String, String>();
				OrderTime ot = new OrderTime();
				if(null != operatingFundsVo.getSourceV() && null==operatingFundsVo.getWap()){
					map.put("sourceV", "HTML5");
					map.put("ret_url", "app:umpWithdraw");
				}else if(null != operatingFundsVo.getSourceV() && "wap".equals(operatingFundsVo.getWap())){
					map.put("sourceV", "HTML5");
					map.put("ret_url", "http://www.zuoyoufy.com/zycfMarket/wap/html/account/withdrawalsSuc.html");
				}else{
					map.put("ret_url", zycfurlIp+"/html/umpreturnMsg/umpReturnMsg.html");
				}
				map.put("notify_url", zycfurlIp+"/umpCallBackWithdraw");
				map.put("apply_notify_flag", 1);
				map.put("order_id", fundRecord.getOrderid());
				map.put("mer_date", ot.getTmeo());
				map.put("user_id", locaUmpAccount.getAccountName());
				map.put("account_id",locaUmpAccount.getAccountId());
				map.put("amount", amountwd);
				if(withdarWfee.endsWith("0")){
					map.put("com_amt_type", 2);
				}else{
					map.put("com_amt_type", 1);
				}
				
				UmpaySignature umpaySignature = new UmpaySignature("cust_withdrawals", map);
				loger.info("提现调用第三方map参数"+map);
				return withdrawResults = umpaySignature.getSignatureStrng();
			} catch (Exception e) {
				loger.info("提现调用第三方失败");
				e.getMessage();
				loger.info(e,e.fillInStackTrace());
				throw new UException(SystemEnum.UMPAT_SIG__FAILED, "提现调用第三方失败");
			}

		}
		
	}
	private UserFund userFundthis(UserFund userFund) {
		try {
			return userFundService.byUserID(userFund);
		} catch (Exception e) {
			loger.info("查询用户资金失败userId"+userFund.getUserId());
			loger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询用户资金失败");
		}
	}
	//提现FundRecord(本地用户资金明细)记录表更新第一次更新(第三方返回之前)
	private static FundRecord preparewithdraw(OperatingFundsVo operatingFundsVo,UserFund userFund) {
		OrderTime orderTime =new OrderTime();
		FundRecord fundRecord =new FundRecord();
		fundRecord.setId(UUID.randomUUID().toString().toUpperCase());
		fundRecord.setAmount(new BigDecimal(operatingFundsVo.getOperatingLimit()));
		fundRecord.setAviAmount(userFund.getAvailableAmount().subtract(new BigDecimal(operatingFundsVo.getOperatingLimit())));
		fundRecord.setOperation(FundRecordOperation.OUT);
		fundRecord.setOrderid(operatingFundsVo.getOrderId());
		fundRecord.setStatus(FundRecordStatus.PROCESSING);
		fundRecord.setTimerecorded(orderTime.getLastUpdateTime());
		fundRecord.setType(FundRecordType.WITHDRAW);
		fundRecord.setDescription("个人用户取现");
		fundRecord.setUserId(operatingFundsVo.getUserId());
		return fundRecord;
	}
	
// 提现FundRecord(本地用户资金明细提现手续费)记录表更新第一次更新(第三方返回之前)
	private static FundRecord preparewithdrawFee(String userId,String orderId) {
		Map<String, Object> sysMap = SystemPro.getProperties();
		String withdrawFee = (String) sysMap.get("WITHDRAWFEE");//获得提现费率
		OrderTime orderTime =new OrderTime();
		FundRecord fundRecord =new FundRecord();
		fundRecord.setId(UUID.randomUUID().toString().toUpperCase());
		fundRecord.setAmount(new BigDecimal(withdrawFee));//
		fundRecord.setOperation(FundRecordOperation.OUT);
		fundRecord.setOrderid(orderId);
		fundRecord.setStatus(FundRecordStatus.PROCESSING);
		fundRecord.setTimerecorded(orderTime.getLastUpdateTime());
		fundRecord.setDescription("个人用户取现手续费");
		fundRecord.setType(FundRecordType.FEE_WITHDRAW);
		fundRecord.setUserId(userId);
		return fundRecord;
	}
	
	//用户转账到平台
	public String transferToBusiness(String transferAmount,String userId) {
		ApplicationBean appen = new ApplicationBean();
		String orderId = appen.orderId();
		String rechargeResults = null;
		int i = this.prepareTransferToBusiness(transferAmount,userId,orderId);
		if(i != 0){
			UmpAccount umpAccount = new UmpAccount();
			umpAccount.setUserId(userId);
			UmpAccount locaUmpAccount = umpAccountService.byUserIdUmpAccount(umpAccount);
			rechargeResults = this.transferToBusinessforUMP(transferAmount,orderId,locaUmpAccount);
		}
		return rechargeResults;
	}
	
    private String transferToBusinessforUMP(String transferAmount,String orderId,UmpAccount umpAccount) {
    	PayType pType = PayType.B2CDEBITBANK;
		String rechargeResults = null;
		OrderTime ot = new OrderTime();
		BigDecimal dj = new BigDecimal(100.00);
		BigDecimal amount = new BigDecimal(transferAmount);
		BigDecimal amounttransfer = amount.multiply(dj).setScale(0);
		Map map = new HashMap<String, String>();

		Map<String, Object> sysMap = SystemPro.getProperties();
	    String zycfurlIp = (String) sysMap.get("ZYCFMARKET_IP");
		map.put("ret_url", zycfurlIp+"/html/umpreturnMsg/umpReturnMsg.html");
		map.put("notify_url",zycfurlIp+"/userToBusinessCallBack");
		map.put("order_id", orderId);
		map.put("mer_date", ot.getTmeo());
		map.put("partic_user_id", umpAccount.getAccountName());
		map.put("partic_account_id", umpAccount.getAccountId());
		map.put("amount", amounttransfer);
		map.put("partic_acc_type", "01");
		UmpaySignature umpaySignature = new UmpaySignature("transfer_asyn", map);
		try {
			rechargeResults = umpaySignature.getSignatureStrng();
		} catch (ReqDataException e) {
			e.printStackTrace();
			loger.info(e,e.fillInStackTrace());
		}
		return rechargeResults;
	}
	////提现FundRecord(本地用户资金明细)记录表更新第一次更新(第三方返回之前)
	private int prepareTransferToBusiness(String transferAmount,String userId,String orderId) {
		int result = 0;
		try {
			OrderTime orderTime =new OrderTime();
			FundRecord fundRecord =new FundRecord();
			BigDecimal amount = new BigDecimal(transferAmount);
			fundRecord.setId(UUID.randomUUID().toString().toUpperCase());
			fundRecord.setAmount(amount);
			fundRecord.setOperation(FundRecordOperation.OUT);
			fundRecord.setOrderid(orderId);
			fundRecord.setStatus(FundRecordStatus.PROCESSING);
			fundRecord.setTimerecorded(orderTime.getLastUpdateTime());
			fundRecord.setDescription("用户向平台转账");
			fundRecord.setType(FundRecordType.TRANSFER);
			fundRecord.setUserId(userId);
			result = fundRecordService.create(fundRecord);
			if(result !=0){
//				this.
			}
		} catch (Exception e) {
			System.out.println("添加用户给平台转账失败 ："+orderId);
			e.printStackTrace();
			loger.info(e,e.fillInStackTrace());
		}
		return result;
		}
	
	public void userToBusinessCallBack(UmpRechargeVo umpRechargeVo) {
		FundRecord fundRecordc = new FundRecord();
		try {
			FundRecord fundRecord = new FundRecord();
			fundRecord.setOrderid(umpRechargeVo.getOrder_id());
			fundRecordc = fundRecordService.findFundRecordByOrderId(fundRecord);
			if("SUCCESSFUL".endsWith(fundRecordc.getStatus().toString())){
				return;
			}else{
				if("0000".endsWith(umpRechargeVo.getRet_code())){
					int c = this.updateUserFundRecord(umpRechargeVo.getOrder_id(),fundRecordc);//修改用户自己记录状态||用户账户||添加平台资金记录
					this.returnCallBack(umpRechargeVo);
				}else{
					int c = this.updateUserFundRecordFail(umpRechargeVo.getOrder_id(),fundRecordc);//修改用户自己记录状态||用户账户||添加平台资金记录:为失败
					this.returnCallBack(umpRechargeVo);
				}
			}
		} catch (Exception e) {
			loger.info("用户转账给平台失败"+umpRechargeVo.getOrder_id());
			e.printStackTrace();
			loger.info(e,e.fillInStackTrace());
		}
		
	}
	
	//扣除手续费回调响应；告诉第三方已经本次交易成功
	private void returnCallBack(UmpRechargeVo umpRechargeVo) {
			try {
				String rechargeResults = null;
				Map map = new HashMap<String, String>();
				map.put("order_id", umpRechargeVo.getOrder_id());
				map.put("mer_date", umpRechargeVo.getMer_date());
				map.put("ret_code", umpRechargeVo.getRet_code());
				UmpaySignature umpaySignature = new UmpaySignature(umpRechargeVo.getService(), map);
				rechargeResults = umpaySignature.callBackSignature();
			} catch (ReqDataException | RetDataException e) {
				loger.info("回调第三方是失败"+umpRechargeVo.getOrder_id());
				e.printStackTrace();
				loger.info(e,e.fillInStackTrace());
			}
			
	}
			
	private int updateUserFundRecordFail(String orderid,FundRecord fundRecord) {
		FundRecord fundRecordObj = new FundRecord();
		int	j = 0;
		try {
			fundRecordObj.setOrderid(orderid);
			fundRecordObj.setStatus(FundRecordStatus.FAILED);
			int i = fundRecordService.updateFundRecordByOrderId(fundRecordObj);
		} catch (Exception e) {
			loger.info("转账到平台跟新记录记录状态失败 ："+orderid);
			e.printStackTrace();
			loger.info(e,e.fillInStackTrace());
		}
		return j;
	}
	private int updateUserFundRecord(String orderid,FundRecord fundRecord) {
		FundRecord fundRecordObj = new FundRecord();
		int	j = 0;
		try {
			fundRecordObj.setOrderid(orderid);
			fundRecordObj.setStatus(FundRecordStatus.SUCCESSFUL);
			int i = fundRecordService.updateFundRecordByOrderId(fundRecordObj);
			if(i != 0){
			int k = this.updateUserFund(fundRecordObj);//修改用户账户余额
			if(k != 0){
				this.addClientFundRecord(fundRecord,orderid);//添加平台资金记录
			}
			}
		} catch (Exception e) {
			loger.info("转账到平台跟新记录记录状态失败 ："+orderid);
			e.printStackTrace();
			loger.info(e,e.fillInStackTrace());
		}
		return j;
	}
	
	private void addClientFundRecord(FundRecord fundRecord,String orderid) {
		try {
			Map<String, Object> sysMap = SystemPro.getProperties();
		    String umpAccount = (String) sysMap.get("ZYCF_UMP_ACCOUNT");
			OrderTime lastTime = new OrderTime();
			ClientFundRecord cfr = new ClientFundRecord();
			cfr.setId(UUID.randomUUID().toString().toUpperCase());
			cfr.setAmount(fundRecord.getAmount());
			cfr.setAccount(umpAccount);
			cfr.setDescription("用户转账到平台");
			cfr.setOperation(FundRecordOperation.IN);
			cfr.setOrderid(fundRecord.getOrderid());
			cfr.setStatus(FundRecordStatus.SUCCESSFUL);
			cfr.setTimerecorded(lastTime.getLastUpdateTime());
			cfr.setType(FundRecordType.TRANSFER);
			cfr.setUserid(fundRecord.getUserId());	
			clientFundRecordService.addClientFundRecord(cfr);
		} catch (Exception e) {
			loger.info("用户转账到平台添加数据失败："+fundRecord.getOrderid());
			e.printStackTrace();
			loger.info(e,e.fillInStackTrace());
		}
	}
	
	private int updateUserFund(FundRecord fundRecord) {
		UserFund userFund =new UserFund();
		int i = 0;
		try {
			UserFund userFundObj  = new UserFund();
			FundRecord fundRecordObg = fundRecordService.findFundRecordByOrderId(fundRecord);
			if(null !=fundRecordObg){
				userFundObj = userFundService.byUserFundId(fundRecordObg.getUserId());
			}
			if(userFundObj != null){
				OrderTime lastTime = new OrderTime();
				BigDecimalAlgorithm algorithm = new BigDecimalAlgorithm();
				BigDecimal avAmount = userFundObj.getAvailableAmount().subtract(fundRecordObg.getAmount());
				userFund.setAvailableAmount(avAmount);//可用额度=原本账户金额-本次转账金额
				userFund.setUserId(userFundObj.getUserId());
				userFund.setTimeLastUpdated(lastTime.getLastUpdateTime());
				i = userFundService.modifyUserFund(userFund);
			}
		} catch (Exception e) {
			loger.info("更新用户账户余额失败："+fundRecord.getOrderid());
			e.printStackTrace();
			loger.info(e,e.fillInStackTrace());
		}
		return i;
	}
}
