package com.zhongyang.java.biz.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umpay.api.exception.ReqDataException;
import com.umpay.api.exception.RetDataException;
import com.zhongyang.java.biz.ClientFundRecordBiz;
import com.zhongyang.java.pojo.ClientFundRecord;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.service.ClientFundRecordService;
import com.zhongyang.java.service.FundRecordService;
import com.zhongyang.java.service.UserFundService;
import com.zhongyang.java.system.umpay.UmpaySignature;
import com.zhongyang.java.vo.TransferCallback;
import com.zhongyang.java.vo.fund.FundRecordStatus;
@Service
public class ClientFundRecordBizImpl implements ClientFundRecordBiz {
	
	Logger logger = Logger.getLogger(ClientFundRecordBizImpl.class.getName());
	
	@Autowired
	private ClientFundRecordService clientFundRecordService;
	
	@Autowired
	private FundRecordService fundRecordService;
	
	@Autowired
	private UserFundService userFundService;

	@Override
	public String transferCallback(TransferCallback transferCallback) {
		try {
			String ret_code = transferCallback.getRet_code();
			if(!"0000".equals(ret_code)){
				ClientFundRecord record=new ClientFundRecord();
				record.setOrderid(transferCallback.getOrder_id());
				record.setStatus(FundRecordStatus.FAILED);
				clientFundRecordService.modifyClientFundRecord(record);
				
				FundRecord fundRecord=new FundRecord();
				fundRecord.setOrderid(transferCallback.getOrder_id());
				fundRecord.setStatus(FundRecordStatus.FAILED);
				fundRecordService.modifyFundRecord(fundRecord);
			}else{
				ClientFundRecord record=new ClientFundRecord();
				record.setOrderid(transferCallback.getOrder_id());
				record.setStatus(FundRecordStatus.SUCCESSFUL);
				clientFundRecordService.modifyClientFundRecord(record);
									
				FundRecord fundRecord=new FundRecord();
				fundRecord.setOrderid(transferCallback.getOrder_id());
				fundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
				fundRecordService.modifyFundRecord(fundRecord);
				
				
				FundRecord rd = fundRecordService.findFundRecordByOrderId(fundRecord);
				UserFund userFund=new UserFund();
				userFund.setUserId(rd.getUserId());
				UserFund fund = userFundService.byUserID(userFund);
				userFund.setAvailableAmount(fund.getAvailableAmount().add(rd.getAmount()));
				userFund.setTimeLastUpdated(new Date());
				userFundService.modifyUserFund(userFund);
			}
			//准备回调参数
			Map<String, String> map=new HashMap<String, String>();
			map.put("order_id", transferCallback.getOrder_id());
			map.put("mer_date", transferCallback.getMer_date());
			map.put("ret_code",ret_code);
			UmpaySignature umpaySignature = new UmpaySignature(null, map);
			
			return umpaySignature.callBackSignature();
			
		} catch (ReqDataException | RetDataException e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		} 
		return null;
	}

}
