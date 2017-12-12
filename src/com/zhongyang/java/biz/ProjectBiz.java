package com.zhongyang.java.biz;

import com.zhongyang.java.vo.ProjectDetail;

/**
 * 
* @Title: LoanRequestBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:  项目管理业务处理接口
* @author 苏忠贺   
* @date 2015年12月2日 上午8:52:29 
* @version V1.0
 */
public interface ProjectBiz {
	/**
	 * 
	* @Title: queryProjectById 
	* @Description:根据Id查询项目
	* @return Project    返回类型 
	* @throws
	 */
	public ProjectDetail queryProjectById(String loanId);
}
