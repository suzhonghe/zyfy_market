package com.zhongyang.java.system;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.zhongyang.java.vo.LoanDetail;
import com.zhongyang.java.vo.PagerVO;

@Aspect
public class MessageAspect {

	@Before("execution(* com.zhongyang.java.biz.impl.LoanBizImpl.queryAllLoan(*))")
	public void testParams(JoinPoint p){
		System.out.println("--------------------------------------");
	}
}
