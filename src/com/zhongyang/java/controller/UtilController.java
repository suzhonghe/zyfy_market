package com.zhongyang.java.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.BannerBiz;
import com.zhongyang.java.biz.UserBiz;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.ShortMessage;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.Util;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.vo.VerificationCode;

@Controller
public class UtilController extends BaseController {
	
	@Autowired
	UserBiz userBiz;
	
	@Autowired
	private BannerBiz BannerBiz;
	
	
	@RequestMapping(value = "/imgValalidate",method=RequestMethod.GET)
	public void getVerificationImg(HttpServletRequest req, HttpServletResponse resp) {
		VerificationCode vc = Util.getVerificationCode();
		req.getSession().setAttribute("code", vc.getCode());        
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpeg");
		try {
			ServletOutputStream sos = resp.getOutputStream();
			ImageIO.write(vc.getBuffImg(), "jpeg", sos);
			sos.flush();
			sos.close();
		} catch (IOException e) {
			throw new UException(e);
		}
	}

	@RequestMapping(value = "/codeValalidate",method=RequestMethod.POST)
	public  @ResponseBody Message getVerificationCode(HttpServletRequest req,String imgCode) {
        Message message=new Message();
		String code= (String) req.getSession().getAttribute("code");
        if (code.equalsIgnoreCase(imgCode)){
            message.setCode(0);
            return message;
        }
        else{
        	 message.setCode(1);
        }
		return message;
	}
	
	@RequestMapping(value = "/mobolileCertWeb",method=RequestMethod.POST)
	public  @ResponseBody Message mobolileCertWeb(HttpServletRequest request,HttpServletResponse response) {
        if(request.getSession().getAttribute("zycfLoginUser")!=null){
            User user= (User) request.getSession().getAttribute("zycfLoginUser");
            List<String> list=new ArrayList<String>();
            list.add(user.getMobile());
            String code=ShortMessage.getShortMessage().getVerificationCode(list);
            request.getSession().setAttribute("mobolileCert",code);
            return new Message(0,"发送成功！");
        }
        Message message=new Message();
        Map<String,Cookie> cookies=ReadCookieMap(request);
        int mobileNumber=0;
        if (cookies.get("mobileNumber")==null){
            mobileNumber=0;
        }else{
            mobileNumber=Integer.parseInt(cookies.get("mobileNumber").getValue());
        }
        int attemptNum= Integer.parseInt((String) SystemPro.getProperties().get("ATTEMPTNUM"));
        if (mobileNumber>=attemptNum){
            message.setCode(1);
            return message;
        }
        long lastTime;
        if (cookies.get("lastTime")==null){
             lastTime=0;
        }else{
             lastTime=Long.parseLong(cookies.get("lastTime").getValue());
        }
        long nowTime=System.currentTimeMillis();
        if (nowTime<lastTime){
            message.setCode(2);
            return message;
        }
        int intervaltime=Integer.parseInt((String) SystemPro.getProperties().get("INTERVALTIME"));
        addCookie(response,"mobileNumber",String.valueOf(mobileNumber++),1);
        addCookie(response,"lastTime",String.valueOf(intervaltime*1000+nowTime),1);
        List<String> list=new ArrayList<String>();
        list.add(request.getSession().getAttribute("mobile").toString());
        String code=ShortMessage.getShortMessage().getVerificationCode(list);
        addCookie(response,"regcode",String.valueOf(code),1);
        request.getSession().setAttribute("regcode",code);
        System.out.print(code);
        message.setCode(0);
		return message;
	}


    @RequestMapping(value = "/moblileCert",method=RequestMethod.POST)
    public  @ResponseBody Message mobolileCert(HttpServletRequest request) {
        Message message=new Message();
        List<String> list=new ArrayList<String>();
        list.add(request.getSession().getAttribute("mobile").toString());
        String code=ShortMessage.getShortMessage().getVerificationCode(list);
        message.setCode(0);
        request.getSession().setAttribute("regcode",code);
        return message;
    }
    
    @RequestMapping(value="/resetPasswdMsg",method=RequestMethod.POST)
    public @ResponseBody Message resetPasswd(HttpServletRequest request,String mobile){
    	Message message = new Message();
    	HttpSession session = request.getSession();
    	
    	Message msg= userBiz.queryUserByParams(mobile);
    	if(msg.getCode() ==0 )
    		return msg;
    	Integer counter=null;
    	if(session.getAttribute("msgcounter")!=null)
    		counter =(Integer) session.getAttribute("msgcounter");
    	
    	if(counter==null)
    	{
    		session.setAttribute("msgcounter", 1);
    	}else if (counter.intValue()>2)
    	{	message.setCode(5);
    		return message;
    	}else{
    		session.setAttribute("msgcounter",counter.intValue()+1 );
    	}
    	
    	String resetCode = ShortMessage.getShortMessage().getResetPasswdCode(mobile);
//    	String resetCode = null;
    	session.setAttribute("resetPasswdCode", resetCode);
    	session.setAttribute("mobile", mobile);
    	message.setCode(1);
    	return message;
    }
    
    @RequestMapping(value="/resetPasswdValidate",method=RequestMethod.POST)
    public @ResponseBody Message resetPasswdValidate(HttpServletRequest request,String resetCode){
    	Message message = new Message();
    	String resetPasswdCode = (String) request.getSession().getAttribute("resetPasswdCode");
    	
    	int validTimes=0;
    	if(request.getSession().getAttribute("validTimes")!=null)
    		validTimes= (Integer)request.getSession().getAttribute("validTimes");
    	
    	if(validTimes>5){
    		message.setCode(2);
    		return message;
    	}
    	
    	if(resetPasswdCode.equalsIgnoreCase(resetCode))
    		message.setCode(0);
    	else
    		message.setCode(1);
    	
    	validTimes++;
    	request.getSession().setAttribute("validTimes", validTimes);
    	return message;
    }
    
    @RequestMapping(value = "/getAppVersion")
	public  @ResponseBody Map<String,Object> getAppVersion(HttpServletRequest req) {
		return BannerBiz.getAppVersion();
	}
}
