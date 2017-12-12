package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.CmsColumnDao;
import com.zhongyang.java.pojo.CmsColumn;
import com.zhongyang.java.service.CmsColumnService;
@Service
public class CmsColumnServiceImpl implements CmsColumnService {
	
	@Autowired
	private CmsColumnDao cmsColumnDao;

	@Override
	public CmsColumn queryByParams(CmsColumn cmsColumn) throws Exception {
		return cmsColumnDao.selectByParams(cmsColumn);
	}

}
