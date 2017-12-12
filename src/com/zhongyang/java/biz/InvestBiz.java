package com.zhongyang.java.biz;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.AppInvestPage;
import com.zhongyang.java.vo.InvestDetail;
import com.zhongyang.java.vo.InvestVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.UmpInvestVo;

/**
 * 
* @Title: InvestBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:投资业务接口 
* @author 苏忠贺   
* @date 2015年12月9日 下午2:55:36 
* @version V1.0
 */
public interface InvestBiz {
	
	/*
	 * 投资
	 */
	public String invest(InvestVo investVo, Integer investAmount,String sourceV,String freshAmountId);
	
	/*
	 * 标的通知
	 */
	public String bidTransactionSettleBiz(UmpInvestVo umpInvestVo);
	/**
	 * 
	* @Title: investRecords 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return List<Invest>    返回类型 
	* @throws
	 */
	public PagerVO<InvestDetail> investRecords(PagerVO<InvestDetail> investVo);
	
	public AppInvestPage queryAppInvestPageData(HttpServletRequest request,String loanId);

	public Map queryByOrderId(String orderId);
	
	public List<Map<String,Object>> queryByTime();
	
	public Message investVirtualLoan();
}
