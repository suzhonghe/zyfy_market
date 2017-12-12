package com.zhongyang.java.vo.app;


/**
* @author 作者:zhaofq
* @version 创建时间：2016年3月1日 上午9:52:10
* 类说明：用来充值,提现的参数实体类
*/
public class OperatingFundsVo {
	private String operatingLimit;//充值金额
	private String bankCode;//银行编号
	private String orderId;//订单编号
	private String sourceV;//视图类型：HTML5为手机页面，web可以为空
	private String userId;
	private String wap;//视图类型：wap为微信端
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSourceV() {
		return sourceV;
	}
	public void setSourceV(String sourceV) {
		this.sourceV = sourceV;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOperatingLimit() {
		return operatingLimit;
	}
	public void setOperatingLimit(String operatingLimit) {
		this.operatingLimit = operatingLimit;
	}
	public String getWap() {
		return wap;
	}
	public void setWap(String wap) {
		this.wap = wap;
	}
    
	
	
}
