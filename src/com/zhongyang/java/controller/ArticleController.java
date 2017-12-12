package com.zhongyang.java.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.ArticleBiz;
import com.zhongyang.java.pojo.Article;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ArticleVO;

/**
 * 
* @Title: ArticleController.java 
* @Package com.zhongyang.java.controller 
* @Description:CMS文章控制器
* @author 苏忠贺   
* @date 2016年3月3日 下午4:04:24 
* @version V1.0
 */
@Controller
public class ArticleController extends BaseController{
	
	@Autowired
	private ArticleBiz articleBiz;
	
	@RequestMapping(value="/queryArticleByParams")
	public @ResponseBody Page<ArticleVO> queryByParams(Page<ArticleVO> page){
		return articleBiz.queryByParams(page);
	}
	
	@RequestMapping("/getArticleContent")
	@ResponseBody
	public Article getArticleById(String id){
		return articleBiz.getArticleById(id);
	}
	
}
