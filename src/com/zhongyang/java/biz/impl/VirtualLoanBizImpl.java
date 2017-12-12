package com.zhongyang.java.biz.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zhongyang.java.biz.VirtualLoanBiz;
import com.zhongyang.java.pojo.ExperienceAmount;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.pojo.VirtualLoan;
import com.zhongyang.java.service.ExperienceAmountService;
import com.zhongyang.java.service.VirtualLoanService;
import com.zhongyang.java.vo.ReturnVirtualLoanVO;
import com.zhongyang.java.vo.VirtualLoanVo;

@Service
public class VirtualLoanBizImpl implements VirtualLoanBiz {
	
	@Autowired
	private VirtualLoanService virtualLoanService;
	
	@Autowired
	private ExperienceAmountService experienceAmountService;

	@Override
	public ReturnVirtualLoanVO queryVirtualLoanOpened() {
		ReturnVirtualLoanVO virtual=new ReturnVirtualLoanVO();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		User user=(User)request.getSession().getAttribute("zycfLoginUser");
		VirtualLoanVo virtualLoanVo=new VirtualLoanVo();
		if(user==null){
			virtualLoanVo.setExperienceAmount(new BigDecimal(0));
			virtualLoanVo.setLogin(false);
		}else{
			ExperienceAmount experienceAmount=new ExperienceAmount();
			experienceAmount.setUserId(user.getId());
			experienceAmount.setEnable(false);
			experienceAmount.setEnableUse(false);
			ExperienceAmount experience = experienceAmountService.queryByParams(experienceAmount);
			if(experience!=null){
				virtualLoanVo.setExperienceAmount(experience.getAmount());
			}else{
				virtualLoanVo.setExperienceAmount(new BigDecimal(0));
			}
			
			virtualLoanVo.setLogin(true);
		}
		
		VirtualLoan virtualLoan=new VirtualLoan();
		virtualLoan.setStatus(1);
		VirtualLoan res = virtualLoanService.queryVirtualLoanByParams(virtualLoan);
		if(res!=null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			virtual.setCode(1);
			virtual.setMessage("查询成功");
			virtualLoanVo.setRate(res.getRate());
			virtualLoanVo.setLoanDay(res.getLoanDay());
			virtualLoanVo.setMethod(res.getMethod());
			virtualLoanVo.setStatus(res.getStatus());
			virtualLoanVo.setEndTime(sdf.format(new Date().getTime()+res.getLoanDay()*24*3600*1000L));
			virtual.setData(virtualLoanVo);
		}else{
			virtual.setCode(0);
			virtual.setMessage("没有进行中的体验标");
		}
		return virtual;
	}

	@Override
	public Map<String, String> queryVirtualLoan() {
		Map<String, String> map= new HashMap<>();
		map.put("msg", "0");
		return map;
	}

}
