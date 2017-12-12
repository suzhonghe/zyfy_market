package com.zhongyang.java.dao;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.InvestDetail;

/**
 * 
* @Title: InvestDao.java 
* @Package com.zhongyang.java.dao 
* @Description:投资信息DAO接口
* @author 苏忠贺   
* @date 2015年12月10日 下午2:20:47 
* @version V1.0
 */
public interface InvestDao {
	/*
	 * 添加一条投资记录
	 */
	public void insertInvest(Invest invest)throws Exception;
	/**
	 * 
	* @Title: updateInvest 
	* @Description:修改投资
	* @return void    返回类型 
	* @throws
	 */
	public void updateInvest(Invest invest)throws Exception;
	/**
	 * 
	* @Title: selectInvestsByLoanId 
	* @Description:根据标的ID查询投资记录
	* @return List<Invest>    返回类型 
	* @throws
	 */
	public List<Invest> selectInvestsByLoanId(Page<InvestDetail>page);

	public List<Map> selectInvestStatusByUserID(User UserID);
	
	public List<Map> selectInvesSurvey(User user)throws Exception;
	
	public Map selectByOrderId(Invest invest)throws Exception;
	
	public Invest selectById(Invest invest)throws Exception;
	
	public List<Map<String,Object>> selectByParams(Map<String,Object>params)throws Exception;
	
	public List<Map<String,Object>> selectByInvest(Invest invest);

}
