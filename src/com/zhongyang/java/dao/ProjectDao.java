package com.zhongyang.java.dao;
import com.zhongyang.java.pojo.Project;

/**
 * 
* @Title: ProjectDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 项目管理dao接口
* @author 苏忠贺   
* @date 2015年12月1日 下午3:27:25 
* @version V1.0
 */
public interface ProjectDao {
	/**
	 * 
	* @Title: selectProjectById 
	* @Description:根据id查询项目 
	* @return Project    返回类型 
	* @throws
	 */
	public Project selectProjectById(Project project)throws Exception;
}
