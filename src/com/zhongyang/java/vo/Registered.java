package com.zhongyang.java.vo;

public class Registered {
	
	private String mobile;
	private String passphrase;
	private String referralMobile;
	private String source;
	private String regcode;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassphrase() {
		return passphrase;
	}
	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}
	public String getReferralMobile() {
		return referralMobile;
	}
	public void setReferralMobile(String referralMobile) {
		this.referralMobile = referralMobile;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRegcode() {
		return regcode;
	}
	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}
	public Registered() {}
	
	public Registered(String mobile, String passphrase, String referralMobile, String source, String regcode) {
		this.mobile = mobile;
		this.passphrase = passphrase;
		this.referralMobile = referralMobile;
		this.source = source;
		this.regcode = regcode;
	}
	@Override
	public String toString() {
		return "Registered [mobile=" + mobile + ", passphrase=" + passphrase + ", referralMobile=" + referralMobile
				+ ", source=" + source + ", regcode=" + regcode + "]";
	}
		
}
