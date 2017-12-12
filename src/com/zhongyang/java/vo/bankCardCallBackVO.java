package com.zhongyang.java.vo;

public class bankCardCallBackVO {
	private String service;
	private String sign_type;
	private String sign;
	private String mer_id;
	private String version;
	private String order_id;
	private String ret_code;
	private String ret_msg;
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
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
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
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
	public bankCardCallBackVO() {
	}
	public bankCardCallBackVO(String sign_type, String sign, String mer_id, String version, String order_id,
			String ret_code, String ret_msg) {
		this.sign_type = sign_type;
		this.sign = sign;
		this.mer_id = mer_id;
		this.version = version;
		this.order_id = order_id;
		this.ret_code = ret_code;
		this.ret_msg = ret_msg;
	}
	@Override
	public String toString() {
		return "bankCardCallBackVO [sign_type=" + sign_type + ", sign=" + sign + ", mer_id=" + mer_id + ", version="
				+ version + ", order_id=" + order_id + ", ret_code=" + ret_code + ", ret_msg=" + ret_msg + "]";
	}
	
	
}
