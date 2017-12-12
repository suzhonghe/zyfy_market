package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.ProofPhoto;

/**
 * 
* @Title: ProofPhotoDao.java 
* @Package com.zhongyang.java.dao 
* @Description:证明图片数据操作Dao 
* @author 苏忠贺   
* @date 2015年12月24日 上午10:11:17 
* @version V1.0
 */
public interface ProofPhotoDao {
	/**
	 * 
	* @Title: selectByProjectId 
	* @Description:查询项目对应的图片 
	* @return List<ProofPhoto>    返回类型 
	* @throws
	 */
	public List<ProofPhoto> selectByProjectId(String projectId)throws Exception;
}
