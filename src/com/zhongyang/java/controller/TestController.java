package com.zhongyang.java.controller;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.umpay.api.exception.ReqDataException;
import com.umpay.api.exception.RetDataException;
import com.zhongyang.java.biz.TestBiz;
import com.zhongyang.java.pojo.Test;
import com.zhongyang.java.system.log.LogInterface;
import com.zhongyang.java.system.umpay.UmpaySignature;

@Controller
public class TestController extends BaseController{
	
	Logger loger=Logger.getLogger(TestController.class.getName());
	
	@Autowired
	private TestBiz testBiz;
	
	@LogInterface(description="测试返回数据")
	@RequestMapping(value="/hello")
	public @ResponseBody void getTest() throws
	UnsupportedEncodingException, ReqDataException{
		Map map=new HashMap<String, String>();
		String uuid="59c7b3a8-37c5-445f-a121-ca46b4ecf6a6";
		uuid=uuid.replace("-", "");
		map.put("mer_cust_id", uuid);
		map.put("mer_cust_name", "张忍德");
		map.put("identity_type", "IDENTITY_CARD");
		map.put("identity_code", "431126198509083213");
		map.put("mobile_id", "15811013432");
		UmpaySignature umpaySignature = new UmpaySignature("mer_register_person",map);
		try {
		Map valuemap=umpaySignature.getSignature();
		System.out.println(valuemap);
		} catch (RetDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@LogInterface(description="测试异常测试异常测试异常测试异111常")
	@RequestMapping(value="/getException")
	public @ResponseBody List<Test> getException(){
		return testBiz.getException();
	}
	
	public static void main(String[] args) throws GeneralSecurityException, ParseException {
//		String uuid="13986550815";
//		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
//		System.out.println(cipher.encrypt(uuid));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		Date date = simpleDateFormat.parse("2015-12-28");
		System.out.println(simpleDateFormat.format(date));
		
		
		
	}
	
	@LogInterface(description="测试返回数据")
	@RequestMapping(value="/testBidpulished")
	public @ResponseBody void getTestBidpublished() throws
	UnsupportedEncodingException, ReqDataException{
		Map map=new HashMap<String, String>();
		String uuid="2635f453-9814-451a-af69-47d807032379";
		uuid=uuid.replace("-", "");
		map.put("project_id", uuid);
		map.put("project_name", "某某某某");
		map.put("project_amount", "100000");
		map.put("loan_user_id", "UB201410101152290000000000877955");
		map.put("project_account_id", "");
		UmpaySignature umpaySignature = new UmpaySignature("mer_bind_project",map );
		
		
		try {
			Map valuemap = umpaySignature.getSignature();
			System.out.println(valuemap);
		} catch (RetDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
