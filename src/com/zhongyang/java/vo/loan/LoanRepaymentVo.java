package com.zhongyang.java.vo.loan;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.zhongyang.java.pojo.LoanRepayment;

public class LoanRepaymentVo {
    private String id;

    private Integer currentperiod;

    private BigDecimal repayamount;

    private Date repaydate;

    private String status;

    private String sourceId;

    private String sourceRealm;

    private BigDecimal amountinterest;

    private BigDecimal amountoutstanding;

    private BigDecimal amountprincipal;
    
    private BigDecimal toTleAmount;

    private String duedate;
   
    private String loanId;
    
    private String loanUserId;
    
    private String title;
    
    private Date startTime;
    
	private Date endTime;
	
	private String totlesize;
	
    List<LoanRepayment> loanVo = new ArrayList<LoanRepayment>();
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getCurrentperiod() {
        return currentperiod;
    }

    public void setCurrentperiod(Integer currentperiod) {
        this.currentperiod = currentperiod;
    }

    public BigDecimal getRepayamount() {
        return repayamount;
    }

    public void setRepayamount(BigDecimal repayamount) {
        this.repayamount = repayamount;
    }

    public Date getRepaydate() {
        return repaydate;
    }

    public void setRepaydate(Date repaydate) {
        this.repaydate = repaydate;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String string) {
		this.status = string;
	}

	public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    public String getSourceRealm() {
        return sourceRealm;
    }

    public void setSourceRealm(String sourceRealm) {
        this.sourceRealm = sourceRealm == null ? null : sourceRealm.trim();
    }

    public BigDecimal getAmountinterest() {
        return amountinterest;
    }

    public void setAmountinterest(BigDecimal amountinterest) {
        this.amountinterest = amountinterest;
    }

    public BigDecimal getAmountoutstanding() {
        return amountoutstanding;
    }

    public void setAmountoutstanding(BigDecimal amountoutstanding) {
        this.amountoutstanding = amountoutstanding;
    }

    public BigDecimal getAmountprincipal() {
        return amountprincipal;
    }

    public void setAmountprincipal(BigDecimal amountprincipal) {
        this.amountprincipal = amountprincipal;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate == null ? null : duedate.trim();
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId == null ? null : loanId.trim();
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<LoanRepayment> getLoanVo() {
		return loanVo;
	}

	public void setLoanVo(List<LoanRepayment> loanVo) {
		this.loanVo = loanVo;
	}

	public BigDecimal getToTleAmount() {
		return toTleAmount;
	}

	public void setToTleAmount(BigDecimal toTleAmount) {
		this.toTleAmount = toTleAmount;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTotlesize() {
		return totlesize;
	}

	public void setTotlesize(String totlesize) {
		this.totlesize = totlesize;
	}

	public String getLoanUserId() {
		return loanUserId;
	}

	public void setLoanUserId(String loanUserId) {
		this.loanUserId = loanUserId;
	}

}