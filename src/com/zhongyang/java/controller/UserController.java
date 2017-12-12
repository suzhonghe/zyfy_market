package com.zhongyang.java.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.UserBiz;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.http.LoginUserInterface;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.CallBackUMP;
import com.zhongyang.java.vo.LoginUser;
import com.zhongyang.java.vo.LoginVO;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.Registered;
import com.zhongyang.java.vo.UserInfoVo;

@Controller
public class UserController extends BaseController {

	@Autowired
	private UserBiz userBiz;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody Message getVerificationCode(HttpServletRequest req, Registered registered) {
		return userBiz.addUser(req, registered);
	}
	
	@RequestMapping(value = "/mobileRepeat", method = RequestMethod.POST)
	public @ResponseBody Message getVerificationCode(HttpServletRequest request,String mobile) {
		return userBiz.mobileRepeat(mobile,request);
	}
	@RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
	public @ResponseBody Message updatePassword(HttpServletRequest request,String oldPassword,String password) {
        return userBiz.updatePassword(request,oldPassword,password);
	}
	
	@RequestMapping(value = "/user/resetPasswd", method = RequestMethod.POST)
	public @ResponseBody Message resetPasswd(HttpServletRequest request,String password) {
        return userBiz.resetPasswd(request,password);
	}
	@RequestMapping(value = "/idCertificate", method = RequestMethod.POST)
	public @ResponseBody Message idCertificat(HttpServletRequest request, String idnumber, String name) {
		return userBiz.idCertificat(request,idnumber, name, (User) request.getSession().getAttribute("zycfLoginUser"));
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Message login(HttpServletRequest req, @RequestBody LoginVO loginVo) {
		return userBiz.login(req, loginVo);
	}

	@RequestMapping(value = "/loginStatus", method = RequestMethod.POST)
	public @ResponseBody LoginUser login(HttpServletRequest request) {
		System.out.println(request.getRequestedSessionId()+"=====================");
		return userBiz.loginUser((User) request.getSession().getAttribute("zycfLoginUser"));
	}
	@RequestMapping(value = "/outLogin", method = RequestMethod.POST)
	public @ResponseBody Message outLogin(HttpServletRequest request) {
		request.getSession().removeAttribute("zycfLoginUser");
		Message message=new Message();
		message.setCode(1);
		return message;
	}

    @RequestMapping(value = "/user/agreement", method = RequestMethod.POST)
    public @ResponseBody String agreement(HttpServletRequest request,@RequestParam(value = "agreements[]") int[] agreements){
        return userBiz.agreement((User) request.getSession().getAttribute("zycfLoginUser"),agreements);
    }
    
    @RequestMapping(value = "/user/cancleAgreement", method = RequestMethod.POST)
    public @ResponseBody Message cancleAgreement(HttpServletRequest request,@RequestParam(value = "agreements[]") int[] agreements,String sourceV){
        return userBiz.cancleAgreement((User) request.getSession().getAttribute("zycfLoginUser"),agreements,sourceV);
    }
    
    
    @RequestMapping(value = "/app/agreement",method = RequestMethod.POST)
    public @ResponseBody Map appAgreement(HttpServletRequest request,@RequestParam(value = "agreements[]") int[] agreements){

    	Map<String,String> map = new HashMap<String,String>();
    	map.put("umpUrl", userBiz.appAgreement((User) request.getSession().getAttribute("zycfLoginUser"),agreements));
    	return map;
    }
    
    @RequestMapping(value = "/android/agreement",method = RequestMethod.POST)
    public @ResponseBody Map androidAgreement(HttpServletRequest request,@RequestBody int[] agreements){

    	Map<String,String> map = new HashMap<String,String>();
    	map.put("umpUrl", userBiz.appAgreement((User) request.getSession().getAttribute("zycfLoginUser"),agreements));
    	return map;
    }

    @RequestMapping(value = "/agreementCallBack")
    public @ResponseBody String agreementCallBack(CallBackUMP callBackUMP){
        return userBiz.agreementCallBack(callBackUMP);
    }
    
    @RequestMapping(value = "/cancleAgreementCallBack")
    public @ResponseBody String cancleAgreementCallBack(CallBackUMP callBackUMP){
        return userBiz.cancleAgreementCallBack(callBackUMP);
    }
    
	@RequestMapping(value = "/user/updateUserName", method = RequestMethod.POST)
	public @ResponseBody Message updateUserName(HttpServletRequest request,String userName){
		return userBiz.updateUserName(request,userName);
	}
	@RequestMapping(value = "/user/updateMail", method = RequestMethod.POST)
	public @ResponseBody Message updateMail(HttpServletRequest request,String email){
		return userBiz.updateMail(request,email);
	}
	@RequestMapping(value = "/selectInvestStatusByUserID")
	public @ResponseBody List<Map> selectInvestStatusByUserID(HttpServletRequest request){
		return userBiz.selectInvestStatusByUserID(request);
	}

	/**
	 * 用户设置数据请求
	 * @param request
	 * @return
	 */
	@LoginUserInterface(login=true)
	@RequestMapping(value="/user/userSettings")
	public @ResponseBody UserInfoVo getUserInfo(HttpServletRequest request){
		
		return userBiz.userSettings(request);
	}

	@RequestMapping(value="/app/login")
	public @ResponseBody Map  appLogin(HttpServletRequest request, UserInfoVo userInfoVo){
		return userBiz.appLogin(request, userInfoVo);
	}
	
	@RequestMapping(value="/queryUserByParams")
	public @ResponseBody Message  queryUserByParams(String mobile){
		return userBiz.queryUserByParams(mobile);
	}
	
	/**
	 * 
	* @Title: queryInvestSurveyByUser 
	* @Description:投资概况
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/queryInvestSurveyByUser")
	public @ResponseBody List<Map> queryInvestSurveyByUser(HttpServletRequest request){
		return userBiz.queryInvestSurveyByUser(request);
	}
	//邀请投资列表
	@RequestMapping(value = "/queryInviterInvest")
	public @ResponseBody PagerVO queryInviterInvest(HttpServletRequest request,Page page){
		return userBiz.queryInviterInvest(request,page);
	}
	
	//邀请列表
	@RequestMapping(value = "/queryUserByUserId")
	public @ResponseBody PagerVO queryUserByUserId(HttpServletRequest request,Page page){
		return userBiz.queryUserByUserId(request,page);
	}
	
	@RequestMapping(value="/app/test")
	public @ResponseBody String testForType(){
		
		return "urlString";
	}
}
