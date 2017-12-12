package com.zhongyang.java.vo;

public class ReturnVirtualLoanVO {
	
	private int code;
	
	private String message;
	
	private VirtualLoanVo data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public VirtualLoanVo getData() {
		return data;
	}

	public void setData(VirtualLoanVo data) {
		this.data = data;
	}
}
