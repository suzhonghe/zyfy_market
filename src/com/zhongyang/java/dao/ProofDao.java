package com.zhongyang.java.dao;

import com.zhongyang.java.pojo.Proof;

/**
 * 
* @Title: ProofDao.java 
* @Package com.zhongyang.java.dao 
* @Description:证明材料dao 
* @author 苏忠贺   
* @date 2015年12月2日 下午1:58:11 
* @version V1.0
 */
public interface ProofDao {

	/*
	 * 插入一条记录
	 */
	public void insertProof(Proof proof)throws Exception;
	/*
	 * 根据项目Id查询Proof
	 */
	public Proof selectProofByProjectId(String projectId)throws Exception;
	/*
	 * 很据id删除Proof
	 */
	public void deleteProofById(String id)throws Exception;
}
