package com.zhongyang.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.ProjectBiz;
import com.zhongyang.java.vo.ProjectDetail;


/**
 * 
* @Title: LoanRequestController.java 
* @Package com.zhongyang.java.controller 
* @Description: 项目管理控制器 
* @author 苏忠贺   
* @date 2015年12月2日 上午9:15:47 
* @version V1.0
 */
@Controller
public class ProjectController extends BaseController{
	
	@Autowired
	private ProjectBiz projectBiz;

	/**
	 * 
	* @Title: queryProjectById 
	* @Description:根据标的ID查询对应项目信息
	* @return Project    返回类型 
	* @throws
	 */
	@RequestMapping(value="/queryProjectById")
	public @ResponseBody ProjectDetail queryProjectById(String loanId){
		return projectBiz.queryProjectById(loanId);
	}
}
