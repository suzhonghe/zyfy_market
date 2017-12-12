package com.zhongyang.java.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.FundAccountBiz;
import com.zhongyang.java.pojo.FundAccount;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.service.FundAccountService;
import com.zhongyang.java.service.LoanRepaymentService;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.system.SortObj;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.vo.BankInfoVo;
import com.zhongyang.java.vo.loan.LoanRepaymentVo;
import com.zhongyang.java.vo.loan.LoanVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月4日 上午11:22:28
* 类说明
*/
@Service
public class FundAccountBizImpl implements FundAccountBiz{
	
	private static Logger logger=Logger.getLogger(FundAccountBizImpl.class);
	
    @Autowired
    FundAccountService fundAccountService;
    
    @Autowired
    LoanService loanService;
    
    @Autowired
    LoanRepaymentService loanRepaymentService;
    
	@Override
	public FundAccount getByUserAndAccount(FundAccount fundAccount) throws Exception {
		return fundAccountService.getByUserAndAccount(fundAccount);
	}

	@Override
	public FundAccount getFundAccountById(FundAccount fundAccount) {
		return fundAccountService.getFundAccountById(fundAccount);
	}

	@Override
	public List<LoanVo> byUserIdLoanVo(LoanVo loanVoc) {
		List<LoanVo> loanVolist = new ArrayList<>();
		List<Loan> LoanVolist = new ArrayList<>();
		try {
			Loan loan = new Loan();
			loan.setLoanUserId(loanVoc.getLoanUserId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if("ALLSTATUS".equals(loanVoc.getStatus().toString())){
            	if(null !=loanVoc.getEndTimes() || null !=loanVoc.getStartTimes()){
            		if("".equals(loanVoc.getStartTimes())){
            			loanVoc.setEndTime(null);
    					loanVoc.setStartTime(null);
            		}else{
            			Date dates = sdf.parse(loanVoc.getEndTimes());
                		Date datee = sdf.parse(loanVoc.getStartTimes());
    					loanVoc.setEndTime(dates);
    					loanVoc.setStartTime(datee);
            		}
				}
            	LoanVolist = loanService.byUserIdLoanVo(loanVoc);
			}else{
				if("".equals(loanVoc.getEndTimes()) || null ==loanVoc.getStartTimes()){
            		loanVoc.setEndTime(null);
					loanVoc.setStartTime(null);
				}else{
					Date dates = sdf.parse(loanVoc.getEndTimes());
            		Date datee = sdf.parse(loanVoc.getStartTimes());
            		loanVoc.setEndTime(dates);
					loanVoc.setStartTime(datee);
				}
				LoanVolist = loanService.byUserIdAndStatusLoanVo(loanVoc);
			}
			if(LoanVolist.size() > 0){
				for(int i=0;i<LoanVolist.size();i++){
					LoanVo loanVo = new LoanVo();
					loanVo.setId(LoanVolist.get(i).getId());
					loanVo.setTitle(LoanVolist.get(i).getTitle());
					loanVo.setAmount(LoanVolist.get(i).getAmount());
					loanVo.setRate(LoanVolist.get(i).getRate());
					loanVo.setAddRate(LoanVolist.get(i).getAddRate());
					loanVo.setLoanTime(LoanVolist.get(i).getLoanTime());
					loanVo.setMonths(LoanVolist.get(i).getMonths());
					loanVo.setMethod(LoanVolist.get(i).getMethod().getKey());
					if("SETTLED".equals(LoanVolist.get(i).getStatus().toString())){
						loanVo.setStrStatus("还款中");
					}else if("OPENED".equals(LoanVolist.get(i).getStatus().toString())){
						loanVo.setStrStatus("投资中");
					}else if("FAILED".equals(LoanVolist.get(i).getStatus().toString())){
						loanVo.setStrStatus("已流标");
					}else if("CLEARED".equals(LoanVolist.get(i).getStatus().toString())){
						loanVo.setStrStatus("已还请");
					}else{
						loanVo.setStrStatus("已满标");;
					}
					loanVolist.add(loanVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return loanVolist;
	}

	@Override
	public List<LoanRepaymentVo> loanRepaymentPlan(LoanVo loanVoc) {
		List<LoanRepaymentVo> LoanVolist = new ArrayList<>();
		List<LoanRepaymentVo> loanRepaymentVoList = new ArrayList<LoanRepaymentVo>();
		LoanRepaymentVo loanRepaymentVo = new LoanRepaymentVo();
		loanRepaymentVo.setLoanUserId(loanVoc.getLoanUserId());
		try {
//			LoanVolist = loanService.byUserIdLoanVo(loanVoc);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if("ALLSTATUS".equals(loanVoc.getStatus().toString())){
				if("".equals(loanVoc.getEndTimes()) || null ==loanVoc.getStartTimes()){
            		loanRepaymentVo.setEndTime(null);
            		loanRepaymentVo.setStartTime(null);
				}else{
					Date dates = sdf.parse(loanVoc.getEndTimes());
            		Date datee = sdf.parse(loanVoc.getStartTimes());
            		loanRepaymentVo.setEndTime(dates);
            		loanRepaymentVo.setStartTime(datee);
				}
            	LoanVolist = loanRepaymentService.getLoanRepayment(loanRepaymentVo);
			}else{
				if("".equals(loanVoc.getEndTimes()) || null == loanVoc.getStartTimes()){
					loanRepaymentVo.setEndTime(null);
					loanRepaymentVo.setStartTime(null);
				}else{
					Date dates = sdf.parse(loanVoc.getEndTimes());
            		Date datee = sdf.parse(loanVoc.getStartTimes());
            		loanRepaymentVo.setEndTime(dates);
            		loanRepaymentVo.setStartTime(datee);
				}
				if("UNDUE".equals(loanVoc.getStatus().toString())){
					loanRepaymentVo.setStatus("UNDUE','OVERDUE");
				}
				if("REPAYED".equals(loanVoc.getStatus().toString())){
					loanRepaymentVo.setStatus("PREPAY','REPAYED");
				}
				LoanVolist = loanRepaymentService.getLoanRepaymentAndStatusVo(loanRepaymentVo);
			}
			if(LoanVolist.size() > 0){
				for(int i=0;i<LoanVolist.size();i++){
						int k=0;
						
							LoanRepaymentVo loanRepaymentVoObj = new LoanRepaymentVo();
							loanRepaymentVoObj.setTitle(LoanVolist.get(i).getTitle());
							if("UNDUE".equals(LoanVolist.get(i).getStatus().toString()) || "OVERDUE".equals(LoanVolist.get(i).getStatus().toString())){
								loanRepaymentVoObj.setStatus("未还");
							}else{
								loanRepaymentVoObj.setStatus("已还");
							}
							loanRepaymentVoObj.setAmountinterest(LoanVolist.get(i).getAmountinterest());//利息
							loanRepaymentVoObj.setAmountprincipal(LoanVolist.get(i).getAmountprincipal());//本金
							String strDate = LoanVolist.get(i).getDuedate();
							loanRepaymentVo.setDuedate(strDate.substring(0,10));
							loanRepaymentVoObj.setToTleAmount(LoanVolist.get(i).getAmountinterest().add(LoanVolist.get(i).getAmountprincipal()));
							String date = LoanVolist.get(i).getDuedate();
							String ss = (String) date.subSequence(0, 10);
							loanRepaymentVoObj.setDuedate(ss);
							loanRepaymentVoList.add(loanRepaymentVoObj);
							SortObj sort = new SortObj();
							//Collections.sort(loanRepaymentVoList,sort);

					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return loanRepaymentVoList;
	}

	/*private List<LoanRepayment> getLoanRepaymentAndStatusVo(LoanRepaymentVo loanRepaymentVos) {
		List<LoanRepayment> loanRepayment = new ArrayList<LoanRepayment>();
		try {
			loanRepayment = loanRepaymentService.getLoanRepaymentAndStatusVo(loanRepaymentVos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loanRepayment;
	}

	private List<LoanRepayment> getLoanRepaymentVo(LoanRepaymentVo loanRepaymentVo) {
		List<LoanRepayment> loanRepayment = new ArrayList<LoanRepayment>();
		try {
			loanRepayment = loanRepaymentService.getLoanRepayment(loanRepaymentVo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return loanRepayment;
		
	}*/

	@Override
	public List<BankInfoVo> getUserBankInfo() {
		
		List<BankInfoVo> bkf = new ArrayList<BankInfoVo>();
		try {
			Map<String, Object> sysMap = SystemPro.getProperties();
			Map<String, Object> sysMaps = (Map<String, Object>) sysMap.get("BANKLIST");
			
			for (String key : sysMaps.keySet()) {
				BankInfoVo df = new BankInfoVo();
				df.setBankCode(key);//银行编号
				String values = (String) sysMaps.get(key);
				System.out.println(sysMaps.get(key));
				String[] names = values.split("\\,");
				df.setTimeLimit(names[0]);//每笔交易额度限制
				df.setDailyLimit(names[1]);//每日交易额度
				df.setBankName(names[2]);//银行名字
				bkf.add(df);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return bkf;
	}

	@Override
	public List<BankInfoVo> queryBankList(String number) {
		List<BankInfoVo> bkf = new ArrayList<BankInfoVo>();
		try {
			Map<String, Object> sysMap = SystemPro.getProperties();
			if("00".equals(number)){
				Map<String, Object> sysMaps = (Map<String, Object>) sysMap.get("BANKLIST");
				for (String key : sysMaps.keySet()) {
					BankInfoVo df = new BankInfoVo();
					df.setBankCode(key);//银行编号
					String values = (String) sysMaps.get(key);
					System.out.println(sysMaps.get(key));
					String[] names = values.split("\\,");
					df.setTimeLimit(names[0]);//每笔交易额度限制
					df.setDailyLimit(names[1]);//每日交易额度
					df.setBankName(names[2]);//银行名字
					bkf.add(df);	
				}
			}
			if("01".equals(number)){
				Map<String, Object> sysMaps = (Map<String, Object>) sysMap.get("PCBINDINGCARDLIST");
				for (String key : sysMaps.keySet()) {
					BankInfoVo df = new BankInfoVo();
					df.setBankCode(key);//银行编号					
					df.setBankName((String) sysMaps.get(key));//银行名字
					bkf.add(df);	
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		}
		return bkf;
	}
}
