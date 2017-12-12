package com.zhongyang.java.biz;

import com.zhongyang.java.pojo.UmpAgreement;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月3日 下午3:41:59
* 类说明:用户保密协议信息
*/
public interface UmpAgreementBiz {
	/*
	 * 通过用户Id查询用户签协议信息
	 */
   public UmpAgreement byUserIDUmpAgreement(UmpAgreement umpAgreement);
   
}
