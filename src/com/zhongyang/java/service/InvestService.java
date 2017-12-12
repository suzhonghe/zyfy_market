package com.zhongyang.java.service;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.InvestDetail;

/**
 * 
* @Title: InvestService.java 
* @Package com.zhongyang.java.service 
* @Description:投资信息业务接口 
* @author 苏忠贺   
* @date 2015年12月10日 下午3:30:33 
* @version V1.0
 */
public interface InvestService {
	
	/*
	 * 添加一条投资记录
	 */
	public void addInvest(Invest invest)throws Exception;
	/**
	 * 
	* @Title: modifyInvest 
	* @Description:修改投资记录
	* @return void    返回类型 
	* @throws
	 */
	public void modifyInvest(Invest invest)throws Exception;
	/**
	 * 
	* @Title: queryInvestByLoanId 
	* @Description:根据标的ID查询投资记录
	* @return List<Invest>    返回类型 
	* @throws
	 */
	public List<Invest>queryInvestByLoanId(Page<InvestDetail>page)throws Exception;


	public List<Map> selectInvestStatusByUserID(User UserID);
	
	public List<Map> queryInvesSurvey(User user)throws Exception;
	
	public Map queryByOrderId(Invest invest)throws Exception;
	
	public Invest queryById(Invest invest)throws Exception;
	
	public List<Map<String,Object>> queryByParams(Map<String,Object>params)throws Exception;
	
	public List<Map<String,Object>> queryByInvest(Invest invest);
}
