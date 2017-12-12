package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.ModifyBankCard;

public interface ModifyBankCardService {
	
	public int addModifyBankCard(ModifyBankCard modifyBankCard);
	
	public int modifyModifyBankCard(ModifyBankCard modifyBankCard);
	
	public List<ModifyBankCard> queryByParams(ModifyBankCard modifyBankCard);

}
