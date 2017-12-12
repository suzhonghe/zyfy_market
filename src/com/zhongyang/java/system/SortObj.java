package com.zhongyang.java.system;

import java.util.Comparator;

import com.zhongyang.java.vo.loan.LoanRepaymentVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年2月29日 下午1:22:06
* 类说明
*/
public class SortObj implements Comparator<Object>{
	 public int compare(Object arg0,Object arg1){
		 LoanRepaymentVo lrv1 = (LoanRepaymentVo)arg0;
		 LoanRepaymentVo lrv2 = (LoanRepaymentVo)arg1;
		  int flag = lrv1.getDuedate().compareTo(lrv2.getDuedate());
		  return flag;
		 }

}
