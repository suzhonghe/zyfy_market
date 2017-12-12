package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ProjectDao;
import com.zhongyang.java.pojo.Project;
import com.zhongyang.java.service.ProjectService;

/**
 * 
* @Title: ProjectServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:service实现
* @author 苏忠贺   
* @date 2016年1月8日 下午5:53:08 
* @version V1.0
 */
@Service
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	private ProjectDao projectDao;
	
	@Override
	public Project queryProjectById(Project project) throws Exception {
		
		return projectDao.selectProjectById(project);
	}

}
