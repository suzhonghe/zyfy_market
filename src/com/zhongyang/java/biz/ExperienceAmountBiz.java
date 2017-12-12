package com.zhongyang.java.biz;

import com.zhongyang.java.pojo.ExperienceAmount;
import com.zhongyang.java.system.Message;

public interface ExperienceAmountBiz {
	
	public Message modifyExperienceAmount(ExperienceAmount experienceAmount);
	
	public ExperienceAmount queryByParams(ExperienceAmount experienceAmount);

}
