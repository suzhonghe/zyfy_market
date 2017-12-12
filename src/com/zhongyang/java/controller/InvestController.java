package com.zhongyang.java.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.zhongyang.java.biz.InvestBiz;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.AppInvestPage;
import com.zhongyang.java.vo.InvestDetail;
import com.zhongyang.java.vo.InvestVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.UmpInvestVo;

/**
 * 
* @Title: InvestController.java 
* @Package com.zhongyang.java.controller 
* @Description:投资控制器 
* @author 苏忠贺   
* @date 2015年12月10日 下午6:43:04 
* @version V1.0
 */
@Controller
public class InvestController extends BaseController{
	
	@Autowired
	private InvestBiz investBiz;
	
	/*
	 * 投资
	 */
	@RequestMapping(value="/invest", method = RequestMethod.POST)
	public @ResponseBody Map<String,String> invest(HttpServletRequest request,InvestVo investVo,Integer investAmount,String sourceV,String freshAmountId){
		User user = (User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
		investVo.setUser(user);
		String invest = investBiz.invest(investVo, investAmount,sourceV,freshAmountId);
		Map<String,String> map = new HashMap<>();
		map.put("invest", invest);
		return map; 
	}
	
	/*
	 * 标的交易通知
	 */
	@RequestMapping(value="/bidTransactionSettle")
	@ResponseBody
	public synchronized String bidTransactionSettle(UmpInvestVo umpInvestVo){
		return investBiz.bidTransactionSettleBiz(umpInvestVo);
	}
	
	@RequestMapping(value="/queryInvestRecord", method = RequestMethod.POST)
	public @ResponseBody PagerVO<InvestDetail> queryInvestRecord(PagerVO<InvestDetail> investVo){
		return investBiz.investRecords(investVo);
	}
	
	@RequestMapping(value="/queryAppInvestPageData")
	public @ResponseBody AppInvestPage queryAppInvestPageData(HttpServletRequest request,String loanId){
		return investBiz.queryAppInvestPageData(request, loanId);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/queryByOrderId", method = RequestMethod.POST)
	public @ResponseBody Map queryByOrderId(String orderId){
		return investBiz.queryByOrderId(orderId);
	}
	
	@RequestMapping(value="/queryByTime", method = RequestMethod.POST)
	public @ResponseBody List<Map<String,Object>> queryByTime(){
		return investBiz.queryByTime();
	}
	
	@RequestMapping(value="/investVirtualLoan")
	public @ResponseBody Message investVirtualLoan(){
		return investBiz.investVirtualLoan();
	}
}
