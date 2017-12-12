package com.zhongyang.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.BannerBiz;
import com.zhongyang.java.pojo.BannerPhoto;

@Controller
public class BannerController extends BaseController{
	
	@Autowired
	private BannerBiz bannerBiz;
	
	@RequestMapping(value="/queryBannerPhotos")
	public @ResponseBody List<BannerPhoto> queryBannerListPhotos(String type){
		return bannerBiz.queryBannerPhotos(type);
	}
}
