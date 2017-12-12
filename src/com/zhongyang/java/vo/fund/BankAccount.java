package com.zhongyang.java.vo.fund;
/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月3日 下午4:56:45
* 类说明
*/
public class BankAccount {
	   private static final long serialVersionUID = 20130918L;

	    /**
	     * 开户人
	     */

	    private String name;

//	    /**
//	     * 开户银行:中国工商银行
//	     */
//
//	    private Bank bank;

	    /**
	     * 开户行所在地:北京市西城区长安街12号
	     */

	    private String location;

	    /**
	     * 开户支行:工行北京分行海淀支行
	     */

	    private String branch;

	    /**
	     * 账号
	     */
	    private String account;

	    /**
	     * 账号所在省份
	     *
	     * @return
	     */

	    private String province;

	    /**
	     * 账号所在城市
	     *
	     * @return
	     */

	    private String city;

	    /**
	     * 银行预留手机号
	     */

	    private String bankMobile;

	    public BankAccount() {
	    }

//	    public BankAccount(String name,// Bank bank, String location, String branch, String account) {
//	        this.name = name;
//	       // this.bank = bank;
//	        this.location = location;
//	        this.branch = branch;
//	        this.account = account;
//	    }
        public BankAccount(String name,String location, String branch, String account) {
        this.name = name;
        this.location = location;
        this.branch = branch;
        this.account = account;
    }

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getBranch() {
			return branch;
		}

		public void setBranch(String branch) {
			this.branch = branch;
		}

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getBankMobile() {
			return bankMobile;
		}

		public void setBankMobile(String bankMobile) {
			this.bankMobile = bankMobile;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
    
	}
