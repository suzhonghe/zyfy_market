package com.zhongyang.java.vo;
/**
* @author 作者:zhaofq
* @version 创建时间：2016年3月1日 下午3:16:43
* 类说明
*/
public class BankInfoVo {
	
	private String bankName;
	private String rates;
	private String dailyLimit;
	private String timeLimit;
	private String bankCode;
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getRates() {
		return rates;
	}
	public void setRates(String rates) {
		this.rates = rates;
	}
	public String getDailyLimit() {
		return dailyLimit;
	}
	public void setDailyLimit(String dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
    
	
}
