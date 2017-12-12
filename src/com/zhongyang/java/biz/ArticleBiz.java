package com.zhongyang.java.biz;

import java.util.List;

import com.zhongyang.java.pojo.Article;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ArticleVO;

/**
 * 
* @Title: ArticleBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:文章业务处理接口
* @author 苏忠贺   
* @date 2016年3月3日 下午3:08:54 
* @version V1.0
 */
public interface ArticleBiz {
	
	public Page<ArticleVO> queryByParams(Page<ArticleVO> page);
	
	public Article getArticleById(String id);


}
