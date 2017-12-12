package com.zhongyang.java.vo.fund;

import java.math.BigDecimal;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月3日 下午4:55:01
* 类说明
*/
public class FundDepositVo extends FundRecordVo{
	  private static final long serialVersionUID = 20131130L;

	    public FundDepositVo(String id,
	                       String userId, 
	                       BankAccount account,
	                       FundRecordStatus status,
	                       BigDecimal amount,
	                       String orderId, 
	                       String transactionId, 
	                       String description) {
	        super(id, 
	              userId, 
	              account,
	              FundRecordType.DEPOSIT,
	              status,
	              FundRecordOperation.IN,
	              amount, 
	              orderId, 
	              transactionId, 
	              description);
	    }


	    public void FundDeposit() {
	    }

}
