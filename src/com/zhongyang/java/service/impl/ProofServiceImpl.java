package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ProofDao;
import com.zhongyang.java.pojo.Proof;
import com.zhongyang.java.service.ProofService;
/**
 * 
* @Title: ProofServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description: 业务实现类 
* @author 苏忠贺   
* @date 2015年12月2日 下午3:00:50 
* @version V1.0
 */
@Service
public class ProofServiceImpl implements ProofService{
	
	@Autowired
	private ProofDao proofDao;
	
	@Override
	public void addOneProof(Proof proof) throws Exception {
		proofDao.insertProof(proof);
	}
	@Override
	public Proof queryProofByProjectId(String projectId) throws Exception {
		return proofDao.selectProofByProjectId(projectId);
	}
	@Override
	public void deleteProofById(String id) throws Exception {
		proofDao.deleteProofById(id);
	}

}
