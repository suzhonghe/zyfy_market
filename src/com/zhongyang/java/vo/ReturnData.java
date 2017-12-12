package com.zhongyang.java.vo;

import java.util.List;

public class ReturnData {
	
	private String message;
	
	private int code;
	
	private List<MyAward> data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<MyAward> getData() {
		return data;
	}

	public void setData(List<MyAward> data) {
		this.data = data;
	}
}
