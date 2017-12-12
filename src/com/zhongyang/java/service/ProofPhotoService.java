package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.ProofPhoto;

/**
 * 
* @Title: ProofPhotoService.java 
* @Package com.zhongyang.java.service 
* @Description:证明图片业务接口 
* @author 苏忠贺   
* @date 2015年12月24日 上午10:38:28 
* @version V1.0
 */
public interface ProofPhotoService {
	/**
	 * 
	* @Title: queryByProjectId 
	* @Description:根据项目ID查询证明信息 
	* @return List<ProofPhoto>    返回类型 
	* @throws
	 */
	public List<ProofPhoto> queryByProjectId(String projectId)throws Exception;
}
