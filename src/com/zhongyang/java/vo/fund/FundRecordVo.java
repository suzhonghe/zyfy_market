package com.zhongyang.java.vo.fund;

import java.math.BigDecimal;
import java.util.Date;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月4日 下午5:13:05
* 类说明
*/
public class FundRecordVo {
	    private static final long serialVersionUID = 20131116L;

	   
	    private String id;

	
	    private String userId;

	    private BankAccount account;

//	    /*
//	     * 资金记录对应的实体，例如投标就对应InvestId
//	     */
//	    private RealmEntity entity;

	    /**
	     * 资金记录类型
	     */
	    
	    protected FundRecordType type;

	    /**
	     * 资金状态
	     */
	    
	    protected FundRecordStatus status;

	    /**
	     * 资金操作
	     */
	   
	    protected FundRecordOperation operation;

	    /**
	     * 金额
	     */
	 
	    protected BigDecimal amount;

	    /*
	     * 交易订单号, 对应汇付接口中的OrdId
	     */
	    private String orderId;

	    /**
	     * 交易流水号, 对应汇付接口中的TrxId
	     */
	    private String transactionId;

	    /**
	     * 可能为失败的提示信息
	     */
	    protected String description;

	    protected Date timeRecorded;

	    /**
	     * json格式扩展内容
	     */
	    protected String recordPriv;


	    public FundRecordVo(String id,
	                      String userId,
	                      BankAccount account,
	                     // RealmEntity entity,
	                      FundRecordType type,
	                      FundRecordStatus status,
	                      FundRecordOperation operation,
	                      BigDecimal amount,
	                      String orderId,
	                      String transactionId,
	                      String description) {
	        this.id = id;
	        this.userId = userId;
	        this.account = account;
	       // this.entity = entity;
	        this.type = type;
	        this.status = status;
	        this.operation = operation;
	        this.amount = amount;
	        this.orderId = orderId;
	        this.transactionId = transactionId;
	        this.description = description;
	    }


	    //TODO for backward compatibility with jsp
	    @Deprecated
	    public String getTransactionNumber() {
	        return transactionId;
	    }

	    //TODO for backward compatibility with jsp
	    @Deprecated
	    public String getOrderNumber() {
	        return orderId;
	    }

	    //TODO for backward compatibility with jsp
	    @Deprecated
	    public Date getRecordTime() {
	        return timeRecorded;
	    }

		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
		}


		public String getUserId() {
			return userId;
		}


		public void setUserId(String userId) {
			this.userId = userId;
		}


		public BankAccount getAccount() {
			return account;
		}


		public void setAccount(BankAccount account) {
			this.account = account;
		}


		public FundRecordType getType() {
			return type;
		}


		public void setType(FundRecordType type) {
			this.type = type;
		}


		public FundRecordStatus getStatus() {
			return status;
		}


		public void setStatus(FundRecordStatus status) {
			this.status = status;
		}


		public FundRecordOperation getOperation() {
			return operation;
		}


		public void setOperation(FundRecordOperation operation) {
			this.operation = operation;
		}


		public BigDecimal getAmount() {
			return amount;
		}


		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}


		public String getOrderId() {
			return orderId;
		}


		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}


		public String getTransactionId() {
			return transactionId;
		}


		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}


		public String getDescription() {
			return description;
		}


		public void setDescription(String description) {
			this.description = description;
		}


		public Date getTimeRecorded() {
			return timeRecorded;
		}


		public void setTimeRecorded(Date timeRecorded) {
			this.timeRecorded = timeRecorded;
		}


		public String getRecordPriv() {
			return recordPriv;
		}


		public void setRecordPriv(String recordPriv) {
			this.recordPriv = recordPriv;
		}


		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	    
}