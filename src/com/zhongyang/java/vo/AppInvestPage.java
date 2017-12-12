package com.zhongyang.java.vo;

import java.math.BigDecimal;
import java.util.List;

import com.zhongyang.java.pojo.FreshAmount;
import com.zhongyang.java.vo.loan.Method;

/**
 * 
* @Title: AppInvestPage.java 
* @Package com.zhongyang.java.vo 
* @Description:app投资页需要的数据
* @author 苏忠贺   
* @date 2016年3月4日 上午10:36:46 
* @version V1.0
 */
public class AppInvestPage {
	
	private String loanId;
	
	private String productName;
	
	private String loanName;
	
	private BigDecimal suplusAmount;
	
	private BigDecimal minAmount;
	
	private BigDecimal stepAmount;
	
	private Method method;
	
	private BigDecimal rate;
	
	private Double addRate;
	
	private Integer months;
	
	private BigDecimal maxAmount;
	
	private BigDecimal availableAmount; // 可用余额（个人账户的）
	
	private List<FreshAmount> freshAmounts;
	
	public Double getAddRate() {
		return addRate;
	}

	public void setAddRate(Double addRate) {
		this.addRate = addRate;
	}

	public List<FreshAmount> getFreshAmounts() {
		return freshAmounts;
	}

	public void setFreshAmounts(List<FreshAmount> freshAmounts) {
		this.freshAmounts = freshAmounts;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getStepAmount() {
		return stepAmount;
	}

	public void setStepAmount(BigDecimal stepAmount) {
		this.stepAmount = stepAmount;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public BigDecimal getSuplusAmount() {
		return suplusAmount;
	}

	public void setSuplusAmount(BigDecimal suplusAmount) {
		this.suplusAmount = suplusAmount;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}
}
