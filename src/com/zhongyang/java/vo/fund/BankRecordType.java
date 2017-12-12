package com.zhongyang.java.vo.fund;

public enum BankRecordType implements BaseEnum {
	
	    BANDING("绑卡"),
	    CHANG("换卡");

	    private final String key;

	    private BankRecordType(String key) {
	        this.key = key;
	    }

	    @Override
	    public String getKey() {
	        return key;
	    }

}
