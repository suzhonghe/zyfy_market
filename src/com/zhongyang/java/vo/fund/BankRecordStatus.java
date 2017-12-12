package com.zhongyang.java.vo.fund;

public enum BankRecordStatus implements BaseEnum {
		INITIED("初始化"),
	    PROCESSING("处理中"),
	    AUDITING("审核中"),//目前主要用于取现申请复核
	    SUCCESSFUL("成功"),
	    FAILED("失败");

	    private final String key;

	    private BankRecordStatus(String key) {
	        this.key = key;
	    }

	    @Override
	    public String getKey() {
	        return key;
	    }

}
