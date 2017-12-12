package com.zhongyang.java.vo;

import java.util.List;

import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.pojo.User;

/**
 * 
* @Title: InvestVo.java 
* @Package com.zhongyang.java.vo 
* @Description: 投资扩展类
* @author 苏忠贺   
* @date 2015年12月9日 下午3:08:21 
* @version V1.0
 */
public class InvestVo {
	
	private User user;
	
	private Loan loan;
	
	private List<InvestDetail> investDetails;
	
	private Integer size;

	public List<InvestDetail> getInvestDetails() {
		return investDetails;
	}

	public void setInvestDetails(List<InvestDetail> investDetails) {
		this.investDetails = investDetails;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

}
