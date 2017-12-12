package com.zhongyang.java.service.impl;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.InvestDao;
import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.service.InvestService;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.InvestDetail;

/**
 * 
* @Title: InvestServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:投资信息雨雾接口实现 
* @author 苏忠贺   
* @date 2015年12月10日 下午3:32:02 
* @version V1.0
 */
@Service
public class InvestServiceImpl implements InvestService {

	@Autowired
	private InvestDao investDao;
	
	@Override
	public void addInvest(Invest invest) throws Exception {
		investDao.insertInvest(invest);
	}

	@Override
	public void modifyInvest(Invest invest) throws Exception {
		investDao.updateInvest(invest);
	}

	@Override
	public List<Invest> queryInvestByLoanId(Page<InvestDetail>page) throws Exception {
		return investDao.selectInvestsByLoanId(page);
	}

	@Override
	public List<Map> selectInvestStatusByUserID(User UserID) {
		return investDao.selectInvestStatusByUserID(UserID);
	}

	@Override
	public List<Map> queryInvesSurvey(User user) throws Exception{
		return investDao.selectInvesSurvey(user);
	}

	@Override
	public Map queryByOrderId(Invest invest) throws Exception {
		return investDao.selectByOrderId(invest);
	}

	@Override
	public Invest queryById(Invest invest) throws Exception {
		return investDao.selectById(invest);
	}

	@Override
	public List<Map<String,Object>> queryByParams(Map<String, Object> params) throws Exception {
		return investDao.selectByParams(params);
	}

	@Override
	public List<Map<String, Object>> queryByInvest(Invest invest) {
		return investDao.selectByInvest(invest);
	}
}
