package com.zhongyang.java.service;

import java.util.List;


import com.zhongyang.java.pojo.Article;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ArticleVO;

/**
 * 
* @Title: ArticleService.java 
* @Package com.zhongyang.java.service 
* @Description:业务接口
* @author 苏忠贺   
* @date 2016年3月3日 下午2:34:25 
* @version V1.0
 */
public interface ArticleService {
	
	public List<Article> queryByParams(Page<ArticleVO> page)throws Exception;
	
	public Article getArticleById(String id);
	/**
	 * 
	 * @param columnId
	 * @param preIndex
	 * @param surIndex
	 * @return
	 */
	public List<Article> getBeforAndAfter(String columnId, int preIndex, int surIndex);
	
}
