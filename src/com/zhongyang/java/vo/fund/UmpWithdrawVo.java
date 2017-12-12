package com.zhongyang.java.vo.fund;

import com.zhongyang.java.vo.UmpResultVO;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月8日 下午7:58:09
* 类说明
*/
public class UmpWithdrawVo extends UmpResultVO{
  private String order_id;
  private String mer_date;
  private String amount;
  private String account_id;
  private String com_amt;
  private String com_amt_type;
  private String ret_code;
  private String ret_msg;
  private String trade_no;
public String getOrder_id() {
	return order_id;
}
public void setOrder_id(String order_id) {
	this.order_id = order_id;
}
public String getMer_date() {
	return mer_date;
}
public void setMer_date(String mer_date) {
	this.mer_date = mer_date;
}
public String getAmount() {
	return amount;
}
public void setAmount(String amount) {
	this.amount = amount;
}
public String getAccount_id() {
	return account_id;
}
public void setAccount_id(String account_id) {
	this.account_id = account_id;
}
public String getCom_amt() {
	return com_amt;
}
public void setCom_amt(String com_amt) {
	this.com_amt = com_amt;
}
public String getCom_amt_type() {
	return com_amt_type;
}
public void setCom_amt_type(String com_amt_type) {
	this.com_amt_type = com_amt_type;
}
public String getRet_code() {
	return ret_code;
}
public void setRet_code(String ret_code) {
	this.ret_code = ret_code;
}
public String getRet_msg() {
	return ret_msg;
}
public void setRet_msg(String ret_msg) {
	this.ret_msg = ret_msg;
}
public String getTrade_no() {
	return trade_no;
}
public void setTrade_no(String trade_no) {
	this.trade_no = trade_no;
}
  
  
}
