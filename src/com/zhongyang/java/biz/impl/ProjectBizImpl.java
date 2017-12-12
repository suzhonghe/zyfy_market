package com.zhongyang.java.biz.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.ProjectBiz;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.pojo.Project;
import com.zhongyang.java.pojo.ProofPhoto;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.service.ProjectService;
import com.zhongyang.java.service.ProofPhotoService;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.vo.ProjectDetail;

@Service
public class ProjectBizImpl implements ProjectBiz {
	
	static{
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCFMARKET_DOMAIN_NAME=(String) sysMap.get("ZYCFMARKET_DOMAIN_NAME");
	}
	
	private static Logger logger=Logger.getLogger(ProjectBizImpl.class);
	
	private static String ZYCFMARKET_DOMAIN_NAME;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProofPhotoService proofPhotoService;
	
	@Autowired
	private LoanService loanService;

	@Override
	public ProjectDetail queryProjectById(String loanId) {
		ProjectDetail projectDetail=new ProjectDetail();
		try {
			
	        Loan loan = loanService.queryLoanById(loanId);
	        if(loan==null){
	        	throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询数据出现问题");
	        }
	        Project pro=new Project();
	        pro.setId(loan.getProjectId());
			Project project = projectService.queryProjectById(pro);
			//根据项目ID查询的到对应的图片
			List<ProofPhoto> ptoofPhotos = proofPhotoService.queryByProjectId(project.getId());
			projectDetail.setFirmInfo(project.getFirmInfo());
			projectDetail.setOperationRange(project.getOperationRange());
			projectDetail.setRepaySource(project.getRepaySource());
			projectDetail.setRiskInfo(project.getRiskInfo());
			projectDetail.setDescription(project.getDescription());
			String photoIp=ZYCFMARKET_DOMAIN_NAME;
			for (ProofPhoto proofPhoto : ptoofPhotos) {
				//拼接身份证图片存储地址url
				if("法人身份证".equals(proofPhoto.getPhotoName())){
					if(projectDetail.getLegalPersonPhotoUrl()!=null){
						projectDetail.setLegalPersonPhotoUrl(projectDetail.getLegalPersonPhotoUrl()+","+photoIp+proofPhoto.getUrlPath());
					}
					else{
						projectDetail.setLegalPersonPhotoUrl(photoIp+proofPhoto.getUrlPath());
					}
				}
				//拼接企业信心图片存储地址url
				if("企业信息".equals(proofPhoto.getPhotoName())){
					if(projectDetail.getEnterpriseInfoPhotoUrl()!=null){
						projectDetail.setEnterpriseInfoPhotoUrl(projectDetail.getEnterpriseInfoPhotoUrl()+","+photoIp+proofPhoto.getUrlPath());
					}
					else{
						projectDetail.setEnterpriseInfoPhotoUrl(photoIp+proofPhoto.getUrlPath());
					}
				}
				//拼接合同文件存储地址url
				if("合同文件".equals(proofPhoto.getPhotoName())){
					if(projectDetail.getAssetsPhotoUrl()!=null){
						projectDetail.setAssetsPhotoUrl(projectDetail.getAssetsPhotoUrl()+","+photoIp+proofPhoto.getUrlPath());
					}
					else{
						projectDetail.setAssetsPhotoUrl(photoIp+proofPhoto.getUrlPath());
					}
				}
				//拼接资产信息图片存储地址url
				if("资产信息".equals(proofPhoto.getPhotoName())){
					if(projectDetail.getContractPhotoUrl()!=null){
						projectDetail.setContractPhotoUrl(projectDetail.getContractPhotoUrl()+","+photoIp+proofPhoto.getUrlPath());
					}
					else{
						projectDetail.setContractPhotoUrl(photoIp+proofPhoto.getUrlPath());
					}
				}
				//拼接其他资料存储地址url
				if("其他资料".equals(proofPhoto.getPhotoName())){
					if(projectDetail.getOthersPhotoUrl()!=null){
						projectDetail.setOthersPhotoUrl(projectDetail.getOthersPhotoUrl()+","+photoIp+proofPhoto.getUrlPath());
					}
					else{
						projectDetail.setOthersPhotoUrl(photoIp+proofPhoto.getUrlPath());
					}
				}
			}
			return projectDetail; 
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询项目出现问题");
		}
	}
}
