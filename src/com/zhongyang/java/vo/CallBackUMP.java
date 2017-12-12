package com.zhongyang.java.vo;

/**
 * Created by Matthew on 2016/1/25.
 */
public class CallBackUMP {
	
	private String user_id;
	
	private String ret_code;
	
	private String ret_msg;
	
	private String user_bind_agreement_list;
	
	private String user_unbind_agreement_list;
	
	public String getUser_unbind_agreement_list() {
		return user_unbind_agreement_list;
	}

	public void setUser_unbind_agreement_list(String user_unbind_agreement_list) {
		this.user_unbind_agreement_list = user_unbind_agreement_list;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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

	public String getUser_bind_agreement_list() {
		return user_bind_agreement_list;
	}

	public void setUser_bind_agreement_list(String user_bind_agreement_list) {
		this.user_bind_agreement_list = user_bind_agreement_list;
	}

	public CallBackUMP(String user_id, String ret_code, String ret_msg, String user_bind_agreement_list) {
		this.user_id = user_id;
		this.ret_code = ret_code;
		this.ret_msg = ret_msg;
		this.user_bind_agreement_list = user_bind_agreement_list;
	}

	public CallBackUMP() {
	}

	@Override
	public String toString() {
		return "CallBackUMP [user_id=" + user_id + ", ret_code=" + ret_code + ", ret_msg=" + ret_msg
				+ ", user_bind_agreement_list=" + user_bind_agreement_list + ", user_unbind_agreement_list="
				+ user_unbind_agreement_list + "]";
	}
}
