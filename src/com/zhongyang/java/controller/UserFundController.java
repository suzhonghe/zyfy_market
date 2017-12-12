package com.zhongyang.java.controller;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.zhongyang.java.biz.FundRecordBiz;
import com.zhongyang.java.biz.UserFundBiz;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.vo.UserFundVo;
import com.zhongyang.java.vo.fund.FundRecordCalenderVo;
import com.zhongyang.java.vo.fund.FundRecordVo;

/**
 * 
* @Title: UserFundController.java 
* @Package com.zhongyang.java.controller 
* @Description: userFund控制器 
* @author 苏忠贺   
* @date 2016年1月11日 上午10:02:46 
* @version V1.0
 */
@Controller
public class UserFundController extends BaseController{
	@Autowired
	private UserFundBiz userFundBiz;
	
	@Autowired
	private FundRecordBiz fundRecordBiz;
	@RequestMapping(value="/queryUserFundByUserId")
	public @ResponseBody UserFundVo queryByUserId(HttpServletRequest request){
		User user = (User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
		return userFundBiz.queryByUseId(user);
	}
	
	/**
	 * 用户资金日历
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/userFundRecordCalendar")
	public @ResponseBody List<FundRecordCalenderVo> userFundRecordCalendar(HttpServletRequest request,String newData){
		User user = (User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
		String newDatas = "2016-06";
		List<FundRecordCalenderVo> recordCalenderVos= new ArrayList<>();
		try {
			if (user != null) {
				recordCalenderVos =  fundRecordBiz.userFundRecordCalendar(newData,user);
			}
			return recordCalenderVos;
		} catch (Exception e) {
			throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
		}
	}
	
	
}
