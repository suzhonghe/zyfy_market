package com.zhongyang.java.pojo;

import java.util.Date;

import com.zhongyang.java.vo.fund.BankRecordStatus;
import com.zhongyang.java.vo.fund.BankRecordType;

public class ModifyBankCard {
	
	private String id;
	
	private String orderId;
	
	private String userId;
	
	private String fundAccountId;
	
	private BankRecordStatus status;
	
	private String bank;
	
	private String account;
	
	private BankRecordType type;
	
	private Date createDate;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFundAccountId() {
		return fundAccountId;
	}

	public void setFundAccountId(String fundAccountId) {
		this.fundAccountId = fundAccountId;
	}

	public BankRecordStatus getStatus() {
		return status;
	}

	public void setStatus(BankRecordStatus status) {
		this.status = status;
	}

	public BankRecordType getType() {
		return type;
	}

	public void setType(BankRecordType type) {
		this.type = type;
	}
}
