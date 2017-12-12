package com.zhongyang.java.biz;

import javax.servlet.http.HttpServletRequest;

import com.zhongyang.java.vo.ReturnData;

public interface AwardManagerBiz {
	
	public ReturnData queryMyAward(HttpServletRequest request);
}
