package com.zhongyang.java.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.UmpAgreementBiz;
import com.zhongyang.java.pojo.UmpAgreement;
import com.zhongyang.java.service.UmpAgreementService;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月3日 下午3:44:31
* 类说明:用户保密协议信息实现
*/
@Service
public class UmpAgreementImpl implements UmpAgreementBiz {
    @Autowired
    UmpAgreementService umpAgreementService;
	@Override
	public UmpAgreement byUserIDUmpAgreement(UmpAgreement umpAgreement) {
		
		return umpAgreementService.byUserUmpAgreement(umpAgreement);
	}

}
