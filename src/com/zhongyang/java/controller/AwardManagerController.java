package com.zhongyang.java.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.AwardManagerBiz;
import com.zhongyang.java.vo.ReturnData;

@Controller
public class AwardManagerController extends BaseController{
	
	@Autowired
	private AwardManagerBiz awardManagerBiz;
	
	@RequestMapping(value="/queryMyAward")
	public @ResponseBody ReturnData queryMyAward(HttpServletRequest request){
		return awardManagerBiz.queryMyAward(request);
	}
}
