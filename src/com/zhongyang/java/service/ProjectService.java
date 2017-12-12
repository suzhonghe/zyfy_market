package com.zhongyang.java.service;

import com.zhongyang.java.pojo.Project;

/**
 * 
* @Title: ProjectService.java 
* @Package com.zhongyang.java.service 
* @Description:项目service接口 
* @author 苏忠贺   
* @date 2016年1月8日 下午5:43:52 
* @version V1.0
 */
public interface ProjectService {
	/**
	 * 
	* @Title: queryProjectById 
	* @Description:根据ID查询项目信息
	* @return Project    返回类型 
	* @throws
	 */
	public Project queryProjectById(Project project)throws Exception;
}
