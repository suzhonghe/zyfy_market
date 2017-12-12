package com.zhongyang.java.biz;


import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.web.servlet.ModelAndView;

import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.*;
import com.zhongyang.java.vo.app.AppUserInfoVo;

import java.util.List;
import java.util.Map;

public interface UserBiz {
	public Message addUser(HttpServletRequest req,Registered registered);
	
	public Message mobileRepeat(String mobile,HttpServletRequest request);
	
	public Message idCertificat(HttpServletRequest request,String idnumber,String name,User user);
	
	public Message login(HttpServletRequest req,LoginVO loginVO);
	
	public LoginUser loginUser(User user);
	
	public Message updatePassword(HttpServletRequest request,String oldPassword,String password);

    public String agreement(User user,int[] agreements);
    
    public Message cancleAgreement(User user,int[] agreements,String sourceV);
    
    public String appAgreement(User user, int[] agreements);

    public String agreementCallBack(CallBackUMP callBackUMP);
    
    public String cancleAgreementCallBack(CallBackUMP callBackUMP);

	public Message updateUserName(HttpServletRequest request,String userName);

	public Message updateMail(HttpServletRequest request,String userName);

	public List<Map> selectInvestStatusByUserID(HttpServletRequest req);
	
	public UserInfoVo userSettings(HttpServletRequest request);
	
	public Map appLogin(HttpServletRequest request, UserInfoVo userInfoVo);
	
	public Message queryUserByParams(String mobile);
	
	public List<Map>queryInvestSurveyByUser(HttpServletRequest request);
	
	public PagerVO queryInviterInvest(HttpServletRequest request,Page page);
	
	public Message resetPasswd(HttpServletRequest request, String passwd);

	public PagerVO queryUserByUserId(HttpServletRequest request, Page page);
}	
