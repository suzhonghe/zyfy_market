package com.zhongyang.java.service;

import com.zhongyang.java.pojo.Proof;

/**
 * 
* @Title: ProofService.java 
* @Package com.zhongyang.java.service 
* @Description: 证明材料业务管理 
* @author 苏忠贺   
* @date 2015年12月2日 下午2:26:36 
* @version V1.0
 */
public interface ProofService {
	/*
	 * 插入一条记录
	 */
	public void addOneProof(Proof proof)throws Exception;
	/*
	 * 根据项目Id查询Proof
	 */
	public Proof queryProofByProjectId(String projectId)throws Exception;
	/*
	 * 很据id删除Proof
	 */
	public void deleteProofById(String id)throws Exception;
}
