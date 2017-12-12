package com.zhongyang.java.vo;

public class ModifyBankCardCallBackVO {
	
	private String service;
	
	private String user_id;
	
	private String mer_id;
	
	private String version;
	
	private String order_id;
	
	private String ret_code;
	
	private String ret_msg;
	
	private String mer_date;
	
	private String gate_id;
	
	private String last_four_cardid;
	
	private String user_bind_agreement_list;
	
	public String getMer_date() {
		return mer_date;
	}

	public void setMer_date(String mer_date) {
		this.mer_date = mer_date;
	}

	public String getGate_id() {
		return gate_id;
	}

	public void setGate_id(String gate_id) {
		this.gate_id = gate_id;
	}

	public String getLast_four_cardid() {
		return last_four_cardid;
	}

	public void setLast_four_cardid(String last_four_cardid) {
		this.last_four_cardid = last_four_cardid;
	}

	public String getUser_bind_agreement_list() {
		return user_bind_agreement_list;
	}

	public void setUser_bind_agreement_list(String user_bind_agreement_list) {
		this.user_bind_agreement_list = user_bind_agreement_list;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getMer_id() {
		return mer_id;
	}

	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

}
