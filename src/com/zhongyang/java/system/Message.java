package com.zhongyang.java.system;

public class Message {
	private int code;
	private String message;
	
	
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
	public Message() {
	}
	public Message(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	
	
}
