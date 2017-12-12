package com.zhongyang.java.dao;


import java.util.List;

import com.zhongyang.java.pojo.FreshAmount;

public interface FreshAmountDao {
	
	public int insertFreshAmount(FreshAmount freshAmount);
	
	public int updateFreshAmount(FreshAmount freshAmount);
	
	public List<FreshAmount> selectFreshAmountByParams(FreshAmount freshAmount);
	
	public int insertBatchFreshAmount(List<FreshAmount> list);
}
