package com.zhongyang.java.vo;

public class LoginUser {
		private String userName;

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public LoginUser() {}

		public LoginUser(String userName) {
			this.userName = userName;
		}

		@Override
		public String toString() {
			return "LoginUser [userName=" + userName + "]";
		}
		
}
