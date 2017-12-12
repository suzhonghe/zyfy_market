package com.zhongyang.java.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.zhongyang.java.biz.AwardManagerBiz;
import com.zhongyang.java.pojo.ExperienceAmount;
import com.zhongyang.java.pojo.FreshAmount;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.ExperienceAmountService;
import com.zhongyang.java.service.FreshAmountService;
import com.zhongyang.java.vo.MyAward;
import com.zhongyang.java.vo.ReturnData;

@Service
public class AwardManagerBizImpl implements AwardManagerBiz{
	
	@Autowired
	private ExperienceAmountService experienceAmountService;
	
	@Autowired
	private FreshAmountService freshAmountService;
		
	@Override
	public ReturnData queryMyAward(HttpServletRequest request) {
		User user=(User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
		ReturnData returnData=new ReturnData();
		if(user==null){
			returnData.setCode(0);
			returnData.setMessage("没有登录");
			return returnData; 
		}
		ExperienceAmount experienceAmount=new ExperienceAmount();
		experienceAmount.setUserId(user.getId());
		ExperienceAmount res = experienceAmountService.queryByParams(experienceAmount);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		List<MyAward> data=new ArrayList<MyAward>();
		if(res!=null){
			MyAward myAward=new MyAward();
			myAward.setAmount(res.getAmount());
			myAward.setLimitDays(res.getLimitDays());
			if(res.isEnable()){
				myAward.setStatus("2");//失效
			}else{
				if(res.isEnableUse()){
					myAward.setStatus("1");//已使用
				}else{
					myAward.setStatus("0");//未使用
				}
			}
			myAward.setType(2);
			myAward.setName("体验金");
			myAward.setEndTime(sdf.format(res.getEndTime()));
			data.add(myAward);
		}
		
		
		
		FreshAmount freshAmount=new FreshAmount();
		freshAmount.setUserId(user.getId());
		freshAmount.setType(1);
		List<FreshAmount> freshAmounts = freshAmountService.queryFreshAmountByParams(freshAmount);
		for (FreshAmount fa : freshAmounts) {
			MyAward award=new MyAward();
			award.setAmount(fa.getAmount());
			award.setLimitDays(fa.getEffectDay());
			award.setStatus(fa.getStatus().toString());
			award.setEndTime(sdf.format(fa.getEndTime()));
			award.setName(fa.getAmount()+"元"+fa.getMonths()+"个月红包");
			award.setType(1);
			award.setMonths(fa.getMonths());
			award.setLimitAmount(fa.getAmountLimit());
			data.add(award);
		}
		
		returnData.setData(data);
		returnData.setCode(1);
		returnData.setMessage("成功");
		return returnData;
	}
}
