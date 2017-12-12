package com.zhongyang.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.ClientFundRecordBiz;
import com.zhongyang.java.vo.TransferCallback;

@Controller
public class ClientFundRecordController extends BaseController{
	
	@Autowired
	private ClientFundRecordBiz clientFundRecordBiz;
	
	@RequestMapping("/transferCallback")
	public @ResponseBody String transferCallback(TransferCallback transferCallback){
		return clientFundRecordBiz.transferCallback(transferCallback);
	}

}
