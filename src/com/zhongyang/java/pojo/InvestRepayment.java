package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class InvestRepayment {
    private String id;

    private Integer currentPeriod;

    private Integer relativePeriod;

    private BigDecimal repayAmount;

    private Date repayDate;

    private String status;

    private String sourceId;

    private String sourceRealm;

    private BigDecimal amountInterest;

    private BigDecimal amountOutStanding;

    private BigDecimal amountPrincipal;

    private String dueDate;

    private String investId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(Integer currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public Integer getRelativePeriod() {
		return relativePeriod;
	}

	public void setRelativePeriod(Integer relativePeriod) {
		this.relativePeriod = relativePeriod;
	}

	public BigDecimal getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceRealm() {
		return sourceRealm;
	}

	public void setSourceRealm(String sourceRealm) {
		this.sourceRealm = sourceRealm;
	}

	public BigDecimal getAmountInterest() {
		return amountInterest;
	}

	public void setAmountInterest(BigDecimal amountInterest) {
		this.amountInterest = amountInterest;
	}

	public BigDecimal getAmountOutStanding() {
		return amountOutStanding;
	}

	public void setAmountOutStanding(BigDecimal amountOutStanding) {
		this.amountOutStanding = amountOutStanding;
	}

	public BigDecimal getAmountPrincipal() {
		return amountPrincipal;
	}

	public void setAmountPrincipal(BigDecimal amountPrincipal) {
		this.amountPrincipal = amountPrincipal;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getInvestId() {
		return investId;
	}

	public void setInvestId(String investId) {
		this.investId = investId;
	}
}