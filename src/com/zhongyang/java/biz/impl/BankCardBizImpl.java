package com.zhongyang.java.biz.impl;

import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umpay.api.exception.ReqDataException;
import com.umpay.api.exception.RetDataException;
import com.zhongyang.java.biz.BankCardBiz;
import com.zhongyang.java.pojo.FundAccount;
import com.zhongyang.java.pojo.ModifyBankCard;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.UmpAgreement;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.FundAccountService;
import com.zhongyang.java.service.ModifyBankCardService;
import com.zhongyang.java.service.UmpAccountService;
import com.zhongyang.java.service.UmpAgreementService;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.system.DESTextCipher;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.ZSession;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.config.ApplicationBean;
import com.zhongyang.java.system.umpay.UmpaySignature;
import com.zhongyang.java.vo.ModifyBankCardCallBackVO;
import com.zhongyang.java.vo.bankCardCallBackVO;
import com.zhongyang.java.vo.fund.BankRecordStatus;
import com.zhongyang.java.vo.fund.BankRecordType;

@Service
public class BankCardBizImpl implements BankCardBiz {
	
	private static Logger logger=Logger.getLogger(BankCardBizImpl.class);

	@Autowired
	private UmpAccountService umpAccountService;
	@Autowired
	private FundAccountService fundAccountService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UmpAgreementService umpAgreementService;
	
	@Autowired
	private ModifyBankCardService modifyBankCardService;
	
	@Override
	public Message bankCardBinding(String bank, String account, String fastPayment, User user,String source) {
		Message message = new Message();
		try {
			
			//如果已经绑过卡，限制
			FundAccount fundAccount = new FundAccount();
			fundAccount.setUserId(user.getId());
			fundAccount = fundAccountService.getFundAccountById(fundAccount);
			
			if(fundAccount!=null)
			{
				message.setCode(5);
				message.setMessage("已绑卡");
				logger.info(user.getId()+":已绑卡，不可重复绑卡");
				return  message;
			}
			
			ModifyBankCard bankCardBinding=new ModifyBankCard();
			bankCardBinding.setType(BankRecordType.BANDING);
			bankCardBinding.setStatus(BankRecordStatus.PROCESSING);
			bankCardBinding.setUserId(user.getId());
			List<ModifyBankCard> result = modifyBankCardService.queryByParams(bankCardBinding);
			if(result!=null&&result.size()!=0){
				message.setCode(6);
				message.setMessage("绑卡处理中，不可以重复绑卡");
				logger.info(user.getId()+":绑卡处理中，不可以重复绑卡");
				return  message;
			}
			
			UmpAccount umpAccount = new UmpAccount();
			umpAccount.setUserId(user.getId());
			umpAccount = umpAccountService.byUserIdUmpAccount(umpAccount);
			
			Map<String, Object> sysMap = SystemPro.getProperties();
			String zycfurlIp = (String) sysMap.get("ZYCFMARKET_IP");
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			Map<String, String> map = new HashMap<String, String>();
			String orderId=ApplicationBean.order();
			
			ModifyBankCard modifyBankCard=new ModifyBankCard();
			modifyBankCard.setId(UUID.randomUUID().toString().toUpperCase());
			modifyBankCard.setOrderId(orderId);
			modifyBankCard.setUserId(user.getId());
			modifyBankCard.setStatus(BankRecordStatus.INITIED);
			modifyBankCard.setBank(bank);
			modifyBankCard.setAccount(account);
			modifyBankCard.setType(BankRecordType.BANDING);
			modifyBankCard.setCreateDate(new Date());
			modifyBankCardService.addModifyBankCard(modifyBankCard);
			
			map.put("order_id", orderId);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMdd");
			Date currentTime = new Date();
			String dateString = simpleDateFormat.format(currentTime);
			map.put("mer_date", dateString);
			map.put("user_id", umpAccount.getAccountName());
			map.put("account_name", user.getName());
			map.put("identity_type", "IDENTITY_CARD");
			map.put("identity_code", cipher.decrypt(user.getIdnumber()));
			map.put("is_open_fastPayment", fastPayment);
			map.put("card_id", account);
			if(source!=null&&source.equals("wap")){
				map.put("ret_url", "https://www.atomfin.com/market/zycfMarket/wap/html/account/bindCardSuc.html");
			}
			else{
				map.put("ret_url", zycfurlIp+"/html/umpreturnMsg/umpReturnMsg.html");
			}
			map.put("notify_url", zycfurlIp+"/umpCardBinding");
			UmpaySignature umpaySignature = new UmpaySignature("ptp_mer_bind_card", map);
			String valueUrl = umpaySignature.getSignatureStrng();
			ZSession zsession = ZSession.getzSession();
			Map<String, Object> sessionMap = zsession.getzSessionMap();
			Map<String, String> bankCardMap = new HashMap<String, String>();
			bankCardMap.put("bank", bank);
			bankCardMap.put("user_id", user.getId());
			bankCardMap.put("order_id", orderId);
			bankCardMap.put("account", account);
			bankCardMap.put("name", user.getName());
		//	bankCardMap.put("token", "OPEN");
			sessionMap.put(orderId, bankCardMap);
			message.setCode(1);
			message.setMessage(valueUrl);
			return message;
		} catch (GeneralSecurityException | ReqDataException e) {
			logger.info(e,e.fillInStackTrace());
			throw new UException(e);
		}

	}
	
	
	@Override
	public Message appBankCardBinding(String bank, String account, String fastPayment, User user) {
		Message message = new Message();
		try {
			
			FundAccount fundAccount = new FundAccount();
			fundAccount.setUserId(user.getId());
			fundAccount = fundAccountService.getFundAccountById(fundAccount);
			
			if(fundAccount!=null)
			{
				message.setCode(5);
				message.setMessage("已绑卡,不可重复绑卡");
				logger.info(user.getId()+":已绑卡。不可重复绑卡");
				return  message;
			}
			
			ModifyBankCard bankCardBinding=new ModifyBankCard();
			bankCardBinding.setType(BankRecordType.BANDING);
			bankCardBinding.setStatus(BankRecordStatus.PROCESSING);
			bankCardBinding.setUserId(user.getId());
			List<ModifyBankCard> result = modifyBankCardService.queryByParams(bankCardBinding);
			if(result!=null&&result.size()!=0){
				message.setCode(6);
				message.setMessage("绑卡处理中，不可以重复绑卡");
				logger.info(user.getId()+":绑卡处理中，不可以重复绑卡");
				return  message;
			}
			
			UmpAccount umpAccount = new UmpAccount();
			umpAccount.setUserId(user.getId());
			umpAccount = umpAccountService.byUserIdUmpAccount(umpAccount);
			Map<String, Object> sysMap = SystemPro.getProperties();
			String zycfurlIp = (String) sysMap.get("ZYCFMARKET_IP");
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			Map<String, String> map = new HashMap<String, String>();
			String orderId = ApplicationBean.order();
			
			ModifyBankCard modifyBankCard=new ModifyBankCard();
			modifyBankCard.setId(UUID.randomUUID().toString().toUpperCase());
			modifyBankCard.setOrderId(orderId);
			modifyBankCard.setUserId(user.getId());
			modifyBankCard.setStatus(BankRecordStatus.INITIED);
			modifyBankCard.setBank(bank);
			modifyBankCard.setAccount(account);
			modifyBankCard.setType(BankRecordType.BANDING);
			modifyBankCard.setCreateDate(new Date());
			modifyBankCardService.addModifyBankCard(modifyBankCard);
						
			map.put("order_id", orderId);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMdd");
			Date currentTime = new Date();
			String dateString = simpleDateFormat.format(currentTime);
			map.put("mer_date", dateString);
			map.put("user_id", umpAccount.getAccountName());
			map.put("account_name", user.getName());
			map.put("identity_type", "IDENTITY_CARD");
			map.put("identity_code", cipher.decrypt(user.getIdnumber()));
			map.put("is_open_fastPayment", fastPayment);
			map.put("card_id", account);
			map.put("ret_url", "app:bankcardBinding");
			map.put("notify_url", zycfurlIp+"/umpCardBinding");
			map.put("sourceV", "HTML5");
			UmpaySignature umpaySignature = new UmpaySignature("ptp_mer_bind_card", map);
			String valueUrl = umpaySignature.getSignatureStrng();
			ZSession zsession = ZSession.getzSession();
			Map<String, Object> sessionMap = zsession.getzSessionMap();
			Map<String, String> bankCardMap = new HashMap<String, String>();
			bankCardMap.put("bank", bank);
			bankCardMap.put("user_id", user.getId());
			bankCardMap.put("order_id", orderId);
			bankCardMap.put("account", account);
			bankCardMap.put("name", user.getName());
			bankCardMap.put("is_open_fastPayment", fastPayment);
		//	bankCardMap.put("token", "OPEN");
			sessionMap.put(orderId, bankCardMap);
			
			// 快捷支付协议处理
			UmpAgreement umpAgreement = new UmpAgreement();
			umpAgreement.setCardno(account);
			umpAgreement.setAccountname(umpAccount.getAccountName());
			umpAgreement.setTimecreated(new Date());
			umpAgreement.setTimelastupdated(new Date());
			umpAgreement.setUserid(user.getId());
			//判断是否已经开通其他协议
			if(umpAgreementService.byUserUmpAgreement(umpAgreement)==null)
				umpAgreementService.insertAgreement(umpAgreement);
			
			message.setCode(1);
			message.setMessage(valueUrl);
			return message;
		} catch (GeneralSecurityException | ReqDataException e) {
			logger.info(e,e.fillInStackTrace());
			throw new UException(e);
		}

	}
	
/**
 * 第三方支付绑卡回调
 * 
 */
	@Override
	public String umpCardBinding(bankCardCallBackVO vo) {
		
		FundAccount fundAccount = new FundAccount();
		Map<String,String> returnMap=new HashMap<String,String>();
		try {
			ModifyBankCard modifyBankCard=new ModifyBankCard();
			modifyBankCard.setOrderId(vo.getOrder_id());
			List<ModifyBankCard> result = modifyBankCardService.queryByParams(modifyBankCard);
			if(result!=null&&result == null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"未获得绑定银行卡记录");
			}
			
			ZSession zsession = ZSession.getzSession();
			Map<String, String> bankCardMap = (Map<String, String>) zsession.getzSessionMap().get(vo.getOrder_id());
			//如果回调已经处理成功，不做处理
			if(bankCardMap == null || bankCardMap.isEmpty())
				return null;
			
			if ("0000".equals(vo.getRet_code())&&"mer_bind_card_apply_notify".equals(vo.getService())&&BankRecordStatus.INITIED.equals(result.get(0).getStatus())) {
				
				ModifyBankCard modify=new ModifyBankCard();
				modify.setStatus(BankRecordStatus.PROCESSING);
				modify.setId(result.get(0).getId());
				modifyBankCardService.modifyModifyBankCard(modify);
				
				returnMap.clear();
				returnMap.put("order_id", vo.getOrder_id());
				returnMap.put("ret_code", vo.getRet_code());
				UmpaySignature umpaySignature = new UmpaySignature(returnMap);
				return umpaySignature.callBackSignature();
			}
			if (!"0000".equals(vo.getRet_code())&&"mer_bind_card_apply_notify".equals(vo.getService())) {
				
				ModifyBankCard modify=new ModifyBankCard();
				modify.setStatus(BankRecordStatus.FAILED);
				modify.setId(result.get(0).getId());
				modifyBankCardService.modifyModifyBankCard(modify);
				
				returnMap.clear();
				returnMap.put("order_id", vo.getOrder_id());
				returnMap.put("ret_code", vo.getRet_code());
				UmpaySignature umpaySignature = new UmpaySignature(returnMap);
				return umpaySignature.callBackSignature();
			}
			if ("0000".equals(vo.getRet_code())&&"mer_bind_card_notify".equals(vo.getService())&&BankRecordStatus.PROCESSING.equals(result.get(0).getStatus())) {
				
				ModifyBankCard modify=new ModifyBankCard();
				modify.setStatus(BankRecordStatus.SUCCESSFUL);
				modify.setId(result.get(0).getId());
				modifyBankCardService.modifyModifyBankCard(modify);
				
				fundAccount.setId(UUID.randomUUID().toString().toUpperCase());
				fundAccount.setBank(bankCardMap.get("bank"));
				fundAccount.setUserId(bankCardMap.get("user_id"));
				fundAccount.setName(bankCardMap.get("name"));
				String cc = bankCardMap.get("account");
				String bankNum = cc.substring(0, 4)+"******"+cc.substring(cc.length()-4, cc.length());//银行卡号中间位数影藏
				fundAccount.setAccount(bankNum);
				fundAccount.setTimerecorded(new Date());
				fundAccountService.addFucndAccount(fundAccount);
				
				zsession.getzSessionMap().remove(vo.getOrder_id());  //处理成功，移除会话
				
				returnMap.clear();
				returnMap.put("order_id", vo.getOrder_id());
				returnMap.put("ret_code", "0000");
				UmpaySignature umpaySignature = new UmpaySignature(returnMap);
				
				
				//更新用户绑卡状态
				User user = new User();
				user.setId(bankCardMap.get("user_id"));
				user.setCardauthenticated(true);
				userService.updateUser(user);
				
				//更新用户快捷支付协议状态
				UmpAgreement umpAgreement = new UmpAgreement();
				umpAgreement.setUserid(bankCardMap.get("user_id"));
				umpAgreement = umpAgreementService.byUserUmpAgreement(umpAgreement);
				
				String is_open_fastPayment = bankCardMap.get("is_open_fastPayment");
				
				if(is_open_fastPayment!=null && is_open_fastPayment.equalsIgnoreCase("1")){
					umpAgreement.setDebit(true);
					umpAgreement.setInstant(true);
					umpAgreementService.updateAgreement(umpAgreement);
				}
				return umpaySignature.callBackSignature();
			}
			if(!"0000".equals(vo.getRet_code())&&"mer_bind_card_notify".equals(vo.getService())){
				
				ModifyBankCard modify=new ModifyBankCard();
				modify.setStatus(BankRecordStatus.FAILED);
				modify.setId(result.get(0).getId());
				modifyBankCardService.modifyModifyBankCard(modify);
				
				returnMap.clear();
				returnMap.put("order_id", vo.getOrder_id());
				returnMap.put("ret_code", vo.getRet_code());
				UmpaySignature umpaySignature = new UmpaySignature(returnMap);
				return umpaySignature.callBackSignature();
			}
		} catch (ReqDataException | RetDataException e) {
			logger.info(e,e.fillInStackTrace());
		}
		return null;
	}


	@Override
	public Message modifyBankCard(String bank, String account, User user,String sourceV,String source) {
		Message message = new Message();
		try {
			if(user==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"没有登录");
			}
			user=userService.queryById(user.getId());
			
			
			/*UmpAgreement umpAgreement=new UmpAgreement();
			umpAgreement.setUserid(user.getId());
			UmpAgreement queryUmpAgreement = umpAgreementService.byUserUmpAgreement(umpAgreement);
			if(queryUmpAgreement.getDebit()||queryUmpAgreement.getInstant()||queryUmpAgreement.getInvest()||queryUmpAgreement.getRepay()){
				message.setCode(6);
				message.setMessage("已开通快捷协议，不可以换卡");
				logger.info(user.getId()+":已开通快捷协议，不可以换卡");
				return  message;
			}*/
			
			FundAccount acc = new FundAccount();
			acc.setUserId(user.getId());
			FundAccount queryfundAccount = fundAccountService.queryFundAccountByParams(acc);
			
			UmpAccount umpAccount = new UmpAccount();
			umpAccount.setUserId(user.getId());
			umpAccount = umpAccountService.byUserIdUmpAccount(umpAccount);
			
			ModifyBankCard bankCardBinding=new ModifyBankCard();
			bankCardBinding.setType(BankRecordType.CHANG);
			bankCardBinding.setStatus(BankRecordStatus.PROCESSING);
			bankCardBinding.setUserId(user.getId());
			List<ModifyBankCard> result = modifyBankCardService.queryByParams(bankCardBinding);
			if(result!=null&&result.size()!=0){
				message.setCode(6);
				message.setMessage("换卡处理中，不可以重复换卡");
				logger.info(user.getId()+":换卡处理中，不可以重复换卡");
				return  message;
			}
			
			Map<String, Object> sysMap = SystemPro.getProperties();
			String zycfurlIp = (String) sysMap.get("ZYCFMARKET_IP");
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			Map<String, String> map = new HashMap<String, String>();
			String orderId=ApplicationBean.order();
			ModifyBankCard modifyBankCard=new ModifyBankCard();
			modifyBankCard.setId(UUID.randomUUID().toString().toUpperCase());
			modifyBankCard.setOrderId(orderId);
			modifyBankCard.setUserId(user.getId());
			modifyBankCard.setFundAccountId(queryfundAccount.getId());
			modifyBankCard.setStatus(BankRecordStatus.INITIED);
			modifyBankCard.setBank(bank);
			modifyBankCard.setAccount(account);
			modifyBankCard.setType(BankRecordType.CHANG);
			modifyBankCard.setCreateDate(new Date());
			modifyBankCardService.addModifyBankCard(modifyBankCard);
			
			map.put("order_id", orderId);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMdd");
			Date currentTime = new Date();
			String dateString = simpleDateFormat.format(currentTime);
			map.put("mer_date", dateString);
			map.put("user_id", umpAccount.getAccountName());
			map.put("account_name", user.getName());
			map.put("identity_type", "IDENTITY_CARD");
			map.put("identity_code", cipher.decrypt(user.getIdnumber()));
			if(sourceV!=null&&"HTML5".equals(sourceV)){
				map.put("sourceV", sourceV);
				map.put("ret_url", "app:modifyBankCard");
			}
			else if(source!=null&&source.equals("wap")){
				map.put("ret_url", zycfurlIp+"/wap/html/account/bindCardSuc.html");
			}
			else{
				map.put("ret_url", zycfurlIp+"/html/umpreturnMsg/umpReturnMsg.html");
			}
			map.put("card_id", account);
			map.put("notify_url", zycfurlIp+"/modifyBankCardCallBack");
			UmpaySignature umpaySignature = new UmpaySignature("ptp_mer_replace_card", map);
			String valueUrl = umpaySignature.getSignatureStrng();
			
			message.setCode(1);
			message.setMessage(valueUrl);
			return message;
		} catch (GeneralSecurityException | ReqDataException e) {
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		}
	}


	@Override
	public String modifyBankCardCallBack(ModifyBankCardCallBackVO vo) {
		Map<String,String> returnMap=new HashMap<String,String>();
		try {
			ModifyBankCard modifyBankCard=new ModifyBankCard();
			modifyBankCard.setOrderId(vo.getOrder_id());
			List<ModifyBankCard> result = modifyBankCardService.queryByParams(modifyBankCard);
			if(result == null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"未获得更换银行卡记录");
			}
			
			UmpAccount umpAccount = new UmpAccount();
			umpAccount.setUserId(result.get(0).getUserId());
			umpAccount = umpAccountService.byUserIdUmpAccount(umpAccount);
			
			//第一次回调
			if (BankRecordStatus.INITIED.equals(result.get(0).getStatus())&&"0000".equals(vo.getRet_code())&&"mer_bind_card_apply_notify".equals(vo.getService())) {

				ModifyBankCard modify=new ModifyBankCard();
				modify.setStatus(BankRecordStatus.PROCESSING);
				modify.setId(result.get(0).getId());
				modifyBankCardService.modifyModifyBankCard(modify);
				
				returnMap.clear();
				returnMap.put("order_id", vo.getOrder_id());
				returnMap.put("ret_code", vo.getRet_code());
				UmpaySignature umpaySignature = new UmpaySignature(returnMap);
				return umpaySignature.callBackSignature();
			} 
			if(BankRecordStatus.FAILED.equals(result.get(0).getStatus())&&!"0000".equals(vo.getRet_code())&&"mer_bind_card_notify".equals(vo.getService())){
				ModifyBankCard modify=new ModifyBankCard();
				modify.setStatus(BankRecordStatus.FAILED);
				modify.setId(result.get(0).getId());
				modifyBankCardService.modifyModifyBankCard(modify);
				
				returnMap.clear();
				returnMap.put("order_id", vo.getOrder_id());
				returnMap.put("ret_code", vo.getRet_code());
				UmpaySignature umpaySignature = new UmpaySignature(returnMap);
				return umpaySignature.callBackSignature();
			}
			
			//第二次回调
			if (BankRecordStatus.PROCESSING.equals(result.get(0).getStatus())&&"0000".equals(vo.getRet_code())&&"mer_bind_card_notify".equals(vo.getService())) {
				
				ModifyBankCard modify=new ModifyBankCard();
				modify.setStatus(BankRecordStatus.SUCCESSFUL);
				modify.setId(result.get(0).getId());
				modifyBankCardService.modifyModifyBankCard(modify);
				
				FundAccount fa = new FundAccount();
				fa.setId(result.get(0).getFundAccountId());
				fa.setBank(result.get(0).getBank());
				String cc = result.get(0).getAccount();
				String bankNum = cc.substring(0, 4)+"******"+cc.substring(cc.length()-4, cc.length());//银行卡号中间位数影藏
				fa.setAccount(bankNum);
				fundAccountService.modifyFunAccount(fa);
				
				returnMap.clear();
				returnMap.put("order_id", vo.getOrder_id());
				returnMap.put("ret_code", vo.getRet_code());
				UmpaySignature umpaySignature = new UmpaySignature(returnMap);
				return umpaySignature.callBackSignature();
			}
			
			if(BankRecordStatus.FAILED.equals(result.get(0).getStatus())&&!"0000".equals(vo.getRet_code())&&"mer_bind_card_notify".equals(vo.getService())){
				ModifyBankCard modify=new ModifyBankCard();
				modify.setStatus(BankRecordStatus.FAILED);
				modify.setId(result.get(0).getId());
				modifyBankCardService.modifyModifyBankCard(modify);
				
				returnMap.clear();
				returnMap.put("order_id", vo.getOrder_id());
				returnMap.put("ret_code", vo.getRet_code());
				UmpaySignature umpaySignature = new UmpaySignature(returnMap);
				return umpaySignature.callBackSignature();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		}
		return null;
	}
}
