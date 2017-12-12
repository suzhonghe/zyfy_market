package com.zhongyang.java.vo;

public class ParamsPojo {
	private String number;
	private String bank;
	private String account;
	private String fastPayment;
	private String source;
	private String TransferAmount;
	
	public String getTransferAmount() {
		return TransferAmount;
	}

	public void setTransferAmount(String transferAmount) {
		TransferAmount = transferAmount;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public String getFastPayment() {
		return fastPayment;
	}

	public void setFastPayment(String fastPayment) {
		this.fastPayment = fastPayment;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	

}
