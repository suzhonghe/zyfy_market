package com.zhongyang.java.biz.impl;

import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import com.umpay.api.exception.ReqDataException;
import com.umpay.api.exception.RetDataException;
import com.zhongyang.java.biz.UserBiz;
import com.zhongyang.java.pojo.FundAccount;
import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.UmpAgreement;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.service.FundAccountService;
import com.zhongyang.java.service.InvestService;
import com.zhongyang.java.service.UmpAccountService;
import com.zhongyang.java.service.UmpAgreementService;
import com.zhongyang.java.service.UserFundService;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.system.CacheMemory;
import com.zhongyang.java.system.DESTextCipher;
import com.zhongyang.java.system.MStringUtils;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.Password;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.config.ApplicationBean;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.system.umpay.UmpaySignature;
import com.zhongyang.java.vo.CallBackUMP;
import com.zhongyang.java.vo.LoginUser;
import com.zhongyang.java.vo.LoginVO;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.Registered;
import com.zhongyang.java.vo.UserCert;
import com.zhongyang.java.vo.UserInfoVo;

@Service
public class UserBizImpl implements UserBiz {
	
	private static Logger logger=Logger.getLogger(UserBizImpl.class);

	@Autowired
	private UserService userService;
	@Autowired
	private UmpAccountService umpAccountService;
	@Autowired
	private UserFundService userFoundService;
	@Autowired
	private UmpAgreementService umpAgreementService;
	@Autowired
	private InvestService investService;
	@Autowired
	private FundAccountService fundAccountService;
	

	@Override
	@Transactional
	public Message addUser(HttpServletRequest req, Registered registered) {
		String regCode = (String) req.getSession().getAttribute("regcode");
		if (registered.getRegcode() == null
				|| !registered.getRegcode().equals(regCode)) {
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "验证码输入有误！");
		}
		try {
			String salt = Password.getSalt(null);
			String userPassword = Password.getPassphrase(salt,
					registered.getPassphrase());
			User user = new User();
			UUID uuid = UUID.randomUUID();
			user.setId(uuid.toString().toUpperCase());
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			user.setMobile(cipher.encrypt(registered.getMobile().trim()));
			User referralUser = userService.getUserByMobile(cipher.encrypt(registered.getReferralMobile()));
			if(referralUser!=null){
				user.setReferralId(referralUser.getId());
				user.setReferralRealm(referralUser.getName());
			}
			user.setSource(registered.getSource());
			user.setSalt(salt);
			user.setPassphrase(userPassword);
			user.setRegisterdate(new Date());
			user.setLastlogindate(new Date());
			user.setMobileauthenticated(true);
			int status = userService.addUser(user);
			if (status > 0) {
				Message message = new Message();
				message.setCode(0);
				message.setMessage("添加成功");
				req.getSession().setAttribute("zycfLoginUser", user);
				return message;
			} else {
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "添加用户失败");
			}
		} catch (GeneralSecurityException e) {
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "添加用户失败");
		}
	}

	@Override
	public Message mobileRepeat(String mobile, HttpServletRequest request) {
		User user = new User();
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		try {
			user.setMobile(cipher.encrypt(mobile));
			int obj = userService.getUserNumByMobile(user);
			Message message = new Message();
			if (obj > 0) {
				message.setCode(1);
			} else {
				request.getSession().setAttribute("mobile", mobile);
				message.setCode(0);
			}
			UserCert userCert = new UserCert();
			userCert.setIp(request.getRemoteAddr());
			userCert.setMobile(mobile);
			request.getSession().setAttribute("mobile", mobile);
			return message;
		} catch (GeneralSecurityException e) {
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.REAL_NAME_AUTHENTICATION_FAILED);
		}
	}

	@Override
	@Transactional
	public Message idCertificat(HttpServletRequest request,String idnumber, String name, User user) {
		try {
			//判断认证用户是否满18周岁
			boolean isLega = this.isLegalAge(idnumber);
			if(isLega != true){
				return new Message(160037, "该用户未满18周岁不能认证");
			}
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			logger.info("用户实名认证："+cipher.decrypt(user.getMobile()));
			
			
			User us=new User();
			us.setIdnumber(cipher.encrypt(idnumber));
			User queryUser = userService.queryUserByParams(us);
			if(queryUser!=null){
				return new Message(160037, "该身份证在左右逢园平台已经做过实名认证");
			}
			ApplicationBean appen = new ApplicationBean();
			String orderId = appen.orderId();
			Map<String, String> map = new HashMap<String, String>();
			String uuid = user.getId();
			uuid = uuid.replace("-", "");
			map.put("mer_cust_id", uuid);
			map.put("mer_cust_name", name);
			map.put("identity_type", "IDENTITY_CARD");
			map.put("identity_code", idnumber);
			map.put("order_id", orderId);
			
			map.put("mobile_id", cipher.decrypt(user.getMobile()));
			UmpaySignature umpaySignature = new UmpaySignature(
					"mer_register_person", map);
			Map<String, String> entity = umpaySignature.getSignature();
			if ("0000".equals(entity.get("ret_code"))) {
				UmpAccount umpAccount = new UmpAccount();
				umpAccount.setAccountId(entity.get("account_id"));
				umpAccount.setAccountName(entity.get("user_id"));
				umpAccount.setRegDate(entity.get("reg_date"));
				umpAccount.setTimeCreate(new Date());
				umpAccount.setUserId(user.getId());
				umpAccountService.addUmpAccount(umpAccount);
				
				UserFund userFound = new UserFund();
				userFound.setUserId(user.getId());
				userFound.setTimeCreated(new Date());
				userFound.setTimeLastUpdated(new Date());
				UserFund res = userFoundService.byUserID(userFound);
				if(res==null){
					userFoundService.addUserFund(userFound);
				}
				
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
				User updateUser = new User();
				updateUser.setId(user.getId());
				updateUser.setIdnumber(cipher.encrypt(idnumber));
				updateUser.setName(name);
				updateUser.setIdauthenticated(true);
				updateUser.setBirthDate(sdf.parse(idnumber.substring(6, 14)));
				userService.updateUser(updateUser);
				
				UmpAgreement umpAgreement = new UmpAgreement();
				umpAgreement.setAccountname(umpAccount.getAccountName());
				umpAgreement.setCardno(umpAccount.getAccountId());
				umpAgreement.setTimecreated(new Date());
				umpAgreement.setTimelastupdated(new Date());
				umpAgreement.setUserid(user.getId());
				umpAgreementService.insertAgreement(umpAgreement);
				User use = userService.queryById(user.getId());
				request.getSession().setAttribute("zycfLoginUser", use);
				return new Message(1, "实名认证成功");
				// 手机号已经被绑定
			} else if ("00160037".equals(entity.get("ret_code"))) {

				return new Message(160037, "该手机不可重复实名认证");
			} else {
				throw new UException(SystemEnum.UNKNOW_EXCEPTION);
			}
		} catch (ReqDataException | RetDataException | GeneralSecurityException | ParseException e) {
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.REAL_NAME_AUTHENTICATION_FAILED);
		}
	}

	private boolean isLegalAge(String idnumber) {
		boolean isLegal = true;
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		Date date = new Date();
		int newYear = Integer.parseInt(df.format(date).toString());
		int idNumberYear = 0;
		if(idnumber.length()>15){
			String  idm = idnumber.substring(6, 10);
			idNumberYear = Integer.parseInt(idm);
		}else{
			idNumberYear = Integer.parseInt("19"+idnumber.substring(6,8));
		}
        int age = 	newYear-idNumberYear;
		if(age < 18){
			isLegal = false;
		}
		return isLegal;
		
	}

	@Override
	public Message login(HttpServletRequest req, LoginVO loginVO) {
		Message message = new Message();
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		Pattern pattern = Pattern.compile("[0-9]*");

		String loginName = (String) req.getSession().getAttribute("loginName") == null ? null : (String)req.getSession().getAttribute("loginName");
		int loginCount = ((Integer)req.getSession().getAttribute("loginCount")== null ? 0 : (Integer)req.getSession().getAttribute("loginCount") );
		if(loginName != null && loginVO.getLoginname()!=null && loginName.equals(loginVO.getLoginname()) ){
			loginCount++;
		}else{
			loginCount=1;
		}

		User user;
		try {
			if (pattern.matcher(loginVO.getLoginname()).matches()) {
				user = userService.getUserByMobile(cipher.encrypt(loginVO.getLoginname()));
			} else {
				user = userService.getUserByLoginName(loginVO.getLoginname());
			}
			if (user != null) {
				if(user.getEnabled() == false){
					return new Message(20001, "用户账户已被锁定");
				}
				if (user.getAllowTime() == null || FormatUtils.timeDiff(new Date(), user.getAllowTime())) {
					String salt = user.getSalt();
					String password = Password.getPassphrase(salt, loginVO.getPassphrase());
					if (user.getPassphrase().equals(password)) {
						loginVO.setLoginCounter(0);
						user.setPassphrase(null);
						user.setSalt(null);
						req.getSession().setAttribute("zycfLoginUser", user);

						User loginUser = new User();
						loginUser.setLastlogindate(new Date());
						loginUser.setId(user.getId());
						userService.updateUser(loginUser);
						return new Message(1, "登陆成功");
					} else {
						req.getSession().setAttribute("loginName", loginVO.getLoginname());
						req.getSession().setAttribute("loginCount", loginCount);
						if (loginCount == 5) {
							DateTime dt = new DateTime(new Date());
							user.setAllowTime(dt.plusMinutes(30).toDate());
							userService.updateUser(user);
							return new Message(1004, "连续5次输入错误，帐号已被锁定！");
						} else {
							return new Message(1001, "用户名和密码不匹配！还有"+(5-loginCount)+"次机会");
						}
					}
				}
			} else {
				return new Message(1002, "用户名不存在");
			}
		} catch (GeneralSecurityException e) {
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.LOGIN_ERROR);
		}
		return new Message(1005, "用户名账号已被锁定，请稍后重试！");
	}

	@Override
	public LoginUser loginUser(User user) {
		String userName;
		try {
			if (user != null) {
				if (!"".equals(user.getName()) && user.getName() != null) {
					userName = user.getName();
				} else {
					String mobile = user.getMobile();
					DESTextCipher cipher = DESTextCipher.getDesTextCipher();
					mobile = cipher.decrypt(mobile);
					userName = mobile.substring(0,
							mobile.length() - (mobile.substring(3)).length())
							+ "****" + mobile.substring(7);
				}
				LoginUser lu = new LoginUser();
				lu.setUserName(userName);
				return lu;
			}
		} catch (GeneralSecurityException e) {
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.USER_NOLOGIN);
		}
		throw new UException(SystemEnum.USER_NOLOGIN);
	}

	@Override
	public Message updatePassword(HttpServletRequest request,
			String oldPassword, String password) {
		Message message = new Message();
		try {
			if (!this.checkPassword(request, oldPassword)) {
				message.setCode(2);
				message.setMessage("原密码错误");
				return message;
			}
			String salt = Password.getSalt(null);
			String userPassword = Password.getPassphrase(salt, password);
			User user = this.updateUser(request);
			if (!this.checkPassword(request, oldPassword)) {
				message.setCode(2);
				message.setMessage("原密码错误");
				return message;
			}
			// user.setMobile(mobile);
			user.setSalt(salt);
			// DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			// mobile = cipher.decrypt(mobile);
			user.setPassphrase(userPassword);
			int num = userService.updateUser(user);
			if (num > 0) {
				message.setCode(0);
				message.setMessage("修改成功");
			} else {
				message.setCode(1);
				message.setMessage("修改失败");
			}

		} catch (Exception e) {
			logger.info(e,e.fillInStackTrace());
			throw new UException(e);
		}
		return message;
	}
	

	@Override
	public String appAgreement(User user, int[] agreements) {
		StringBuffer protocals = new StringBuffer();
		try {
			UmpAccount umpAccount = new UmpAccount();
			umpAccount.setUserId(user.getId());
			UmpAccount umpAccountTrue = umpAccountService
					.byUserIdUmpAccount(umpAccount);
			if (umpAccountTrue == null) {
				throw new UException(SystemEnum.UN_AUTHENTICATION);
			}
			UmpAgreement umpAgreement = new UmpAgreement();
			
			
			FundAccount fundAccount  = new FundAccount();
			fundAccount.setUserId(user.getId());
			fundAccount = fundAccountService.getByUserAndAccount(fundAccount);
			
			if(fundAccount == null )
				return "请先绑定银行卡";
//			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			
			umpAgreement.setCardno(fundAccount.getAccount());
			umpAgreement.setAccountname(umpAccountTrue.getAccountName());
			umpAgreement.setTimecreated(new Date());
			umpAgreement.setTimelastupdated(new Date());
			umpAgreement.setUserid(user.getId());
			
			UmpAgreement result = umpAgreementService.byUserUmpAgreement(umpAgreement);
			//判断是否已经开通其他协议
			if(result==null){
				umpAgreementService.insertAgreement(umpAgreement);
				result=umpAgreement;
			}
			for (int i : agreements) {
				if(i==1){
					agreements=new int[]{1,2};
					break;
				}
			}
			
			if (agreements != null) {
				boolean start = false;
				for (int agrementInt : agreements) {
					if (start) {
						protocals.append("|");
					} else {
						start = true;
					}
					switch (agrementInt) {
					case 1:
						if(result.getDebit())break;
						result.setDebit(true);
						protocals.append("ZKJP0700");
						break;// 借记卡快捷协议
					case 2:
						if(result.getInstant())break;
						result.setInstant(true);
						protocals.append("ZCZP0800");
						break;// 快捷支付协议
					case 3:
						if(result.getInvest())break;
						result.setInvest(true);
						protocals.append("ZTBB0G00");
						break;// 无密投资协议
					case 4:
						if(result.getRepay())break;
						result.setRepay(true);
						protocals.append("ZHKB0H01");
						break;// 无密还款协议
					}

				}
			}
			if(protocals.toString().startsWith("|")){
				protocals.delete(0, 1);
			}
			if(protocals.toString().endsWith("|")){
				protocals.delete(protocals.lastIndexOf("|"), protocals.length());
			}
			Map<String, String> agreement = new HashMap<>();
			agreement.put("user_id", umpAccountTrue.getAccountName());
			agreement.put("account_id", umpAccountTrue.getAccountId());
			agreement.put("user_bind_agreement_list", protocals.toString());
			Map<String, Object> sysMap = SystemPro.getProperties();
			String ret_url = "app:agreement";
			
			String notify_url = (String) ((Map) sysMap.get("NOTIFYURL"))
					.get("AGREEMENT");
			agreement.put("ret_url", ret_url);
			agreement.put("notify_url", notify_url);

			CacheMemory.getCacheMemory().put("umpAgreement" + umpAccountTrue.getAccountName(),
					result);
			UmpaySignature umpaySignature = new UmpaySignature(
					"ptp_mer_bind_agreement", agreement);
			return umpaySignature.getSignatureStrng();
		} catch (ReqDataException e) {
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.AUTHENTICATION_FAIL);
		}catch(Exception e){
			logger.info(e,e.fillInStackTrace());
			return null;
		}
	}

	
	
	@Override
	public String agreement(User user, int[] agreements) {
		StringBuffer protocals = new StringBuffer();
		try {
			UmpAccount umpAccount = new UmpAccount();
			umpAccount.setUserId(user.getId());
			UmpAccount umpAccountTrue = umpAccountService
					.byUserIdUmpAccount(umpAccount);
			if (umpAccountTrue == null) {
				throw new UException(SystemEnum.UN_AUTHENTICATION);
			}
			UmpAgreement umpAgreement = new UmpAgreement();
			
			FundAccount fundAccount  = new FundAccount();
			fundAccount.setUserId(user.getId());
			fundAccount = fundAccountService.getByUserAndAccount(fundAccount);
			
			if(fundAccount == null )
				return "请先绑定银行卡";
			
			umpAgreement.setCardno(fundAccount.getAccount());
			umpAgreement.setAccountname(umpAccountTrue.getAccountName());
			umpAgreement.setTimecreated(new Date());
			umpAgreement.setTimelastupdated(new Date());
			umpAgreement.setUserid(user.getId());
			
			UmpAgreement result = umpAgreementService.byUserUmpAgreement(umpAgreement);
			//判断是否已经开通其他协议
			if(result==null){
				umpAgreementService.insertAgreement(umpAgreement);
				result=umpAgreement;
			}
			
			if (agreements != null) {
				boolean start = false;
				for (int agrementInt : agreements) {
					if (start) {
						protocals.append("|");
					} else {
						start = true;
					}
					switch (agrementInt) {
					case 1:
						if(result.getDebit())break;
						result.setDebit(true);
						protocals.append("ZKJP0700");
						break;// 借记卡快捷协议
					case 2:
						if(result.getInstant())break;
						result.setInstant(true);
						protocals.append("ZCZP0800");
						break;// 快捷支付协议
					case 3:
						if(result.getInvest())break;
						result.setInvest(true);
						protocals.append("ZTBB0G00");
						break;// 无密投资协议
					case 4:
						if(result.getRepay())break;
						result.setRepay(true);
						protocals.append("ZHKB0H01");
						break;// 无密还款协议
					}

				}
			}
			if(protocals.toString().startsWith("|")){
				protocals.delete(0, 1);
			}
			if(protocals.toString().endsWith("|")){
				protocals.delete(protocals.lastIndexOf("|"), protocals.length());
			}
			Map<String, String> agreement = new HashMap<>();
			agreement.put("user_id", umpAccountTrue.getAccountName());
			agreement.put("account_id", umpAccountTrue.getAccountId());
			agreement.put("user_bind_agreement_list", protocals.toString());
			Map<String, Object> sysMap = SystemPro.getProperties();
			String zycfIp = (String) sysMap.get("ZYCFMARKET_IP");
			String notify_url = (String) ((Map) sysMap.get("NOTIFYURL"))
					.get("AGREEMENT");
			agreement.put("ret_url", zycfIp+"/html/umpreturnMsg/umpReturnMsg.html");
			agreement.put("notify_url", notify_url);

			CacheMemory.getCacheMemory().put("umpAgreement" + umpAccountTrue.getAccountName(),
					result);
			UmpaySignature umpaySignature = new UmpaySignature(
					"ptp_mer_bind_agreement", agreement);
			return umpaySignature.getSignatureStrng();
		} catch (ReqDataException e) {
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.AUTHENTICATION_FAIL);
		}catch(Exception e){
			logger.info(e,e.fillInStackTrace());
			return null;
		}
	}
	
	
	@Override
	public Message cancleAgreement(User user, int[] agreements,String sourceV) {
		Message message=new Message();
		StringBuffer protocals = new StringBuffer();
		try {
			if(user==null){
				message.setCode(0);
				message.setMessage("用户未登录");
				return message;
			}
			UmpAccount umpAccount = new UmpAccount();
			umpAccount.setUserId(user.getId());
			UmpAccount umpAccountTrue = umpAccountService
					.byUserIdUmpAccount(umpAccount);
			if (umpAccountTrue == null) {
				message.setCode(0);
				message.setMessage("用户未在联动优势开户");
				return message;
			}
			Map<String, Object> map = userService.queryByParams(user);
			if(map.containsKey("idauthenticated")&&(boolean)map.get("idauthenticated")==false){
				message.setCode(0);
				message.setMessage("用户未实名认证");
				return message;
			}
			
			
			UmpAgreement umpAgreement = new UmpAgreement();
			umpAgreement.setUserid(user.getId());
			UmpAgreement result = umpAgreementService.byUserUmpAgreement(umpAgreement);
			if (agreements != null) {
				boolean start = false;
				for (int agrementInt : agreements) {
					if (start) {
						protocals.append("|");
					} else {
						start = true;
					}
					switch (agrementInt) {
					case 1:
						result.setDebit(false);
						protocals.append("ZKJP0700");
						break;// 借记卡快捷协议
					case 2:
						result.setInstant(false);
						protocals.append("ZCZP0800");
						break;// 快捷支付协议
					case 3:
						result.setInvest(false);
						protocals.append("ZTBB0G00");
						break;// 无密投资协议
					case 4:
						result.setRepay(false);
						protocals.append("ZHKB0H01");
						break;// 无密还款协议
					}

				}
			}
			if(protocals.toString().contains("ZKJP0700")){
				BigDecimal availableAmount=(BigDecimal)map.get("availableAmount");
				BigDecimal frozenAmount=(BigDecimal)map.get("frozenAmount");
				if(map.containsKey("availableAmount")&&availableAmount.doubleValue()!=0){
					message.setCode(0);
					message.setMessage("用户账户余额不为0，不允许解约协议");
					return message;
				}
				if(map.containsKey("frozenAmount")&&frozenAmount.doubleValue()!=0){
					message.setCode(0);
					message.setMessage("账户存在在途资金，不允许解约协议");
					return message;
				}
				Invest invest=new Invest();
				invest.setUserid(user.getId());
				List<Map<String, Object>> res = investService.queryByInvest(invest);
				if(res!=null&&res.size()!=0){
					message.setCode(0);
					message.setMessage("账户存在在途资金，不允许解约协议");
					return message;
				}
				
			}
			Map<String, String> agreement = new HashMap<>();
			agreement.put("user_id", umpAccountTrue.getAccountName());
			agreement.put("account_id", umpAccountTrue.getAccountId());
			agreement.put("user_unbind_agreement_list", protocals.toString());
			Map<String, Object> sysMap = SystemPro.getProperties();
			String zycfIp = (String) sysMap.get("ZYCFMARKET_IP");
			String notify_url = (String) ((Map) sysMap.get("NOTIFYURL"))
					.get("CANCLEAGREEMENT");
			agreement.put("ret_url", zycfIp+"/html/umpreturnMsg/umpReturnMsg.html");
			agreement.put("notify_url", notify_url);
			if(sourceV!=null&&!"".equals(sourceV)){
				agreement.put("sourceV", sourceV);
			}
			CacheMemory.getCacheMemory().put("umpAgreement" + umpAccountTrue.getAccountName(),
					result);
			UmpaySignature umpaySignature = new UmpaySignature(
					"mer_unbind_agreement", agreement);
			message.setCode(1);
			message.setMessage(umpaySignature.getSignatureStrng());
			return message;
		} catch (ReqDataException e) {
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.AUTHENTICATION_FAIL);
		}catch(Exception e){
			logger.info(e,e.fillInStackTrace());
			return null;
		}
	}

	@Override
	public String agreementCallBack(CallBackUMP callBackUMP) {
		Map<String, String> agreement = new HashMap<>();
		agreement.put("user_id", callBackUMP.getUser_id());
		if ("0000".equals(callBackUMP.getRet_code())) {
			UmpAgreement umpAgreement = (UmpAgreement) CacheMemory
					.getCacheMemory().get(
							"umpAgreement" + callBackUMP.getUser_id());
			if(umpAgreement == null)
				return null;
			umpAgreementService.updateAgreement(umpAgreement);
		}
		CacheMemory.getCacheMemory().remove("umpAgreement" + callBackUMP.getUser_id());
		agreement.put("ret_code", callBackUMP.getRet_code());
		UmpaySignature umpaySignature = new UmpaySignature(null, agreement);
		try {
			return umpaySignature.callBackSignature();
		} catch (ReqDataException | RetDataException e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return null;
	}
	
	@Override
	public String cancleAgreementCallBack(CallBackUMP callBackUMP) {
		Map<String, String> agreement = new HashMap<>();
		agreement.put("user_id", callBackUMP.getUser_id());
		if ("0000".equals(callBackUMP.getRet_code())) {
			UmpAgreement umpAgreement = (UmpAgreement) CacheMemory
					.getCacheMemory().get(
							"umpAgreement" + callBackUMP.getUser_id());
			if(umpAgreement == null)
				return null;
			umpAgreementService.updateAgreement(umpAgreement);
		}
		CacheMemory.getCacheMemory().remove("umpAgreement" + callBackUMP.getUser_id());
		agreement.put("ret_code", callBackUMP.getRet_code());
		UmpaySignature umpaySignature = new UmpaySignature(null, agreement);
		try {
			return umpaySignature.callBackSignature();
		} catch (ReqDataException | RetDataException e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return null;
	}

	// 设置用户名
	@Override
	public Message updateUserName(HttpServletRequest request, String userName) {
		Message message = new Message();
		User user = this.updateUser(request);

		if (userName == null || "" == userName) {
			message.setCode(3);
			message.setMessage("用户名为空");
			return message;

		}
		User validateUser = new User();
		validateUser.setLoginname(userName);

		validateUser = userService.getUserByLoginName(userName);
		if (validateUser != null) {
			message.setCode(4);
			message.setMessage("该用户名已经注册");
			return message;
		}
		user.setLoginname(userName);
		int num = userService.updateUser(user);
		if (num > 0) {
			message.setCode(0);
			message.setMessage("设置成功");
		} else {
			message.setCode(1);
			message.setMessage("设置失败");
		}
		return message;
	}

	@Override
	public Message updateMail(HttpServletRequest request, String mail) {
		Message message = new Message();
		User user = this.updateUser(request);
		user.setEmail(mail);
		user.setEmailauthenticated(true);
		int num = userService.updateUser(user);
		if (num > 0) {
			User us=(User)request.getSession().getAttribute("zycfLoginUser");
			us.setEmail(mail);
			request.getSession().setAttribute("zycfLoginUser", us);
			message.setCode(0);
			message.setMessage("修改成功");
		} else {
			message.setCode(1);
			message.setMessage("修改失败");
		}
		return message;
	}

	@Override
	public List<Map> selectInvestStatusByUserID(HttpServletRequest req) {
		return investService.selectInvestStatusByUserID((User) req.getSession()
				.getAttribute("zycfLoginUser"));
	}

	/**
	 * 返回用户个人设置信息
	 */
	@Override
	public UserInfoVo userSettings(HttpServletRequest request) {

		User loguser = (User) request.getSession()
				.getAttribute("zycfLoginUser");

		if (loguser == null)
			throw new UException(SystemEnum.USER_NOLOGIN, "未登录");

		UserInfoVo query = new UserInfoVo();
		query.setId(loguser.getId());
		query.setCardauthenticated(loguser.getCardauthenticated());

		if(loguser.getCardauthenticated()==null)
			query.setCardauthenticated(false);
		

		UserInfoVo user = userService.getUserInfo(query);
		User ref = userService.queryById(user.getReferralId());
		// Map<String,String> map = new HashMap<String,String>();
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		try {

			if (user.getIdnumber() != null) {
				String idNumber = "";
				try {
					idNumber = cipher.decrypt(user.getIdnumber());

				} catch (Exception e) {

				}
				idNumber = MStringUtils.decrypt(idNumber, 4, 15, '*');
				user.setIdnumber(idNumber);
			}
			String mobile = user.getMobile();
			if (mobile != null)
				mobile = cipher.decrypt(mobile);
			
			String refMobile = ref.getMobile();
			if(refMobile != null)
				refMobile = cipher.decrypt(refMobile);
			
			refMobile = MStringUtils.decrypt(refMobile, 4, 8, '*');
			
			user.setReferralRealm(refMobile);
			user.setMobile(mobile);

			user.setPassphrase(null);
			user.setSalt(null);

			user.setStrLastLoginDate(FormatUtils.simpleFormat(user
					.getLastlogindate()));

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return user;
	}

	/**
	 * 用户信息修改私有方法
	 * 
	 * @param request
	 * @return
	 */

	private User updateUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		if (user == null)
			throw new UException(SystemEnum.USER_NOLOGIN, "未登录");

		User updUser = new User();
		updUser.setId(user.getId());
		return updUser;
	}

	/**
	 * 用户密码校验
	 * 
	 * @param request
	 * @param password
	 * @return
	 */
	private boolean checkPassword(HttpServletRequest request, String password) {
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		if (user == null)
			throw new UException(SystemEnum.USER_NOLOGIN, "未登录");
		user = userService.queryById(user.getId());
		String old = Password.getPassphrase(user.getSalt(), password);
		if (old.equalsIgnoreCase(user.getPassphrase()))
			return true;
		else
			return false;
	}

	@Override
	public Map appLogin(HttpServletRequest request, UserInfoVo userInfoVo) {

		Map<String, Object> map = new HashMap<String, Object>();

		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		Pattern pattern = Pattern.compile("[0-9]*");
		// int num = loginVO.getLoginCounter();
		// loginVO.setLoginCounter(num++);
		User user;
		try {
			if (pattern.matcher(userInfoVo.getLoginname()).matches()) {
				user = userService.getUserByMobile(cipher.encrypt(userInfoVo
						.getLoginname()));
			} else {
				user = userService
						.getUserByLoginName(userInfoVo.getLoginname());
			}
			if (user != null) {
				if((!user.getEnabled())||(user.getAllowTime()!=null&&!FormatUtils.timeDiff(new Date(), user.getAllowTime()))){
					map.put("code", "9");
					map.put("message", "您的账户已被锁定");
					return map;
				}
				String salt = user.getSalt();
				String password = Password.getPassphrase(salt,
						userInfoVo.getPassphrase());
				if (user.getPassphrase().equals(password)) {
					// loginVO.setLoginCounter(0);
					user.setPassphrase(null);
					user.setSalt(null);
					request.getSession().setAttribute("zycfLoginUser", user);

					User loginUser = new User();
					loginUser.setLastlogindate(new Date());
					loginUser.setId(user.getId());
					userService.updateUser(loginUser);
					// 获取用户个人信息
					userInfoVo.setId(user.getId());
					userInfoVo.setCardauthenticated(user.getCardauthenticated());
					if(userInfoVo.getCardauthenticated()==null)
						userInfoVo.setCardauthenticated(false);
					UserInfoVo userInfo = userService.getUserInfo(userInfoVo);
					userInfo.setSalt(null);
					userInfo.setPassphrase(null);
				
						if (userInfo.getIdnumber() != null) {
							String idNumber = "";
						
								idNumber = cipher.decrypt(user.getIdnumber());

							idNumber = MStringUtils.decrypt(idNumber, 4, 15, '*');
							userInfo.setIdnumber(idNumber);
						}
						String mobile = userInfo.getMobile();
						if (mobile != null)
							mobile = cipher.decrypt(mobile);
						// mobile = MStringUtils.decrypt(mobile, 4, 8, '*');
//						if (userInfo.getAccount() != null) {
//							String account = "";
//							account = cipher.decrypt(userInfo.getAccount());
//							account = MStringUtils.decrypt(account, 4, 8, '*');
//							userInfo.setAccount(account);
//
//						}

						userInfo.setMobile(mobile);

						userInfo.setPassphrase(null);
						userInfo.setSalt(null);

						userInfo.setStrLastLoginDate(FormatUtils.simpleFormat(userInfo
								.getLastlogindate()));
					
					map.put("userInfo", userInfo);
					map.put("code", "1");
					map.put("message", "登录成功");

					return map;
				}
			} else {
				map.put("code", "2");
				map.put("message", "登录失败");

				return map;
			}
		} catch (GeneralSecurityException e) {
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.LOGIN_ERROR);
		}
		map.put("code", "3");
		map.put("message", "密码错误");

		return map;

	}

	@Override
	public Message queryUserByParams(String mobile) {
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		Message message=new Message();
		try {
			User user = userService.getUserByMobile(cipher.encrypt(mobile));
			if(user==null){
				message.setCode(0);
				message.setMessage("用户不存在");
			}
			else{
				message.setCode(1);
				message.setMessage("用户存在");
			}
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		}
		return message;
	}

	@Override
	public List<Map> queryInvestSurveyByUser(HttpServletRequest request) {
		try {
			User user = (User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
			if(user!=null){
				return investService.queryInvesSurvey(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.SERVER_REFUSED);
		}
		return null;
	}

	@Override
	public PagerVO queryInviterInvest(HttpServletRequest request,Page page) {
		PagerVO pagerVo=new PagerVO();
		User user = (User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
		if(user==null){
			throw new UException(SystemEnum.USER_NOLOGIN);
		}
		try {
			page.getParams().put("id", user.getId());
			List<Map> map = userService.queryInviterInvest(page);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			for (Map mp : map) {
				String userName = (String)mp.get("name");
				String name=userName.substring(0,1)+"**";
				mp.replace("name",name);
				Date date = (Date)mp.get("investDate");
				if(date!=null){
					String investDate = sdf.format(date);
					mp.replace("investDate",investDate);
				}
			}
			pagerVo.setData(map);
			int totalPage;
			if(page.getTotalRecord()%page.getPageSize()==0){
				totalPage=page.getTotalRecord()/page.getPageSize();
			}
			else{
				totalPage=(page.getTotalRecord()/page.getPageSize())+1;
			}
			pagerVo.setTotalPage(totalPage);
			pagerVo.setRecordsTotal(page.getTotalRecord());
			pagerVo.setRecordsFiltered(page.getTotalPage());
			return pagerVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		}
	}

	@Override
	public Message resetPasswd(HttpServletRequest request, String passwd) {
		Message message = new Message();
		String mobile =(String) request.getSession().getAttribute("mobile");
	try{
		
		if(mobile==null)
		{
			message.setCode(2);
			message.setMessage("会话已过期");
			return message;
		}
		
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		mobile = cipher.encrypt(mobile);
		
		User user = userService.getUserByMobile(mobile);
		
		String salt = Password.getSalt(null);
		String userPassword = Password.getPassphrase(salt, passwd);
		user.setSalt(salt);
		user.setPassphrase(userPassword);
		int num = userService.updateUser(user);
		if (num > 0) {
			message.setCode(0);
			message.setMessage("修改成功");
		} else {
			message.setCode(1);
			message.setMessage("修改失败");
		}

	} catch (Exception e) {
		logger.info(e,e.fillInStackTrace());
		throw new UException(e);
	}
	return message;
		
	}

	@Override
	public PagerVO queryUserByUserId(HttpServletRequest request, Page page) {
		PagerVO pagerVo=new PagerVO();
		User user = (User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
		if(user==null){
			throw new UException(SystemEnum.USER_NOLOGIN);
		}
		try {
			page.getParams().put("id", user.getId());
			List<Map> map = userService.queryUserByUserId(page);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();

			for (Map mp : map) {
				String mobile = (String)mp.get("MOBILE");
//				if(mp.containsKey("NAME")){
//					String name = (String)mp.get("NAME");
//					name=name.substring(0,1)+ "**";
//					mp.replace("NAME",name);
//				}
				
				mobile = cipher.decrypt(mobile);
//				mobile=mobile.substring(0,
//						mobile.length() - (mobile.substring(3)).length())
//						+ "****" + mobile.substring(7);
				 
				Date lastLoginDate = (Date)mp.get("LASTLOGINDATE");
				Date registerDate = (Date)mp.get("REGISTERDATE");
				String registerDates = sdf.format(registerDate);
				mp.replace("REGISTERDATE",registerDates);
				mp.replace("MOBILE",mobile);
				
				if(lastLoginDate ==null){
					mp.replace("LASTLOGINDATE",registerDates);
				}else{
					String lastLoginDates = sdf.format(lastLoginDate);
					mp.replace("LASTLOGINDATE",lastLoginDates);
				}
			}
			pagerVo.setData(map);
			int totalPage;
			if(page.getTotalRecord()%page.getPageSize()==0){
				totalPage=page.getTotalRecord()/page.getPageSize();
			}
			else{
				totalPage=(page.getTotalRecord()/page.getPageSize())+1;
			}
			pagerVo.setTotalPage(totalPage);
			pagerVo.setRecordsTotal(page.getTotalRecord());
			pagerVo.setRecordsFiltered(page.getTotalPage());
			return pagerVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		}
		}
}
