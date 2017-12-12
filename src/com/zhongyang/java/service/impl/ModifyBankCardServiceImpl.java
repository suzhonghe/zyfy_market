package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ModifyBankCardDao;
import com.zhongyang.java.pojo.ModifyBankCard;
import com.zhongyang.java.service.ModifyBankCardService;
@Service
public class ModifyBankCardServiceImpl implements ModifyBankCardService {
	
	@Autowired
	private ModifyBankCardDao modifyBankCardDao;

	@Override
	public int addModifyBankCard(ModifyBankCard modifyBankCard) {
		return modifyBankCardDao.insertModifyBankCard(modifyBankCard);
	}

	@Override
	public int modifyModifyBankCard(ModifyBankCard modifyBankCard) {
		return modifyBankCardDao.updateModifyBankCard(modifyBankCard);
	}

	@Override
	public List<ModifyBankCard> queryByParams(ModifyBankCard modifyBankCard) {
		return modifyBankCardDao.selectByParams(modifyBankCard);
	}

}
