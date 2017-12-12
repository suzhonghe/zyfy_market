package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.VirtualLoanDao;
import com.zhongyang.java.pojo.VirtualLoan;
import com.zhongyang.java.service.VirtualLoanService;

/**
 * 体验标操作实现类
 * @author Administrator
 *
 */
@Service
public class VirtualLoanServiceImpl implements VirtualLoanService {
	
	@Autowired
	public VirtualLoanDao virtualLoanDao;

	@Override
	public VirtualLoan queryVirtualLoanByParams(VirtualLoan virtualLoan) {
		return virtualLoanDao.selectVirtualLoanByParams(virtualLoan);
	}
}
