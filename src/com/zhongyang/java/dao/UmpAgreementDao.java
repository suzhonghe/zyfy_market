package com.zhongyang.java.dao;

import com.zhongyang.java.pojo.UmpAgreement;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月3日 下午3:48:07
* 类说明：用户协议信息
*/
public interface UmpAgreementDao {
	
  public UmpAgreement byUserUmpAgreement(UmpAgreement umpAgreement);

  public int insertUmpAgreement(UmpAgreement umpAgreement);

    public int updateAgreement(UmpAgreement umpAgreement);
}
