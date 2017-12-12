package com.zhongyang.java.vo;

import java.math.BigDecimal;
import java.util.Date;

public class VirtualLoanVo {
	
	private String id;
	
	private BigDecimal experienceAmount;
	
	private double rate;
	
	private String createTime;
	
	private int loanDay;
	
	private Integer status;
	
	private String method;
	
	private String title;
	
	private String endTime;
	
	private boolean login;
	
	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getExperienceAmount() {
		return experienceAmount;
	}

	public void setExperienceAmount(BigDecimal experienceAmount) {
		this.experienceAmount = experienceAmount;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getLoanDay() {
		return loanDay;
	}

	public void setLoanDay(int loanDay) {
		this.loanDay = loanDay;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
