package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UmpTenderDao;
import com.zhongyang.java.pojo.UmpTender;
import com.zhongyang.java.service.UmpTenderService;
/**
 * 
* @Title: LoanServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:联动相关业务实现类
* @author 苏忠贺   
* @date 2015年12月4日 上午9:05:01 
* @version V1.0
 */
@Service
public class UmpTenderServiceImpl implements UmpTenderService{
	
	@Autowired
	private UmpTenderDao umpTenderDao;

	@Override
	public void addOneUmpTender(UmpTender umpTender) throws Exception {
		umpTenderDao.insertOneUmpTender(umpTender);
	}

	@Override
	public UmpTender queryUmpTenderByLoanId(String loanId) throws Exception {
		return umpTenderDao.selectUmpTenderByLoanId(loanId);
	}

	@Override
	public void modifyUmpTender(UmpTender umpTender) throws Exception {
		umpTenderDao.updateUmpTender(umpTender);
	}

}
