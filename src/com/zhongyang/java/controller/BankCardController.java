package com.zhongyang.java.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.BankCardBiz;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.ModifyBankCardCallBackVO;
import com.zhongyang.java.vo.ParamsPojo;
import com.zhongyang.java.vo.bankCardCallBackVO;

@Controller
public class BankCardController extends BaseController{
		
	@Autowired
	private BankCardBiz bankCardBiz;
	
	@Autowired UserService userService;

	@RequestMapping(value = "/bankCardBinding",method=RequestMethod.POST )
	public  @ResponseBody Message bankCardBinding(HttpServletRequest req,@RequestBody ParamsPojo params) {
			User user=(User) req.getSession().getAttribute("zycfLoginUser");
			if(params.getFastPayment()==null)
				params.setFastPayment("0");
		return bankCardBiz.bankCardBinding(params.getBank(),params.getAccount(),params.getFastPayment(),user,params.getSource());
	}
	
	@RequestMapping(value = "/modifyBankCard",method=RequestMethod.POST)
	public  @ResponseBody Message modifyBankCard(HttpServletRequest req,@RequestBody ParamsPojo params) {
		User user=(User) req.getSession().getAttribute("zycfLoginUser");
		return bankCardBiz.modifyBankCard(params.getBank(),params.getAccount(),user,"wap",params.getSource());
	}
	
	@RequestMapping(value = "/umpCardBinding")
	public  @ResponseBody String umpCardBinding(HttpServletRequest req,bankCardCallBackVO vo) {
		return	bankCardBiz.umpCardBinding(vo);
	}
	
	@RequestMapping(value = "/modifyBankCardCallBack")
	public  @ResponseBody String modifyBankCardCallBack(HttpServletRequest req,ModifyBankCardCallBackVO vo) {
		return	bankCardBiz.modifyBankCardCallBack(vo);
	}
	
	
	@RequestMapping(value = "/app/bankCardBinding",method=RequestMethod.POST)
	public  @ResponseBody Message appBankCardBinding(HttpServletRequest req,String bank,String account,String fastPayment) {
			User user=(User) req.getSession().getAttribute("zycfLoginUser");
			if(user.getIdnumber() == null)
				user = userService.queryById(user.getId());
				fastPayment="0";
		return bankCardBiz.appBankCardBinding(bank,account,fastPayment,user);
	}
	
}
