package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.ModifyBankCard;

public interface ModifyBankCardDao {
	
	public int insertModifyBankCard(ModifyBankCard modifyBankCard);
	
	public int updateModifyBankCard(ModifyBankCard modifyBankCard);
	
	public List<ModifyBankCard> selectByParams(ModifyBankCard modifyBankCard);

}
