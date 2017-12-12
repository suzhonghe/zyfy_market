package com.zhongyang.java.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.system.Message;

public class UserFundVo {
	
	private Message message;
	
    private String userid;

    private BigDecimal availableAmount;

    private BigDecimal depositAmount;

    private BigDecimal dueInAmount;

    private BigDecimal dueOutAmount;

    private BigDecimal frozenAmount;

    private Date timecreated;

    private Date timelastupdated;

    private BigDecimal transferAmount;

    private BigDecimal withdrawAmount;
    
    private int status;
    //订单Id
    private String minrecharge;
    private String orderId;
    private String account;
    private String singleLimit;//单次限额

    private String bank;
    private String bankCoade;//银行编码
    private String bankName;
    private String bankNum;
    private String rechargeRates;//充值费率
    private String dailyLimit;
	private String timeLimit;
	private String WithdrawRates;//充值费率
    public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
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

    public Date getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(Date timecreated) {
        this.timecreated = timecreated;
    }

    public Date getTimelastupdated() {
        return timelastupdated;
    }

    public void setTimelastupdated(Date timelastupdated) {
        this.timelastupdated = timelastupdated;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(String dailyLimit) {
		this.dailyLimit = dailyLimit;
	}

	public String getSingleLimit() {
		return singleLimit;
	}

	public void setSingleLimit(String singleLimit) {
		this.singleLimit = singleLimit;
	}

	public String getBankCoade() {
		return bankCoade;
	}

	public void setBankCoade(String bankCoade) {
		this.bankCoade = bankCoade;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getRechargeRates() {
		return rechargeRates;
	}

	public void setRechargeRates(String rechargeRates) {
		this.rechargeRates = rechargeRates;
	}

	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getWithdrawRates() {
		return WithdrawRates;
	}

	public void setWithdrawRates(String withdrawRates) {
		WithdrawRates = withdrawRates;
	}

	public String getMinrecharge() {
		return minrecharge;
	}

	public void setMinrecharge(String minrecharge) {
		this.minrecharge = minrecharge;
	}
	
    
}