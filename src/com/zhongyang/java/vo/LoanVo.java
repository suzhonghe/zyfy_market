package com.zhongyang.java.vo;

import java.util.List;

public class LoanVo {
	
	private Integer size;
	
	private List<LoanDetail> loanDetails;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<LoanDetail> getLoanDetails() {
		return loanDetails;
	}

	public void setLoanDetails(List<LoanDetail> loanDetails) {
		this.loanDetails = loanDetails;
	}
	
}
