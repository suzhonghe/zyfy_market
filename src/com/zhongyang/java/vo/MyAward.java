package com.zhongyang.java.vo;

import java.math.BigDecimal;

public class MyAward {
	
	private BigDecimal amount;//金额
	
	private String endTime;//到期时间
	
	private int limitDays;
	
	private String status;//0：未使用，1：已使用，2：已过期
	
	private String name;
	
	private Integer type;//1：红包，2：体验金
	
	private Integer months;//红包适用的标的期限
	
	private BigDecimal limitAmount;//红包适用限制额度
		
	public BigDecimal getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(BigDecimal limitAmount) {
		this.limitAmount = limitAmount;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getLimitDays() {
		return limitDays;
	}

	public void setLimitDays(int limitDays) {
		this.limitDays = limitDays;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
