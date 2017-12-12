package com.zhongyang.java.biz.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.UserFundBiz;
import com.zhongyang.java.dao.UmpAccountDao;
import com.zhongyang.java.dao.UserFundDao;
import com.zhongyang.java.pojo.FundAccount;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.service.FundAccountService;
import com.zhongyang.java.service.UserFundService;
import com.zhongyang.java.system.BackUitl;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.config.ApplicationBean;
import com.zhongyang.java.vo.UserFundVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月1日 下午5:27:27
* 类说明
*/
@Service
public class UserFundImpl implements UserFundBiz {
    
	private static Logger logger=Logger.getLogger(UserFundImpl.class);
	
	@Autowired
	UserFundService userFundService;
	
	@Autowired
	UserFundDao userFundDao;
	
	@Autowired
	UmpAccountDao umpAccountDao;
	
	
	@Autowired
	FundAccountService fundAccountService;

	/*
	 * 
	 */
	@Override
	public UserFundVo byUserId(UserFundVo userFundVo)  throws Exception{
		if(userFundVo.getUserid()==null){
          throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
		}else{
			try {
				ApplicationBean appben = new  ApplicationBean();
				UserFund userFund =new UserFund();
				userFund.setUserId(userFundVo.getUserid());
				userFund = userFundService.byUserID(userFund);
				if(userFund != null){
					Map<String, Object> sysMap = SystemPro.getProperties();
					userFundVo.setAvailableAmount(userFund.getAvailableAmount());
					userFundVo.setDepositAmount(userFund.getDepositAmount());
					userFundVo.setDueInAmount(userFund.getDueInAmount());
					userFundVo.setDueOutAmount(userFund.getFrozenAmount());
					userFundVo.setStatus(userFund.getStatus());
					userFundVo.setTimecreated(userFund.getTimeCreated());
					userFundVo.setTimelastupdated(userFund.getTimeLastUpdated());
					userFundVo.setUserid(userFund.getUserId());
					userFundVo.setWithdrawAmount(userFund.getWithdrawAmount());
					userFundVo.setOrderId(appben.orderId());//充值生成的订单号
					String minrecharge = (String) sysMap.get("MINRECHARGE");//最低充值额度
					userFundVo.setMinrecharge(minrecharge);
				}else{
					userFundVo.setOrderId(appben.orderId());;
				}
			} catch (Exception e) {
				logger.info(e,e.fillInStackTrace());
				System.out.println(e.getMessage()+"");
			}
			return userFundVo;	
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhongyang.java.biz.UserFundBiz#addUserFund(com.zhongyang.java.pojo.UserFund)
	 * 用户资金添加
	 */
	public void addUserFund(UserFund UserFund) {
		
		
	}

    /*
     * (non-Javadoc)
     * @see com.zhongyang.java.biz.UserFundBiz#ByUserIdWithdraw(com.zhongyang.java.vo.UserFundVo)
     *  根据UserId查询用户资金信息
     */
	public UserFundVo byUserIdWithdraw(UserFundVo userFundVo) throws Exception{
		if(userFundVo.getUserid()==null){
	          throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
			}else{
				try {
					ApplicationBean appben = new  ApplicationBean();
					UserFund userFund =new UserFund();
					userFund.setUserId(userFundVo.getUserid());
					UserFund userFundObj = userFundService.byUserID(userFund);
					FundAccount fundAccount =new FundAccount();
					fundAccount.setUserId(userFund.getUserId());
					
					FundAccount fundAccountObj = this.getByUserAndAccount(fundAccount);
					if(fundAccountObj != null){
						Map<String, Object> sysMap = SystemPro.getProperties();
						userFundVo.setAvailableAmount(userFundObj.getAvailableAmount());
						userFundVo.setDepositAmount(userFundObj.getDepositAmount());
						userFundVo.setDueInAmount(userFundObj.getDueInAmount());
						userFundVo.setDueOutAmount(userFundObj.getFrozenAmount());
						userFundVo.setStatus(userFundObj.getStatus());
						userFundVo.setTimecreated(userFundObj.getTimeCreated());
						userFundVo.setTimelastupdated(userFundObj.getTimeLastUpdated());
						userFundVo.setUserid(userFundObj.getUserId());
						userFundVo.setWithdrawAmount(userFundObj.getWithdrawAmount());
						userFundVo.setOrderId(appben.orderId());//充值生成的订单号
						userFundVo.setBank(fundAccountObj.getBank());//
						String withdrawfee = (String) sysMap.get("WITHDRAWFEE");//获得提现费率
/*						DESTextCipher cipher = DESTextCipher.getDesTextCipher();
						String cc = cipher.decrypt(fundAccountObj.getAccount());*/
						userFundVo.setAccount(fundAccountObj.getAccount());
						userFundVo.setBankCoade(fundAccountObj.getBankmobile());
						userFundVo.setWithdrawRates(withdrawfee);
					}else{
						userFundVo.setOrderId(appben.orderId());
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.info(e,e.fillInStackTrace());
				}
				return userFundVo;
			}
	}

	private FundAccount getByUserAndAccount(FundAccount fundAccount) {
		FundAccount fundAccountBack = new FundAccount();
		try {
			FundAccount fundAccountobj = fundAccountService.getByUserAndAccount(fundAccount);
			if(fundAccountobj == null){
				fundAccountBack.setAccount("");
				fundAccountBack.setBank("");
			}else{
				String backc = fundAccountobj.getBank();
				BackUitl backUitl = new BackUitl();
				String cc = backUitl.createBackUitl(backc);
				fundAccountBack.setAccount(fundAccountobj.getAccount());
				fundAccountBack.setBank(cc);
				fundAccountBack.setBankmobile(backc);//存放了银行编号
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return fundAccountBack;
		
		
	}

	@Override
	public UserFundVo queryByUseId(User user) {
		try {
			UserFundVo ufv=new UserFundVo();
			Message message=new Message();
			if(user==null){
				message.setCode(0);
				message.setMessage("没有登录");
				ufv.setMessage(message);
				return ufv;
			}
			if(!user.getIdauthenticated()){
				message.setCode(1);
				message.setMessage("没有身份认证");
				ufv.setMessage(message);
				return ufv;
			}
			message.setCode(2);
			message.setMessage("身份认证通过");
			ufv.setMessage(message);
			UserFund userFund=new UserFund();
			userFund.setUserId(user.getId());
			UserFund userFundObj = userFundService.byUserID(userFund);
			if(userFundObj!=null){
				ufv.setAvailableAmount(userFundObj.getAvailableAmount());
				ufv.setUserid(userFundObj.getUserId());
			}
			return ufv;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, e.getMessage());
		}
	}

	public UserFund byUserFundId(String id) {
		UserFund userFund = new UserFund();
		try {
			userFund =  userFundService.byUserFundId(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return userFund;
	}

	
	public UserFundVo mobileUserFundRecharge(UserFundVo userFundVo) {
	    
		try {
			Map<String, Object> sysMap = SystemPro.getProperties();
		    String rechargeRates = (String) sysMap.get("RECHARGERATES");//获得充值费率
		    String minrecharge = (String) sysMap.get("MINRECHARGE");//获得充值费率
		    FundAccount fundAccount = new FundAccount();
		    fundAccount.setUserId(userFundVo.getUserid());
		    fundAccount = this.getByUserAndAccount(fundAccount);
		    if(null !=fundAccount){
		    	userFundVo.setBankName(fundAccount.getBank());
		    	Map<String, Object> sysMaps = (Map<String, Object>) sysMap.get("BANKLIST");
			    String dRates = (String) sysMaps.get(fundAccount.getBankmobile());//限额
				String[] names = dRates.split("\\,");
				userFundVo.setTimeLimit(names[0]);//次限额
				userFundVo.setDailyLimit(names[1]);//日限额
//				DESTextCipher cipher = DESTextCipher.getDesTextCipher();
//				String cc = cipher.decrypt(fundAccount.getAccount());
//				userFundVo.setBankNum(cc.substring(0, 4)+"******"+cc.substring(cc.length()-4, cc.length()));
				userFundVo.setBankNum(fundAccount.getAccount());
				userFundVo.setBankCoade(fundAccount.getBankmobile());
				userFundVo.setRechargeRates(rechargeRates);
				userFundVo.setMinrecharge(minrecharge);
		    }
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return userFundVo;
	}
   
}
