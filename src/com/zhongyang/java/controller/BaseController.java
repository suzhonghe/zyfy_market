package com.zhongyang.java.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.vo.UMessage;

@Controller
public class BaseController {
	
	@Autowired
	HttpSession session;
	
	@ExceptionHandler(UException.class)
	public @ResponseBody UMessage exp(HttpServletRequest request,UException uException) {
		UMessage uMessage =UMessage.getuMessage();
		switch (uException.getState()) {
		case 0:
			uMessage.setCode(uException.getCode());
			String massage=uException.getMessage();
			uMessage.setMessage(massage);
			break;
		case 1:
		Exception e=uException.getException();
		Throwable th=e.getCause();
			if(th instanceof PersistenceException){
				uMessage.setCode(SystemEnum.SERVER_REFUSED);
				uMessage.setMessage("数据库连接失败");
				break;
			}else if(th instanceof NoSuchAlgorithmException){
				uMessage.setCode(SystemEnum.EN_CODE_MD5_EXCEPTION);
				uMessage.setMessage("加密错误");
				break;
			}else if(th instanceof IOException){
				uMessage.setCode(SystemEnum.FILE_READ_WRITE_EXCEPTION);
				uMessage.setMessage("文件读写错误");
				break;
			}
			else{
				uMessage.setCode(SystemEnum.UNKNOW_EXCEPTION);
				uMessage.setMessage("未知异常,请与管理员联系");
				break;
			}		
		}
		return uMessage;		
	}

    static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    public static Cookie getCookieByName(HttpServletRequest request,String name){
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie;
        }else{
            return null;
        }
    }

    public static void addCookie(HttpServletResponse response, String name, String value,int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        if(maxAge>0)  cookie.setMaxAge(maxAge*24*60*60);
        response.addCookie(cookie);
    }
	
}
