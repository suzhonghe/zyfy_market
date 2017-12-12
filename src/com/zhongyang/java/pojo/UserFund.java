package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class UserFund {
    private String userId;

    private BigDecimal availableAmount;//可用金额

    private BigDecimal depositAmount;//充值

    private BigDecimal dueInAmount;//待收

    private BigDecimal dueOutAmount;//待发

    private BigDecimal frozenAmount;//冻结

    private Date timeCreated;

    private Date timeLastUpdated;

    private BigDecimal transferAmount;//转账

    private BigDecimal withdrawAmount;//提现金额

    private int status;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

	public BigDecimal getDueInAmount() {
		return dueInAmount;
	}

	public void setDueInAmount(BigDecimal dueInAmount) {
		this.dueInAmount = dueInAmount;
	}

	public BigDecimal getDueOutAmount() {
		return dueOutAmount;
	}

	public void setDueOutAmount(BigDecimal dueOutAmount) {
		this.dueOutAmount = dueOutAmount;
	}

	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public BigDecimal getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(BigDecimal withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getTimeLastUpdated() {
		return timeLastUpdated;
	}

	public void setTimeLastUpdated(Date timeLastUpdated) {
		this.timeLastUpdated = timeLastUpdated;
	}

    
}