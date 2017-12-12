package com.zhongyang.java.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.ExperienceAmountBiz;
import com.zhongyang.java.pojo.ExperienceAmount;
import com.zhongyang.java.service.ExperienceAmountService;
import com.zhongyang.java.system.Message;

@Service
public class ExperienceAmountBizImpl implements ExperienceAmountBiz {
	
	@Autowired
	private ExperienceAmountService experienceAmountService;

	@Override
	public Message modifyExperienceAmount(ExperienceAmount experienceAmount) {
		return null;
	}

	@Override
	public ExperienceAmount queryByParams(ExperienceAmount experienceAmount) {
		return null;
	}

}
