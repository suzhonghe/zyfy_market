package com.zhongyang.java.biz;

import java.util.Map;

import com.zhongyang.java.vo.ReturnVirtualLoanVO;

/**
 * 体验标信息操作
 * @author Administrator
 *
 */
public interface VirtualLoanBiz {
	
	public ReturnVirtualLoanVO queryVirtualLoanOpened();

	public Map<String, String> queryVirtualLoan();
}
